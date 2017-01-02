package zeta.android.myntra.api.idpapi;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import rx.Observable;
import zeta.android.myntra.api.idpapi.response.idp.IdpTokenResponse;
import zeta.android.myntra.api.idpapi.response.params.IdpTokenParams;

@ParametersAreNonnullByDefault
public interface IdpDevApi {

    @GET("idp")
    Observable<Response<IdpTokenResponse>> getLoginInfo(@Body IdpTokenParams params);

}