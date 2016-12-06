package zeta.android.apps.ui.fragment.products;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.bundler.FragmentBundlerCompat;

import java.net.URL;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import butterknife.BindView;
import zeta.android.apps.R;
import zeta.android.apps.di.component.ZetaAppComponent;
import zeta.android.apps.ui.common.BaseViews;
import zeta.android.apps.ui.fragment.common.BaseNavigationFragment;
import zeta.android.apps.ui.fragment.products.presentation.ProductsPresentation;
import zeta.android.apps.ui.fragment.products.presenter.ProductsDetailsPresenter;
import zeta.android.apps.ui.fragment.products.presenter.ProductsPresenterParam;
import zeta.android.myntra.modules.ProductsModule;
import zeta.android.utils.view.ViewUtils;

@ParametersAreNonnullByDefault
public class ProductDetailsFragment extends BaseNavigationFragment implements ProductsPresentation {

    private static final String ARG_BUNDLE_PDP_PARAMS = "ARG_BUNDLE_PDP_PARAMS";
    private static final String ARG_HOME_SAVED_STATE_PRESENTER = "ARG_HOME_SAVED_STATE_PRESENTER";

    private Views mViews;

    //Saved data
    private Parcelable mSavedState;

    @Inject
    ProductsDetailsPresenter mPresenter;

    static class Views extends BaseViews {

        @BindView(R.id.zeta_progress_bar)
        ProgressBar progressBar;

        @BindView(R.id.zeta_pdp_scroll_view)
        ScrollView scrollContainer;

        @BindView(R.id.zeta_pdp_image_view)
        ImageView imageView;

        @BindView(R.id.zeta_pdp_title)
        TextView title;

        @BindView(R.id.zeta_pdp_price)
        TextView price;

        @BindView(R.id.zeta_pdp_description)
        TextView description;

        Views(View root) {
            super(root);
        }
    }

    public static ProductDetailsFragment newInstance(ProductsDetailsFragmentParam param) {
        return FragmentBundlerCompat.create(new ProductDetailsFragment())
                .put(ARG_BUNDLE_PDP_PARAMS, param)
                .build();
    }

    @Override
    public void configureDependencies(ZetaAppComponent component) {
        component.zetaProductsComponent(new ProductsModule()).inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
        if (savedInstance != null) {
            mSavedState = savedInstance.getParcelable(ARG_HOME_SAVED_STATE_PRESENTER);
        }
        mPresenter.onCreate(getPresenterParams());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_details, container, false);
        mViews = new Views(rootView);
        mPresenter.onCreateView(this);
        registerClickListeners();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewCreated();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ARG_HOME_SAVED_STATE_PRESENTER, mPresenter.getSavedState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unRegisterClickListeners();
        mPresenter.onDestroyView();
        mViews.clear();
        mViews = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mPresenter = null;
    }

    @Override
    public void showProgressBar(boolean show) {
        ViewUtils.setVisibility(mViews.progressBar, show);
    }

    @Override
    public void hideProgressBarAndShowContentContainer() {
        ViewUtils.showFirstAndHideOthers(mViews.scrollContainer, mViews.progressBar);
    }

    @Override
    public void showProductImage(URL imageUrl) {
        Glide.with(getContext())
                .load(imageUrl.toString())
                .crossFade()
                .thumbnail(0.2f)
                .into(mViews.imageView);
        ViewUtils.setToVisible(mViews.imageView);
    }

    @Override
    public void showProductTitle(String title) {
        mViews.title.setText(title);
    }

    @Override
    public void showProductPrice(int price) {

    }

    @Override
    public void showProductDescription(String description) {
        mViews.description.setText(description);
    }

    @Override
    public void showSnackBarMessage(@StringRes int message) {
        Snackbar.make(mViews.getRootView(), getString(message), Snackbar.LENGTH_LONG).show();
    }

    //region internal helper methods
    private void registerClickListeners() {

    }

    private void unRegisterClickListeners() {

    }

    private ProductsDetailsFragmentParam getProductDetailsParams() {
        return getArguments().getParcelable(ARG_BUNDLE_PDP_PARAMS);
    }

    private ProductsPresenterParam getPresenterParams() {
        ProductsDetailsFragmentParam productDetailsParams = getProductDetailsParams();
        return ProductsPresenterParam.create()
                .setProductId(productDetailsParams.getProductId())
                .setSavedState(mSavedState)
                .build();
    }
    //endregion
}
