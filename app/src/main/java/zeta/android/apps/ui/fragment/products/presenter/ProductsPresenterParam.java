package zeta.android.apps.ui.fragment.products.presenter;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import zeta.android.myntra.models.products.ProductId;

@AutoValue
public abstract class ProductsPresenterParam implements Parcelable {

    public static Builder create() {
        return new AutoValue_ProductsPresenterParam.Builder();
    }

    public Builder newBuilder() {
        return new AutoValue_ProductsPresenterParam.Builder(this);
    }

    @Nullable
    public abstract Parcelable getSavedState();

    public abstract ProductId getProductId();

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder setSavedState(@Nullable Parcelable savedState);

        public abstract Builder setProductId(ProductId productId);

        public abstract ProductsPresenterParam build();

    }

}
