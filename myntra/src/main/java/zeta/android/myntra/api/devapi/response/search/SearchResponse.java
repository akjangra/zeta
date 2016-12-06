package zeta.android.myntra.api.devapi.response.search;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SearchResponse {

    public final SearchDataResponse data;
    public final SearchMetaResponse meta;

    public SearchResponse(SearchDataResponse data,
                          SearchMetaResponse meta) {
        this.data = data;
        this.meta = meta;
    }
}
