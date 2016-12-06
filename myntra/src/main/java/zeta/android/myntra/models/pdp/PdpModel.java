package zeta.android.myntra.models.pdp;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.net.URL;

import zeta.android.myntra.models.products.ProductId;
import zeta.android.myntra.models.search.SearchModel;

@AutoValue
public abstract class PdpModel implements Parcelable {

    public static Builder create() {
        return new AutoValue_PdpModel.Builder();
    }

    public abstract String getProductTitle();

    @Nullable
    public abstract String getProductDescription();

    public abstract ProductId getProductId();

    public abstract URL getDefaultImageUrl();

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder setProductTitle(String productTitle);

        public abstract Builder setProductDescription(@Nullable String productDescription);

        public abstract Builder setProductId(ProductId productId);

        public abstract Builder setDefaultImageUrl(URL imageUrl);

        public abstract PdpModel build();
    }
}
