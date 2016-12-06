package zeta.android.apps.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkSamplingInterceptor implements Interceptor {

    public NetworkSamplingInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }
}
