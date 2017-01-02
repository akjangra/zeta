package zeta.android.myntra.api.idpapi.response.params;

import com.google.gson.annotations.SerializedName;

public class IdpTokenParams {
    public final String action;

    public final String referer;

    @SerializedName("userId")
    public final String userId;

    public final String password;

    @SerializedName("usertype")
    public final String userType;

    public final String gender;

    public final String title;

    @SerializedName("firstname")
    public final String firstName;

    @SerializedName("lastname")
    public final String lastName;

    public final String email;

    public final String mobile;

    public final String actionToken;

    public IdpTokenParams(String action, String referer, String userId, String password,
                          String userType, String gender, String title, String firstName,
                          String lastName, String email, String mobile, String actionToken) {
        this.action = action;
        this.referer = referer;
        this.userId = userId;
        this.password = password;
        this.userType = userType;
        this.gender = gender;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.actionToken = actionToken;
    }
}
