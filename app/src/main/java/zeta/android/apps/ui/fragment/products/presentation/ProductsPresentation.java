package zeta.android.apps.ui.fragment.products.presentation;

import android.support.annotation.StringRes;

import java.net.URL;
import java.util.List;

import zeta.android.myntra.models.products.ProductGist;

public interface ProductsPresentation {

    void showProgressBar(boolean show);

    void hideProgressBarAndShowContentContainer();

    void showProductImage(URL imageUrl);

    void showProductTitle(String title);

    void showProductPrice(int price);

    void showProductDescription(String description);

    void showSnackBarMessage(@StringRes int message);

}
