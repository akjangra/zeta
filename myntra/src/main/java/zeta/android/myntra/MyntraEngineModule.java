package zeta.android.myntra;

import android.content.Context;

import com.franmontiel.persistentcookiejar.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zeta.android.myntra.environments.DevApiEnvironment;
import zeta.android.myntra.environments.IdpApiEnvironment;
import zeta.android.myntra.interceptors.CacheHeadersInterceptor;
import zeta.android.myntra.interceptors.DevApiHeadersInterceptor;
import zeta.android.myntra.interceptors.IdpApiHeadersInterceptor;
import zeta.android.myntra.interceptors.JsonContentTypeHeadersInterceptor;
import zeta.android.myntra.interceptors.XmlContentTypeHeadersInterceptor;
import zeta.android.myntra.qualifiers.okhttp.JsonContentTypeOkHttp;
import zeta.android.myntra.qualifiers.okhttp.MyntraOkHttp;
import zeta.android.myntra.qualifiers.okhttp.XmlContentTypeOkHttp;
import zeta.android.myntra.qualifiers.retrofit.RetrofitDevApi;
import zeta.android.myntra.qualifiers.retrofit.RetrofitIdpApi;

@Module
@ParametersAreNonnullByDefault
public class MyntraEngineModule {

    //This has to be at-least package level to be built by Dagger
    interface BaseRetrofitFactory {
        Retrofit.Builder newRetrofitBuilder();
    }

    @Provides
    @Singleton
    @MyntraOkHttp
    OkHttpClient providesOkHttpClient(MyntraEngineCachePolicy cachePolicy,
                                      @Named OkHttpClient okHttpClient) {
        final long cacheMaxAgeInSeconds = cachePolicy.getCacheMaxAgeInSeconds();
        final Cache cache = new Cache(cachePolicy.getCacheDirectory(),
                cachePolicy.getCacheSizeInMb());
        return okHttpClient.newBuilder()
                .cache(cache)
                .addNetworkInterceptor(new CacheHeadersInterceptor(cacheMaxAgeInSeconds))
                .build();
    }

    @Provides
    @Singleton
    @JsonContentTypeOkHttp
    OkHttpClient providesJsonContentTypeRetrofit(@MyntraOkHttp OkHttpClient okHttpClient) {
        return okHttpClient.newBuilder()
                .addNetworkInterceptor(new JsonContentTypeHeadersInterceptor())
                .build();
    }

    @Provides
    @Singleton
    @XmlContentTypeOkHttp
    OkHttpClient providesXmlContentTypeRetrofit(@MyntraOkHttp OkHttpClient okHttpClient) {
        return okHttpClient.newBuilder()
                .addNetworkInterceptor(new XmlContentTypeHeadersInterceptor())
                .build();
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    Converter.Factory providesGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    CallAdapter.Factory providesRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    //region environments
    @Provides
    @Singleton
    DevApiEnvironment providesDevApiEnvironment(MyntraEngineConfig myntraEngineConfig) {
        return new DevApiEnvironment(myntraEngineConfig.getDevApiEnvironment());
    }

    @Provides
    @Singleton
    IdpApiEnvironment providesIdpApiEnvironment(MyntraEngineConfig myntraEngineConfig) {
        return new IdpApiEnvironment(myntraEngineConfig.getIdpApiEnvironment());
    }
    //Add more env here

    //endregion environments
    @Provides
    @Singleton
    MyntraEngineCachePolicy providesCachePolicy(Context context, MyntraEngineConfig config) {
        final MyntraEngineCachePolicy cachePolicy = config.getCachePolicy();
        return cachePolicy != null ? cachePolicy : MyntraEngineCachePolicy.create(context.getCacheDir()).build();
    }
    //region retrofit

    @Provides
    @Singleton
    BaseRetrofitFactory provideBaseRetrofitFactory(final Converter.Factory gsonConverterFactory,
                                                   final CallAdapter.Factory callAdapterFactory) {
        return () -> new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .validateEagerly(BuildConfig.DEBUG);
    }

    @Provides
    @Singleton
    @RetrofitDevApi
    Retrofit providesMyntraDevApiRetrofit(DevApiEnvironment myntraDevApiEnvironment,
                                          @JsonContentTypeOkHttp OkHttpClient okHttpClient,
                                          BaseRetrofitFactory baseRetrofitFactory) {
        return baseRetrofitFactory.newRetrofitBuilder()
                .baseUrl(myntraDevApiEnvironment.getBaseUrl())
                .client(okHttpClient.newBuilder()
                        .addNetworkInterceptor(new DevApiHeadersInterceptor())
                        .build())
                .build();
    }


    @Provides
    @Singleton
    @RetrofitIdpApi
    Retrofit providesMyntraIdpApiRetrofit(IdpApiEnvironment idpApiEnvironment,
                                          @JsonContentTypeOkHttp OkHttpClient okHttpClient,
                                          BaseRetrofitFactory baseRetrofitFactory) {
        return baseRetrofitFactory.newRetrofitBuilder()
                .baseUrl(idpApiEnvironment.getBaseUrl())
                .client(okHttpClient.newBuilder()
                        .addNetworkInterceptor(new IdpApiHeadersInterceptor())
                        .build())
                .build();
    }
    //endregion retrofit

}