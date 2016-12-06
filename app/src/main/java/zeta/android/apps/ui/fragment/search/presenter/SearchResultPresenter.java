package zeta.android.apps.ui.fragment.search.presenter;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import zeta.android.apps.R;
import zeta.android.apps.presenter.ZetaRxFragmentLifeCyclePresenter;
import zeta.android.apps.rx.providers.RxSchedulerProvider;
import zeta.android.apps.rx.subscriber.ZetaSubscriber;
import zeta.android.apps.ui.fragment.products.ProductsDetailsFragmentParam;
import zeta.android.apps.ui.fragment.search.presentation.SearchResultPresentation;
import zeta.android.myntra.managers.SearchManager;
import zeta.android.myntra.managers.params.SearchParams;
import zeta.android.myntra.models.products.ProductGist;
import zeta.android.myntra.models.search.SearchModel;
import zeta.android.myntra.models.search.errors.SearchException;

@ParametersAreNonnullByDefault
public class SearchResultPresenter extends ZetaRxFragmentLifeCyclePresenter<SearchResultPresentation> {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_START_INDEX = 1;

    private SearchResultPresentation mPresentation;

    private SearchManager mMyntraSearchManager;
    private SearchPresenterParam mPresenterParam;

    private boolean mIsSearchRequestLoading;

    //Saved data
    private int mSavedPreviousSize;
    private int mSavedCurrentPage;
    private long mSavedTotalRecordCount;
    private List<ProductGist> mSavedSearchResults = new ArrayList<>(0);

    public SearchResultPresenter(RxSchedulerProvider schedulerProvider, SearchManager searchManager) {
        super(schedulerProvider);
        mMyntraSearchManager = searchManager;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //no op
    }

    public void onCreate(SearchPresenterParam presenterParam) {
        mPresenterParam = presenterParam;
        final Parcelable savedState = mPresenterParam.getSavedState();
        if (savedState != null) {
            setSavedState(savedState);
        }
    }

    @Override
    public void onCreateView(SearchResultPresentation homePresentation) {
        mPresentation = homePresentation;
    }

    @Override
    public void onViewCreated() {
        if (hasSearchResultData()) {
            showSearchResult(mSavedSearchResults, mSavedPreviousSize);
        } else {
            requestMynytraSearchResponse("nike", DEFAULT_PAGE_START_INDEX);
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
        mMyntraSearchManager = null;
    }

    public void onItemClick(ProductGist productGist) {
        mPresentation.navigateToProductDetailsPage(ProductsDetailsFragmentParam.create(productGist.getProductId()).build());
    }

    public void onPageScrolled(int firstVisibleItemPosition, int visibleItemCount, int totalItemCount) {
        if (shouldLoadNextPage(firstVisibleItemPosition, visibleItemCount, totalItemCount)) {
            mPresentation.showListViewFooter(true);
            //Request next page here
            requestMynytraSearchResponse("nike", mSavedCurrentPage + 1);
        }
    }

    public void onFooterRetryClicked() {
        mPresentation.showListViewFooterRetry(false);
        requestMynytraSearchResponse("nike", mSavedCurrentPage + 1);
    }

    //region saved data
    public Parcelable getSavedState() {
        return SavedState.create()
                .setPreviousPageSize(mSavedPreviousSize)
                .setProductGist(mSavedSearchResults)
                .setCurrentPageNumber(mSavedCurrentPage)
                .setTotalRecordCount(mSavedTotalRecordCount)
                .build();
    }

    public void setSavedState(Parcelable savedState) {
        SavedState state = (SavedState) savedState;
        mSavedPreviousSize = state.getPreviousPageSize();
        mSavedSearchResults = state.getProductGist();
        mSavedCurrentPage = state.getCurrentPageNumber();
        mSavedTotalRecordCount = state.getTotalRecordCount();
    }
    //endregion

    private void requestMynytraSearchResponse(String searchQuery, int currentPage) {
        mPresentation.showProgress(true);
        final SearchParams searchParams = SearchParams.create()
                .setPageNumber(currentPage)
                .setPageSize(DEFAULT_PAGE_SIZE)
                .setSearchQuery(searchQuery)
                .build();
        mIsSearchRequestLoading = true;
        subscribe(mMyntraSearchManager.getSearchResult(searchParams),
                new ZetaSubscriber<SearchModel, SearchException>() {
                    @Override
                    protected void onSuccess(SearchModel success) {
                        synchronized (SearchResultPresenter.this) {
                            //We have data show
                            mIsSearchRequestLoading = false;
                            mSavedPreviousSize = mSavedSearchResults.size();
                            mSavedSearchResults.addAll(success.getProducts());
                            mSavedCurrentPage = success.getPageNumber();
                            mSavedTotalRecordCount = success.getTotalSearchCount();
                        }
                        showSearchResult(mSavedSearchResults, mSavedPreviousSize);
                    }

                    @Override
                    protected void onFailure(@Nullable SearchException failure) {
                        mIsSearchRequestLoading = false;
                        mPresentation.showSnackBarMessage(R.string.zeta_error_loading);
                        mPresentation.showProgress(false);
                    }

                    @Override
                    protected void onNoNetworkFailure() {
                        mIsSearchRequestLoading = false;
                        mPresentation.showSnackBarMessage(R.string.zeta_no_network);
                        mPresentation.showProgress(false);
                    }
                });

    }

    private boolean shouldLoadNextPage(final int firstVisibleItem,
                                       final int visibleItemCount,
                                       final int totalItemCount) {
        boolean hasMoreRecords = (totalItemCount < getTotalRecordCount());
        if (visibleItemCount > 0 && hasMoreRecords) {
            boolean metTriggerPoint = (firstVisibleItem + visibleItemCount) >= (totalItemCount - (getPageSize() / 2));
            return (metTriggerPoint && !mIsSearchRequestLoading);
        }
        return false;
    }

    private boolean hasSearchResultData() {
        return mSavedSearchResults != null && !mSavedSearchResults.isEmpty();
    }

    private long getTotalRecordCount() {
        return mSavedTotalRecordCount;
    }

    private int getPageSize() {
        return mSavedSearchResults != null ? mSavedSearchResults.size() : 0;
    }

    private void showSearchResult(List<ProductGist> productGists, int previousSize) {
        mPresentation.updateImageAdapters(productGists, previousSize);
        mPresentation.showProgress(false);
        mPresentation.showListView(true);
        mPresentation.showListViewFooter(false);
    }

    //region saved instance
    @AutoValue
    static abstract class SavedState implements Parcelable {

        public static Builder create() {
            return new AutoValue_SearchResultPresenter_SavedState.Builder()
                    .setPreviousPageSize(0);
        }

        public abstract int getPreviousPageSize();

        public abstract int getCurrentPageNumber();

        public abstract long getTotalRecordCount();

        @Nullable
        public abstract List<ProductGist> getProductGist();

        @AutoValue.Builder
        public static abstract class Builder {

            public abstract Builder setPreviousPageSize(int previousPageSize);

            public abstract Builder setProductGist(@Nullable List<ProductGist> productGists);

            public abstract Builder setCurrentPageNumber(int currentPageNumber);

            public abstract Builder setTotalRecordCount(long totalRecordCount);

            public abstract SavedState build();
        }
    }
    //end region

}
