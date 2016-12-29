package zeta.android.myntra.environments;

import android.support.annotation.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import zeta.android.myntra.MyntraEngineEnvironment.Environment;

import static zeta.android.myntra.MyntraEngineEnvironment.Environment.PROD;
import static zeta.android.myntra.MyntraEngineEnvironment.Environment.STAGE;
import static zeta.android.myntra.MyntraEngineEnvironment.Environment.UAT;

@ParametersAreNonnullByDefault
public class IdpApiEnvironment implements BaseEnvironment {

    @Environment
    private final int mEnvironment;
    private final String mBaseUrl;

    private static String PROD_BASE_URL = "http://developer.myntra.com";
    private static String STAGE_BASE_URL = "http://foxhaddeveloper.myn.myntra.com";
    private static String UAT_BASE_URL = STAGE_BASE_URL;

    public IdpApiEnvironment(@Environment int environment) {
        mEnvironment = environment;
        switch (environment) {
            case STAGE:
                mBaseUrl = PROD_BASE_URL;
                break;
            case UAT:
                mBaseUrl = UAT_BASE_URL;
                break;
            case PROD:
            default:
                mBaseUrl = PROD_BASE_URL;
                break;
        }
    }

    @Override
    @Environment
    public int getEnvironment() {
        return mEnvironment;
    }

    @Override
    public String getBaseUrl() {
        return mBaseUrl;
    }

    @Override
    @Nullable
    public String getKey() {
        return null;
    }

    @Override
    @Nullable
    public String getSecureKey() {
        return null;
    }
}
