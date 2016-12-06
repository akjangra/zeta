package zeta.android.myntra.utils;

import java.net.URL;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ImageUtils {

    private static final String MYNTRA_ASSETS_DOMAIN_1 = "https://assets.myntassets.com";
    private static final String MYNTRA_ASSETS_DOMAIN_2 = "https://myntra.myntassets.com";

    public static String getCloudnaryImageUrl(URL imageUrl) {
        StringBuilder modifiedImageUrl = new StringBuilder(MYNTRA_ASSETS_DOMAIN_1);
        String host = imageUrl.getHost();
        modifiedImageUrl.append("/w_100,q_60/");
        modifiedImageUrl.append(imageUrl.getPath());
        return modifiedImageUrl.toString();
    }

}
