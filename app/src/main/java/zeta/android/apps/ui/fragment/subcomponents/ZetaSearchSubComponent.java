package zeta.android.apps.ui.fragment.subcomponents;

import dagger.Subcomponent;
import zeta.android.apps.di.scope.FragmentScope;
import zeta.android.apps.ui.fragment.search.SearchResultFragment;
import zeta.android.apps.ui.fragment.search.modules.SearchPresenterModule;
import zeta.android.myntra.modules.SearchModule;

@FragmentScope
@Subcomponent(modules = {
        SearchPresenterModule.class,
        SearchModule.class})
public interface ZetaSearchSubComponent {

    void inject(SearchResultFragment fragment);

}
