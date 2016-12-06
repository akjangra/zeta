package zeta.android.myntra.api.devapi.response.search;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SearchMetaResponse {

    public final int code;

    public SearchMetaResponse(int code) {
        this.code = code;
    }
}
