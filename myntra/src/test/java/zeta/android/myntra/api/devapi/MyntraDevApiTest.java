package zeta.android.myntra.api.devapi;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;
import rx.Observable;
import rx.observers.TestSubscriber;
import zeta.android.myntra.ApiTestBase;
import zeta.android.myntra.api.devapi.response.pdp.PdpDataResponse;
import zeta.android.myntra.api.devapi.response.pdp.PdpMetaResponse;
import zeta.android.myntra.api.devapi.response.pdp.PdpNotificationResponse;
import zeta.android.myntra.api.devapi.response.pdp.PdpResponse;
import zeta.android.myntra.api.devapi.response.search.SearchDataResponse;
import zeta.android.myntra.api.devapi.response.search.SearchMetaResponse;
import zeta.android.myntra.api.devapi.response.search.SearchProductsResponse;
import zeta.android.myntra.api.devapi.response.search.SearchResponse;

import static org.junit.Assert.assertEquals;

@ParametersAreNonnullByDefault
public class MyntraDevApiTest extends ApiTestBase {

    private MyntraDevApi myntraDevApi;

    @Before
    public void setUpMockRetrofit() {
        super.setUpMockRetrofit();

        //Mock the response form the test json file
        final BehaviorDelegate<MyntraDevApi> myntraDevApiBehaviorDelegate = mMockRetrofit.create(MyntraDevApi.class);
        myntraDevApi = new MyntraDevApi() {
            @Override
            public Observable<Response<SearchResponse>> getSearchResultResponse(@Path("query") String query,
                                                                                @Query("p") int pageNumber,
                                                                                @Query("row") int pageSize) {
                return myntraDevApiBehaviorDelegate.returning(
                        buildResponse("search_response_nike.json", SearchResponse.class))
                        .getSearchResultResponse(query, pageNumber, pageSize);
            }

            @Override
            public Observable<Response<PdpResponse>> getProductDetailsResponse(@Path("styleId") int styleId) {
                return myntraDevApiBehaviorDelegate.returning(
                        buildResponse("pdp_response_1291342.json", PdpResponse.class))
                        .getProductDetailsResponse(styleId);
            }
        };
    }

    @Test
    public void getSearchResultResponse() throws Exception {
        TestSubscriber<Response<SearchResponse>> testSubscriber = new TestSubscriber<>();
        myntraDevApi.getSearchResultResponse("nike", 1, 20).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        List<Response<SearchResponse>> onNextEvents = testSubscriber.getOnNextEvents();
        SearchResponse searchResponse = onNextEvents.get(0).body();

        SearchDataResponse data = searchResponse.data;
        SearchMetaResponse meta = searchResponse.meta;

        List<SearchProductsResponse> products = data.results.products;
        assert products != null;
        SearchProductsResponse searchProductsResponse = products.get(0);
        assert searchProductsResponse != null;

        assertEquals(200, meta.code);
        assertEquals("Nike Men Blue AS M NSW CRW FLC AIR Pullover Sweatshirt", searchProductsResponse.productTitle);
        assertEquals("Nike Men Blue AS M NSW CRW FLC AIR Pullover Sweatshirt", searchProductsResponse.styleName);
        assertEquals(1601830, searchProductsResponse.productId);
        assertEquals(0, searchProductsResponse.discount);
        assertEquals("http://myntra.myntassets.com/assets/images/1601830/2016/11/21/11479707823353-Nike-Men-Blue-Solid-Pullover-Sweatshirt-5921479707822951-1.jpg",
                searchProductsResponse.searchImage.toString());
        assertEquals("Nike", searchProductsResponse.brandsFilterFacets);

        //TODO :: Add more asserts here.
    }

    @Test
    public void getProductDetailsResponse() throws Exception {
        TestSubscriber<Response<PdpResponse>> testSubscriber = new TestSubscriber<>();
        myntraDevApi.getProductDetailsResponse(1291342).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        List<Response<PdpResponse>> onNextEvents = testSubscriber.getOnNextEvents();
        PdpResponse response = onNextEvents.get(0).body();
        assert response != null;

        PdpDataResponse pdpDataResponse = response.pdpDataResponse;
        PdpMetaResponse pdpMetaResponse = response.pdpMetaResponse;
        PdpNotificationResponse pdpNotificationResponse = response.pdpNotificationResponse;

        assert pdpDataResponse != null;
        assert pdpMetaResponse != null;
        assert pdpNotificationResponse != null;

        assertEquals(1291342, pdpDataResponse.productId);
        assertEquals(699, pdpDataResponse.price);
        assertEquals(419, pdpDataResponse.discountedPrice);

        assertEquals("P", pdpDataResponse.styleType);
        assertEquals("JK2695-Black-1291342", pdpDataResponse.articleNumber);
        assertEquals("", pdpDataResponse.visualTag);
        assertEquals("Jaipur Kurti Black Printed Top", pdpDataResponse.productDisplayName);
        assertEquals("Jaipurkurti Black Cotton Printed  Top", pdpDataResponse.variantName);
        assertEquals("Jaipur Kurti", pdpDataResponse.brandName);
        assertEquals("Adults-Women", pdpDataResponse.ageGroup);
        assertEquals("Women", pdpDataResponse.gender);
        assertEquals("Black", pdpDataResponse.baseColour);

        assertEquals("NA", pdpDataResponse.colour1);
        assertEquals("NA", pdpDataResponse.colour2);
        assertEquals("Fashion", pdpDataResponse.fashionType);
        assertEquals("Summer", pdpDataResponse.season);
        assertEquals("2016", pdpDataResponse.year);
        assertEquals("Casual", pdpDataResponse.usage);
        assertEquals(0, pdpDataResponse.navigationId);
        assertEquals("Tops/Jaipur-Kurti/Jaipur-Kurti-Black-Printed-Top/1291342/buy",
                pdpDataResponse.landingPageUrl);

        //TODO :: Add more asserts here.
    }

}