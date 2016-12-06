package zeta.android.myntra.api.devapi.response.search;

import android.support.annotation.Nullable;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SearchResultsResponse {

    public final long totalProductsCount;
    public final List<SearchFiltersResponse> filters;
    @Nullable
    public final List<SearchProductsResponse> products;

    public SearchResultsResponse(long totalProductsCount,
                                 List<SearchFiltersResponse> filters,
                                 @Nullable List<SearchProductsResponse> products) {
        this.totalProductsCount = totalProductsCount;
        this.filters = filters;
        this.products = products;
    }

}
