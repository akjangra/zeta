package zeta.android.apps.ui.fragment.search.presentation;

import java.util.List;

import zeta.android.apps.ui.fragment.common.presentation.BasePresentation;
import zeta.android.apps.ui.fragment.products.ProductsDetailsFragmentParam;
import zeta.android.myntra.models.products.ProductGist;
import zeta.android.myntra.models.products.ProductId;

public interface SearchResultPresentation extends BasePresentation {

    void showListView(boolean show);

    void showListViewFooter(boolean show);

    void showListViewFooterRetry(boolean show);

    void updateImageAdapters(List<ProductGist> productGists,
                             int previousSize);

    void navigateToProductDetailsPage(ProductsDetailsFragmentParam param);

}
