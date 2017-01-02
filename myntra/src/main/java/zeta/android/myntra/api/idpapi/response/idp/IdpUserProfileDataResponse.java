package zeta.android.myntra.api.idpapi.response.idp;

import com.google.gson.annotations.SerializedName;

public class IdpUserProfileDataResponse {

    @SerializedName("login")
    public final String login;

    @SerializedName("usertype")
    public final String userType;

    @SerializedName("firstname")
    public final String firstName;

    @SerializedName("email")
    public final String email;

    @SerializedName("mobile")
    public final String mobile;

    @SerializedName("status")
    public final String status;

    @SerializedName("lastlogin")
    public final String lastLogin;

    @SerializedName("firstlogin")
    public final String firstLogin;

    @SerializedName("gender")
    public final String gender;

    @SerializedName("uidx")
    public final String uidx;

    @SerializedName("dob")
    public final String dateOfBirth;

    @SerializedName("new_")
    public final String isNew;

    @SerializedName("profile")
    public final IdpUserProfileResponse profile;

    public IdpUserProfileDataResponse(String login, String userType, String firstName, String email,
                                      String mobile, String status, String lastLogin,
                                      String firstLogin, String gender, String uidx,
                                      String dateOfBirth, String isNew, IdpUserProfileResponse profile) {
        this.login = login;
        this.userType = userType;
        this.firstName = firstName;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
        this.lastLogin = lastLogin;
        this.firstLogin = firstLogin;
        this.gender = gender;
        this.uidx = uidx;
        this.dateOfBirth = dateOfBirth;
        this.isNew = isNew;
        this.profile = profile;
    }
}

