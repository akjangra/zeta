package zeta.android.myntra.modules;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import zeta.android.myntra.api.devapi.MyntraDevApi;
import zeta.android.myntra.api.devapi.response.pdp.PdpResponse;
import zeta.android.myntra.managers.ProductsManager;
import zeta.android.myntra.models.common.ITransformer;
import zeta.android.myntra.models.pdp.PdpModel;
import zeta.android.myntra.models.transformers.ProductsModelTransformer;
import zeta.android.myntra.qualifiers.retrofit.RetrofitDevApi;

@Module
public class ProductsModule {

    @Provides
    MyntraDevApi providesMyntraDevApi(@RetrofitDevApi Retrofit retrofit) {
        return retrofit.create(MyntraDevApi.class);
    }

    @Provides
    ITransformer<PdpResponse, PdpModel> providesProductsTransformer() {
        return new ProductsModelTransformer();
    }

    @Provides
    ProductsManager providesProductManager(MyntraDevApi devApi,
                                           ITransformer<PdpResponse, PdpModel> transformer) {
        return new ProductsManager(devApi, transformer);
    }

}
