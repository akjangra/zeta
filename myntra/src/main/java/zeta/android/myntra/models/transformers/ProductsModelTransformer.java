package zeta.android.myntra.models.transformers;

import javax.annotation.ParametersAreNonnullByDefault;

import zeta.android.myntra.api.devapi.response.pdp.PdpDataResponse;
import zeta.android.myntra.api.devapi.response.pdp.PdpResponse;
import zeta.android.myntra.models.common.ITransformer;
import zeta.android.myntra.models.pdp.PdpModel;
import zeta.android.myntra.models.products.ProductId;

@ParametersAreNonnullByDefault
public class ProductsModelTransformer implements ITransformer<PdpResponse, PdpModel> {

    @Override
    public PdpModel transform(PdpResponse pdpResponse) {
        PdpDataResponse pdpDataResponse = pdpResponse.pdpDataResponse;

        return PdpModel.create()
                .setProductId(ProductId.create(pdpDataResponse.productId).build())
                .setProductTitle(pdpDataResponse.productDisplayName)
                .setDefaultImageUrl(pdpDataResponse.styleImages.defaultImages.imageURL)
                .build();
    }
}