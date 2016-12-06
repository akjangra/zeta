package zeta.android.apps.ui.fragment.products;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import zeta.android.myntra.models.products.ProductId;

@AutoValue
public abstract class ProductsDetailsFragmentParam implements Parcelable {

    public static Builder create(ProductId productId) {
        return new AutoValue_ProductsDetailsFragmentParam.Builder()
                .setProductId(productId);
    }

    public Builder newBuilder() {
        return new AutoValue_ProductsDetailsFragmentParam.Builder(this);
    }

    public abstract ProductId getProductId();

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder setProductId(ProductId productId);

        public abstract ProductsDetailsFragmentParam build();

    }

}
