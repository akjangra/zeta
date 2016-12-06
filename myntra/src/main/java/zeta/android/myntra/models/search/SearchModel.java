package zeta.android.myntra.models.search;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

import zeta.android.myntra.models.products.ProductGist;

@AutoValue
public abstract class SearchModel implements Parcelable {

    public static Builder create() {
        return new AutoValue_SearchModel.Builder()
                .setPageSize(20)
                .setPageNumber(1);
    }

    @Nullable
    public abstract String getSearchQuery();

    public abstract List<ProductGist> getProducts();

    public abstract int getPageSize();

    public abstract int getPageNumber();

    public abstract long getTotalSearchCount();

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setSearchQuery(@Nullable String searchQuery);

        public abstract Builder setProducts(List<ProductGist> products);

        public abstract Builder setPageSize(int pageSize);

        public abstract Builder setPageNumber(int pageNumber);

        public abstract Builder setTotalSearchCount(long totalSearchCount);

        public abstract SearchModel build();
    }
}
