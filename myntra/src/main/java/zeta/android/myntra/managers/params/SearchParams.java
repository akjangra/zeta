package zeta.android.myntra.managers.params;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SearchParams implements Parcelable {

    public static Builder create() {
        return new AutoValue_SearchParams.Builder();
    }

    public abstract String getSearchQuery();

    public abstract int getPageNumber();

    public abstract int getPageSize();

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder setSearchQuery(String searchQuery);

        public abstract Builder setPageNumber(int pageNumber);

        public abstract Builder setPageSize(int pageSize);

        public abstract SearchParams build();
    }

}
