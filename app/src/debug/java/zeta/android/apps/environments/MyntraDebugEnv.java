package zeta.android.apps.environments;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({MyntraDebugEnv.DEBUG_PROD, MyntraDebugEnv.DEBUG_STAGE, MyntraDebugEnv.DEBUG_UAT})
@Retention(RetentionPolicy.SOURCE)
public @interface MyntraDebugEnv {
    int DEBUG_PROD = 1;
    int DEBUG_STAGE = 2;
    int DEBUG_UAT = 3;
}

