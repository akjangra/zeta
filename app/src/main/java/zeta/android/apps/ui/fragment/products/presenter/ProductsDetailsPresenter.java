package zeta.android.apps.ui.fragment.products.presenter;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.auto.value.AutoValue;

import javax.annotation.ParametersAreNonnullByDefault;

import zeta.android.apps.R;
import zeta.android.apps.presenter.ZetaRxFragmentLifeCyclePresenter;
import zeta.android.apps.rx.providers.RxSchedulerProvider;
import zeta.android.apps.rx.subscriber.ZetaSubscriber;
import zeta.android.apps.ui.fragment.products.presentation.ProductsPresentation;
import zeta.android.myntra.managers.ProductsManager;
import zeta.android.myntra.managers.params.ProductDetailsParams;
import zeta.android.myntra.models.pdp.PdpModel;
import zeta.android.myntra.models.pdp.errors.PdpException;
import zeta.android.myntra.models.products.ProductId;

@ParametersAreNonnullByDefault
public class ProductsDetailsPresenter extends ZetaRxFragmentLifeCyclePresenter<ProductsPresentation> {

    private ProductsPresentation mPresentation;

    private ProductsManager mProductsManager;
    private ProductsPresenterParam mPresenterParam;

    //Saved data
    private PdpModel mSavedPdpModel;

    public ProductsDetailsPresenter(RxSchedulerProvider schedulerProvider, ProductsManager productsManager) {
        super(schedulerProvider);
        mProductsManager = productsManager;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //no op
    }

    public void onCreate(ProductsPresenterParam presenterParam) {
        mPresenterParam = presenterParam;
        final Parcelable savedState = mPresenterParam.getSavedState();
        if (savedState != null) {
            setSavedState(savedState);
        }
    }

    @Override
    public void onCreateView(ProductsPresentation homePresentation) {
        mPresentation = homePresentation;
    }

    @Override
    public void onViewCreated() {
        if (hasProductDetailsData()) {
            showProductDetailsResult(mSavedPdpModel);
        } else {
            requestProductDetailsResponse(mPresenterParam.getProductId());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresentation = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterParam = null;
        mProductsManager = null;
    }

    //region saved data
    public Parcelable getSavedState() {
        return SavedState.create()
                .setPdpModel(mSavedPdpModel)
                .build();
    }

    public void setSavedState(Parcelable savedState) {
        SavedState state = (SavedState) savedState;
        mSavedPdpModel = state.getPdpModel();
    }

    //endregion
    private void requestProductDetailsResponse(ProductId productId) {
        mPresentation.showProgressBar(true);
        final ProductDetailsParams productDetailsParams = ProductDetailsParams.create(productId).build();
        subscribe(mProductsManager.getProductDetails(productDetailsParams),
                new ZetaSubscriber<PdpModel, PdpException>() {

                    @Override
                    protected void onSuccess(PdpModel success) {
                        mSavedPdpModel = success;
                        showProductDetailsResult(success);
                    }

                    @Override
                    protected void onFailure(@Nullable PdpException failure) {
                        mPresentation.showProgressBar(false);
                    }

                    @Override
                    protected void onNoNetworkFailure() {
                        mPresentation.showProgressBar(false);
                        mPresentation.showSnackBarMessage(R.string.zeta_no_network);
                    }
                });
    }

    private boolean hasProductDetailsData() {
        return mSavedPdpModel != null;
    }

    private void showProductDetailsResult(PdpModel pdpModel) {
        mPresentation.hideProgressBarAndShowContentContainer();
        mPresentation.showProductImage(pdpModel.getDefaultImageUrl());
        mPresentation.showProductTitle(pdpModel.getProductTitle());
        mPresentation.showProductDescription(pdpModel.getProductDescription());
    }

    //region saved instance
    @AutoValue
    static abstract class SavedState implements Parcelable {

        public static Builder create() {
            return new AutoValue_ProductsDetailsPresenter_SavedState.Builder();
        }

        @Nullable
        public abstract PdpModel getPdpModel();

        @AutoValue.Builder
        public static abstract class Builder {

            public abstract Builder setPdpModel(@Nullable PdpModel pdpModel);

            public abstract SavedState build();
        }
    }
    //end region

}
