package zeta.android.myntra.environments;

import android.support.annotation.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import static zeta.android.myntra.MyntraEngineEnvironment.Environment;

@ParametersAreNonnullByDefault
public interface BaseEnvironment {

    @Environment
    int getEnvironment();

    String getBaseUrl();

    @Nullable
    String getKey();

    @Nullable
    String getSecureKey();
}
