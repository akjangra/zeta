package zeta.android.myntra.api.devapi.response.search;

import com.google.gson.annotations.SerializedName;

public class SearchInfoResponse {

    public final String query;

    @SerializedName("start")
    public final int startIndex;

    @SerializedName("rows")
    public final int noOfRows;

    public SearchInfoResponse(String query,
                              int startIndex,
                              int noOfRows) {
        this.query = query;
        this.startIndex = startIndex;
        this.noOfRows = noOfRows;
    }

}
