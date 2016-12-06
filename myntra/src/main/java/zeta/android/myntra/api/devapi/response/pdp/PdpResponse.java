package zeta.android.myntra.api.devapi.response.pdp;

import com.google.gson.annotations.SerializedName;

public class PdpResponse {

    @SerializedName("notification")
    public final PdpNotificationResponse pdpNotificationResponse;

    @SerializedName("meta")
    public final PdpMetaResponse pdpMetaResponse;

    @SerializedName("data")
    public final PdpDataResponse pdpDataResponse;

    public PdpResponse(PdpNotificationResponse pdpNotificationResponse,
                       PdpMetaResponse pdpMetaResponse,
                       PdpDataResponse pdpDataResponse) {
        this.pdpNotificationResponse = pdpNotificationResponse;
        this.pdpMetaResponse = pdpMetaResponse;
        this.pdpDataResponse = pdpDataResponse;
    }
}
