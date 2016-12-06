package zeta.android.myntra;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import static zeta.android.myntra.MyntraEngineEnvironment.Environment;

@AutoValue
public abstract class MyntraEngineConfig implements Parcelable {

    public static Builder create() {
        return new AutoValue_MyntraEngineConfig.Builder()
                .setMyntraEngineEnvironment(Environment.PROD);
    }

    @Nullable
    public abstract MyntraEngineCachePolicy getCachePolicy();

    @Environment
    public abstract int getMyntraEngineEnvironment();

    //Add more env if we are using more API's later!

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder setCachePolicy(@Nullable MyntraEngineCachePolicy cachePolicy);

        public abstract Builder setMyntraEngineEnvironment(@Environment int environment);

        public abstract MyntraEngineConfig build();
    }
}
