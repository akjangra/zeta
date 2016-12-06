package zeta.android.apps.di.module;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import zeta.android.apps.sharedPref.DebugSharedPreferences;
import zeta.android.apps.sharedPref.DebugSharedPreferences.Environment;
import zeta.android.myntra.MyntraEngineCachePolicy;
import zeta.android.myntra.MyntraEngineConfig;
import zeta.android.myntra.MyntraEngineEnvironment;

@Singleton
@Module
public class ConfigModule {

    @Provides
    MyntraEngineConfig provideConfig(Context context, DebugSharedPreferences sharedPreferences) {
        //Debug app uses 10 min's max age cache policy
        Environment myntraSearchEnvironment = sharedPreferences.getCurrentMyntraSearchEnvironment();
        final MyntraEngineCachePolicy cachePolicy = MyntraEngineCachePolicy.create(context.getCacheDir())
                .setCacheMaxAgeInSeconds(TimeUnit.MINUTES.toMinutes(10))
                .build();
        return MyntraEngineConfig.create()
                .setMyntraEngineEnvironment(transform(myntraSearchEnvironment))
                .setCachePolicy(cachePolicy)
                .build();
    }

    @MyntraEngineEnvironment.Environment
    private int transform(Environment env) {
        switch (env) {
            case Stage:
                return MyntraEngineEnvironment.Environment.STAGE;
            case UAT:
                return MyntraEngineEnvironment.Environment.UAT;
            default:
            case Production:
                return MyntraEngineEnvironment.Environment.PROD;
        }
    }

}
