package zeta.android.myntra.models.products;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ProductId implements Parcelable {

    public static Builder create(int productId) {
        return new AutoValue_ProductId.Builder()
                .setRawProductId(productId);
    }

    public abstract int getRawProductId();

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder setRawProductId(int productId);

        public abstract ProductId build();
    }
}
