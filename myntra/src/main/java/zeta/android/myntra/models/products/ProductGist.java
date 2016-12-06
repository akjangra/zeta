package zeta.android.myntra.models.products;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ProductGist implements Parcelable {

    public static Builder create() {
        return new AutoValue_ProductGist.Builder();
    }

    public abstract int getDiscountedPrice();

    public abstract int getRegularPrice();

    public abstract ProductId getProductId();

    public abstract String getProductTitle();

    public abstract String getProductImage();

    @Nullable
    public abstract String getProductBrand();

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder setDiscountedPrice(int discountedPrice);

        public abstract Builder setRegularPrice(int regularPrice);

        public abstract Builder setProductId(ProductId productId);

        public abstract Builder setProductTitle(String productTitle);

        public abstract Builder setProductImage(String productImage);

        public abstract Builder setProductBrand(@Nullable String productBrand);

        public abstract ProductGist build();
    }

}
