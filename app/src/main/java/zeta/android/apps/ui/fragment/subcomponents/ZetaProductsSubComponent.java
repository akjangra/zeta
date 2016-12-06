package zeta.android.apps.ui.fragment.subcomponents;

import dagger.Subcomponent;
import zeta.android.apps.di.scope.FragmentScope;
import zeta.android.apps.ui.fragment.products.ProductDetailsFragment;
import zeta.android.apps.ui.fragment.products.modules.ProductsPresenterModule;
import zeta.android.myntra.modules.ProductsModule;

@FragmentScope
@Subcomponent(modules = {
        ProductsPresenterModule.class,
        ProductsModule.class})
public interface ZetaProductsSubComponent {

    void inject(ProductDetailsFragment fragment);

}
