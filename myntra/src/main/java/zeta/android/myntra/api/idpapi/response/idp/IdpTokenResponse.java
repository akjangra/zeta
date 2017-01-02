package zeta.android.myntra.api.idpapi.response.idp;

import com.google.gson.annotations.SerializedName;

public class IdpTokenResponse {

    @SerializedName("meta")
    public final IdpMetaResponse profileMeta;

    @SerializedName("notification")
    public final IdpNotificationResponse dataNotification;

    @SerializedName("data")
    public final IdpUserProfileResponse profileData;

    public IdpTokenResponse(IdpMetaResponse profileMeta,
                            IdpNotificationResponse dataNotification,
                            IdpUserProfileResponse profileData) {
        this.profileMeta = profileMeta;
        this.dataNotification = dataNotification;
        this.profileData = profileData;
    }
}
