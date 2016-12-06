package zeta.android.apps.di.component;

import javax.inject.Singleton;

import dagger.Component;
import zeta.android.apps.di.module.*;
import zeta.android.apps.ZetaApplication;
import zeta.android.apps.ui.fragment.subcomponents.ZetaProductsSubComponent;
import zeta.android.apps.ui.fragment.subcomponents.ZetaSearchSubComponent;
import zeta.android.myntra.MyntraEngineModule;
import zeta.android.myntra.modules.ProductsModule;
import zeta.android.myntra.modules.SearchModule;

@Singleton
@Component(modules = {
        DebugModule.class,
        NetworkModule.class,
        EventBusModule.class,
        ZetaAppModule.class,
        OkHttpInterceptorsModule.class,
        EventBusNoSubscriberModule.class,
        ConfigModule.class,
        MyntraEngineModule.class})
public interface ZetaAppComponent {

    ZetaSearchSubComponent zetaSearchComponent(SearchModule searchModule);

    ZetaProductsSubComponent zetaProductsComponent(ProductsModule productModule);

    NavigationActivityComponent navigationActivity();

    DebugComponent debugComponent();

    void inject(ZetaApplication targetApplication);

}
