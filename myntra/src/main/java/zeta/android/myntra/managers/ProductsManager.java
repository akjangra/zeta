package zeta.android.myntra.managers;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.github.zetaapps.either.Either;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import rx.Observable;
import zeta.android.myntra.api.devapi.MyntraDevApi;
import zeta.android.myntra.api.devapi.response.pdp.PdpResponse;
import zeta.android.myntra.managers.params.ProductDetailsParams;
import zeta.android.myntra.models.common.ITransformer;
import zeta.android.myntra.models.common.Managers;
import zeta.android.myntra.models.pdp.PdpModel;
import zeta.android.myntra.models.pdp.errors.PdpException;

@ParametersAreNonnullByDefault
public class ProductsManager {

    private final MyntraDevApi devAPi;
    private final ITransformer<PdpResponse, PdpModel> mProductsTransformer;

    @Inject
    public ProductsManager(MyntraDevApi devApi, ITransformer<PdpResponse, PdpModel> transformer) {
        devAPi = devApi;
        mProductsTransformer = transformer;
    }

    @RxLogObservable
    public Observable<Either<PdpModel, PdpException>> getProductDetails(ProductDetailsParams productDetailsParams) {
        return devAPi.getProductDetailsResponse(productDetailsParams.getProductId().getRawProductId())
                .map(response -> Managers.buildOneOf(
                        response,
                        PdpException::new,
                        mProductsTransformer));
    }

}
