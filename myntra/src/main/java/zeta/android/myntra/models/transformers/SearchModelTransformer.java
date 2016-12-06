package zeta.android.myntra.models.transformers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import zeta.android.myntra.api.devapi.response.search.SearchDataResponse;
import zeta.android.myntra.api.devapi.response.search.SearchInfoResponse;
import zeta.android.myntra.api.devapi.response.search.SearchProductsResponse;
import zeta.android.myntra.api.devapi.response.search.SearchResponse;
import zeta.android.myntra.api.devapi.response.search.SearchResultsResponse;
import zeta.android.myntra.models.common.ITransformer;
import zeta.android.myntra.models.products.ProductGist;
import zeta.android.myntra.models.products.ProductId;
import zeta.android.myntra.models.search.SearchModel;
import zeta.android.myntra.utils.ImageUtils;

@ParametersAreNonnullByDefault
public class SearchModelTransformer implements ITransformer<SearchResponse, SearchModel> {

    @Override
    public SearchModel transform(SearchResponse response) {
        final SearchDataResponse searchData = response.data;
        final SearchInfoResponse searchInfo = searchData.searchInfo;
        final SearchResultsResponse results = searchData.results;

        final int pageSize = searchInfo.noOfRows;
        final int pageStartIndex = searchInfo.startIndex;
        final long totalProductsCount = results.totalProductsCount;
        final int currentPageNumber = (pageSize > 0 ? pageStartIndex / pageSize : 0) + 1;

        final List<SearchProductsResponse> products = results.products;
        final List<ProductGist> productGists = new ArrayList<>(products != null ? products.size() : 0);
        if (products != null) {
            for (SearchProductsResponse product : products) {
                ProductGist productGistObject = ProductGist.create()
                        .setProductId(ProductId.create(product.productId).build())
                        .setProductImage(ImageUtils.getCloudnaryImageUrl(product.searchImage))
                        .setProductTitle(product.productTitle)
                        .setRegularPrice(product.regularPrice)
                        .setDiscountedPrice(product.discountedPrice)
                        .setProductBrand(product.brandsFilterFacets)
                        .build();
                productGists.add(productGistObject);
            }
        }

        return SearchModel.create()
                .setSearchQuery(searchInfo.query)
                .setProducts(productGists)
                .setPageNumber(currentPageNumber)
                .setPageSize(pageSize)
                .setTotalSearchCount(totalProductsCount)
                .build();
    }
}