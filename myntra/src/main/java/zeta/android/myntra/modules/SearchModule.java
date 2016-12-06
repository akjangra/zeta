package zeta.android.myntra.modules;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import zeta.android.myntra.api.devapi.MyntraDevApi;
import zeta.android.myntra.api.devapi.response.search.SearchResponse;
import zeta.android.myntra.managers.SearchManager;
import zeta.android.myntra.models.common.ITransformer;
import zeta.android.myntra.models.search.SearchModel;
import zeta.android.myntra.models.transformers.SearchModelTransformer;
import zeta.android.myntra.qualifiers.retrofit.RetrofitDevApi;

@Module
public class SearchModule {

    @Provides
    MyntraDevApi providesMyntraApi(@RetrofitDevApi Retrofit retrofit) {
        return retrofit.create(MyntraDevApi.class);
    }

    @Provides
    ITransformer<SearchResponse, SearchModel> providesSearchTransformer() {
        return new SearchModelTransformer();
    }

    @Provides
    SearchManager providesSearchManager(MyntraDevApi devApi,
                                        ITransformer<SearchResponse, SearchModel> transformer) {
        return new SearchManager(devApi, transformer);
    }
}
