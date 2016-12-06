package zeta.android.apps.ui.fragment.common.presentation;

import android.support.annotation.StringRes;

public interface BasePresentation {

    void showProgress(boolean show);

    void showSnackBarMessage(@StringRes int message);
}
