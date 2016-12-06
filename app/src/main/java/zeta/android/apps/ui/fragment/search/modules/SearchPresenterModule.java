package zeta.android.apps.ui.fragment.search.modules;

import dagger.Module;
import dagger.Provides;
import zeta.android.apps.di.scope.FragmentScope;
import zeta.android.apps.rx.providers.RxSchedulerProvider;
import zeta.android.apps.ui.fragment.search.presenter.SearchResultPresenter;
import zeta.android.myntra.managers.SearchManager;

@Module
@FragmentScope
public class SearchPresenterModule {

    @Provides
    SearchResultPresenter providesHomePresenter(RxSchedulerProvider schedulerProvider,
                                                SearchManager searchManager) {
        return new SearchResultPresenter(schedulerProvider, searchManager);
    }

}
