package zeta.android.myntra.api.devapi.response.search;

import com.google.gson.annotations.SerializedName;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SearchDataResponse {

    public final SearchResultsResponse results;

    @SerializedName("search")
    public final SearchInfoResponse searchInfo;

    public final int totalProductsCount;

    public SearchDataResponse(SearchResultsResponse results,
                              SearchInfoResponse searchInfo,
                              int totalProductsCount) {
        this.results = results;
        this.searchInfo = searchInfo;
        this.totalProductsCount = totalProductsCount;
    }

}
