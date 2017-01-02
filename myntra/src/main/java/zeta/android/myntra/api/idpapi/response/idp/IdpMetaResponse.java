package zeta.android.myntra.api.idpapi.response.idp;

import com.google.gson.annotations.SerializedName;

public class IdpMetaResponse {

    @SerializedName("code")
    public final String responseCode;

    @SerializedName("token")
    public final String token;

    @SerializedName("xsrfToken")
    public final String xsrfToken;

    public IdpMetaResponse(String responseCode, String token, String xsrfToken) {
        this.responseCode = responseCode;
        this.token = token;
        this.xsrfToken = xsrfToken;
    }
}
