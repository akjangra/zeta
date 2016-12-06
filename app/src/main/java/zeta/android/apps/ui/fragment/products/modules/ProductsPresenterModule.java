package zeta.android.apps.ui.fragment.products.modules;

import dagger.Module;
import dagger.Provides;
import zeta.android.apps.di.scope.FragmentScope;
import zeta.android.apps.rx.providers.RxSchedulerProvider;
import zeta.android.apps.ui.fragment.products.presenter.ProductsDetailsPresenter;
import zeta.android.myntra.managers.ProductsManager;

@Module
@FragmentScope
public class ProductsPresenterModule {

    @Provides
    ProductsDetailsPresenter providesHomePresenter(RxSchedulerProvider schedulerProvider,
                                                   ProductsManager productsManager) {
        return new ProductsDetailsPresenter(schedulerProvider, productsManager);
    }

}
