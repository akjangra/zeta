package zeta.android.myntra;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import static zeta.android.myntra.MyntraEngineEnvironment.Environment;

@AutoValue
public abstract class MyntraEngineConfig implements Parcelable {

    public static Builder create() {
        return new AutoValue_MyntraEngineConfig.Builder()
                .setDevApiEnvironment(Environment.PROD)
                .setIdpApiEnvironment(Environment.PROD);
    }

    @Nullable
    public abstract MyntraEngineCachePolicy getCachePolicy();

    @Environment
    public abstract int getDevApiEnvironment();

    @Environment
    public abstract int getIdpApiEnvironment();
    //Add more env if we are using more API's later!

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder setCachePolicy(@Nullable MyntraEngineCachePolicy cachePolicy);

        public abstract Builder setDevApiEnvironment(@Environment int environment);

        public abstract Builder setIdpApiEnvironment(@Environment int environment);

        public abstract MyntraEngineConfig build();
    }
}
