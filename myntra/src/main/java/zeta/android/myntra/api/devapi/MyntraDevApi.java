package zeta.android.myntra.api.devapi;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import zeta.android.myntra.api.devapi.response.pdp.PdpResponse;
import zeta.android.myntra.api.devapi.response.search.SearchResponse;

@ParametersAreNonnullByDefault
public interface MyntraDevApi {

    @GET("v2/search/data/{query}")
    Observable<Response<SearchResponse>> getSearchResultResponse(@Path("query") String query,
                                                                 @Query("p") int pageNumber,
                                                                 @Query("row") int pageSize);

    @GET("style/{styleId}")
    Observable<Response<PdpResponse>> getProductDetailsResponse(@Path("styleId") int styleId);

}