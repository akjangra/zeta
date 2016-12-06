package zeta.android.myntra.interceptors;

import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@ParametersAreNonnullByDefault
public class XmlContentTypeHeadersInterceptor implements Interceptor {

    private static final String KEY_ACCEPT = "Accept";
    private static final String KEY_CONTENT_TYPE = "Content-Type";
    private static final String VALUE_ACCEPT = "application/xml";
    private static final String VALUE_CONTENT_TYPE = "application/xml";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request
                .newBuilder()
                .header(KEY_ACCEPT, VALUE_ACCEPT)
                .header(KEY_CONTENT_TYPE, VALUE_CONTENT_TYPE)
                .build();
        return chain.proceed(request);
    }
}
