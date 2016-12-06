package zeta.android.apps.di.module;

import android.content.Context;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import zeta.android.apps.di.qualifier.OkHttpInterceptors;
import zeta.android.apps.di.qualifier.OkHttpNetworkInterceptors;
import zeta.android.apps.network.interceptor.ConnectivityCheckInterceptor;
import zeta.android.apps.providers.interfaces.ConnectivityProvider;

@Module
public class NetworkModule {

    private static final long THIRTY_SECONDS = 30L;

    @Provides
    @Singleton
    @Named
    public OkHttpClient provideOkHttpClient(ConnectivityProvider connectivityProvider,
                                            @OkHttpInterceptors List<Interceptor> interceptors,
                                            @OkHttpNetworkInterceptors List<Interceptor> networkInterceptors) {
        //Common interceptors / other OkHttp builder things should go here
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .readTimeout(THIRTY_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(THIRTY_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(THIRTY_SECONDS, TimeUnit.SECONDS);

        //Check for no connectivity
        okHttpBuilder.addInterceptor(new ConnectivityCheckInterceptor(connectivityProvider));

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        return okHttpBuilder.build();
    }
}
