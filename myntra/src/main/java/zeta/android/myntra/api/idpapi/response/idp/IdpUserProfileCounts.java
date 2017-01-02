package zeta.android.myntra.api.idpapi.response.idp;

import com.google.gson.annotations.SerializedName;

public class IdpUserProfileCounts {

    @SerializedName("following")
    public final String following;

    @SerializedName("followers")
    public final String followers;

    public IdpUserProfileCounts(String following, String followers) {
        this.following = following;
        this.followers = followers;
    }
}


