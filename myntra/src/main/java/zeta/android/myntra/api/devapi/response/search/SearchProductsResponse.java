package zeta.android.myntra.api.devapi.response.search;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SearchProductsResponse {

    public final long discount;

    @SerializedName("brands_filter_facet")
    public final String brandsFilterFacets;

    @SerializedName("search_image")
    public final URL searchImage;

    @SerializedName("stylename")
    public final String styleName;

    @SerializedName("discounted_price")
    public final int discountedPrice;

    @SerializedName("price")
    public final int regularPrice;

    @SerializedName("product")
    public final String productTitle;

    @SerializedName("styleid")
    public final int productId;

    public SearchProductsResponse(long discount,
                                  String brandsFilterFacets,
                                  URL searchImage,
                                  String styleName,
                                  int discountedPrice,
                                  int regularPrice,
                                  String productTitle,
                                  int productId) {
        this.discount = discount;
        this.brandsFilterFacets = brandsFilterFacets;
        this.searchImage = searchImage;
        this.styleName = styleName;
        this.discountedPrice = discountedPrice;
        this.regularPrice = regularPrice;
        this.productTitle = productTitle;
        this.productId = productId;
    }
}
