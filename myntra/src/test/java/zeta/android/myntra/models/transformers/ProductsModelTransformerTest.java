package zeta.android.myntra.models.transformers;

import org.junit.Before;
import org.junit.Test;

import javax.annotation.ParametersAreNonnullByDefault;

import zeta.android.myntra.api.devapi.response.pdp.PdpResponse;
import zeta.android.myntra.models.pdp.PdpModel;
import zeta.android.myntra.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ParametersAreNonnullByDefault
public class ProductsModelTransformerTest {

    private ProductsModelTransformer mProductsModelTransformer;

    @Before
    public void setUp() throws Exception {
        mProductsModelTransformer = new ProductsModelTransformer();
    }

    @Test
    public void testPdpResponse() {
        PdpModel pdpResponse = getPdpResponse();
        assert pdpResponse != null;

        assertEquals(1291342, pdpResponse.getProductId().getRawProductId());
        assertEquals("Jaipur Kurti Black Printed Top", pdpResponse.getProductTitle());
        assertNull(pdpResponse.getProductDescription());

        //TODO :: Add more asserts here.
    }

    private PdpModel getPdpResponse() {
        PdpResponse response = TestUtils.deserialize("pdp_response_1291342.json", PdpResponse.class);
        return mProductsModelTransformer.transform(response);
    }

}