package zeta.android.apps.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import zeta.android.myntra.MyntraEngineConfig;

@Singleton
@Module
public class ConfigModule {

    @Provides
    MyntraEngineConfig provideConfig() {
        //Release uses default configurations
        return MyntraEngineConfig.create().build();
    }
}
