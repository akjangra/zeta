package zeta.android.myntra.models.search.errors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.util.Pair;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.ParametersAreNonnullByDefault;

import zeta.android.myntra.models.common.errors.BaseException;

@ParametersAreNonnullByDefault
public class SearchException extends BaseException {

    public SearchException(@Nullable Pair<String, String> errorCodeAndParam) {
        super(errorCodeAndParam);
    }

    @SearchError
    @NonNull
    @Override
    public String getErrorCode() {
        if (errorCode == null) {
            return SearchError.UNKNOWN;
        }
        switch (errorCode) {
            case SearchError.NO_SEARCH_RESULT_FOUND:
                return SearchError.NO_SEARCH_RESULT_FOUND;
            default:
                return SearchError.UNKNOWN;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({SearchError.NO_SEARCH_RESULT_FOUND, SearchError.UNKNOWN})
    public @interface SearchError {
        String NO_SEARCH_RESULT_FOUND = "no search result found";
        String UNKNOWN = "UNKNOWN";
    }
}
