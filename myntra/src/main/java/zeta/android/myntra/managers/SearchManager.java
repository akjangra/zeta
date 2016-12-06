package zeta.android.myntra.managers;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.github.zetaapps.either.Either;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import rx.Observable;
import zeta.android.myntra.api.devapi.MyntraDevApi;
import zeta.android.myntra.api.devapi.response.search.SearchResponse;
import zeta.android.myntra.managers.params.SearchParams;
import zeta.android.myntra.models.common.ITransformer;
import zeta.android.myntra.models.common.Managers;
import zeta.android.myntra.models.search.SearchModel;
import zeta.android.myntra.models.search.errors.SearchException;

@ParametersAreNonnullByDefault
public class SearchManager {

    private final MyntraDevApi devAPi;
    private final ITransformer<SearchResponse, SearchModel> mSearchTransformer;

    @Inject
    public SearchManager(MyntraDevApi devApi, ITransformer<SearchResponse, SearchModel> transformer) {
        devAPi = devApi;
        mSearchTransformer = transformer;
    }

    @RxLogObservable
    public Observable<Either<SearchModel, SearchException>> getSearchResult(SearchParams searchParams) {
        return devAPi.getSearchResultResponse(searchParams.getSearchQuery(),
                searchParams.getPageNumber(),
                searchParams.getPageSize())
                .map(response -> Managers.buildOneOf(
                        response,
                        SearchException::new,
                        mSearchTransformer));
    }


}
