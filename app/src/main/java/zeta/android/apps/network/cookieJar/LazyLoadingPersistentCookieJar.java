package zeta.android.apps.network.cookieJar;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 1. The default implementation provided by the library performed <em>I/O operations in the constructor</em>
 * (yes, really), which meant a .5 second(!!) pause on the main thread.  This version lazy-loads the cache from
 * disk as needed, which will happening on a background thread.
 */
@ParametersAreNonnullByDefault
public class LazyLoadingPersistentCookieJar implements ClearableCookieJar {

    private CookieCache cache;
    private CookiePersistor persistor;
    private boolean isCacheLoaded;

    public LazyLoadingPersistentCookieJar(CookieCache cache, CookiePersistor persistor) {
        this.cache = cache;
        this.persistor = persistor;
    }

    @Override
    synchronized public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        loadCacheIfNeeded();  // NOTE: this MUST be called first!

        cookies = preprocessCookies(cookies);

        cache.addAll(cookies);
        persistor.saveAll(cookies);
    }

    @Override
    synchronized public List<Cookie> loadForRequest(HttpUrl url) {
        loadCacheIfNeeded();  // NOTE: this MUST be called first!

        List<Cookie> removedCookies = new ArrayList<>();
        List<Cookie> validCookies = new ArrayList<>();

        for (Iterator<Cookie> it = cache.iterator(); it.hasNext(); ) {
            Cookie currentCookie = it.next();

            if (isCookieExpired(currentCookie)) {
                removedCookies.add(currentCookie);
                it.remove();

            } else if (currentCookie.matches(url)) {
                validCookies.add(currentCookie);
            }
        }

        persistor.removeAll(removedCookies);

        return validCookies;
    }

    @Override
    public void clearSession() {
        clear();
    }

    public synchronized void clear() {
        cache.clear();
        persistor.clear();
    }

    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    private void loadCacheIfNeeded() {
        if (isCacheLoaded) {
            return;
        }
        isCacheLoaded = true;
        this.cache.addAll(persistor.loadAll());
    }

    private List<Cookie> preprocessCookies(List<Cookie> cookies) {
        // make a modifiable list of cookies
        List<Cookie> validCookies = new ArrayList<>(cookies);
        List<Cookie> expiredCookies = new ArrayList<>(cookies.size());
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            if (cookie.persistent() && cookie.expiresAt() < System.currentTimeMillis()) {
                //remove the expired cookie from the list valid cookies
                validCookies.remove(cookie);
                // add the expired cookie to a list, later to be removed from the persistor
                expiredCookies.add(cookie);
            }
        }

        // remove the list of expired cookies
        this.persistor.removeAll(expiredCookies);

        // proceed with the super class implementation, add all the valid cookies to the cache
        // and save them to the persistor
        return validCookies;
    }
}
