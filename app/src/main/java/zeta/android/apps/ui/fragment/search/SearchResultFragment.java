package zeta.android.apps.ui.fragment.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import butterknife.BindView;
import zeta.android.apps.R;
import zeta.android.apps.di.component.ZetaAppComponent;
import zeta.android.apps.ui.adapters.SearchAdapter;
import zeta.android.apps.ui.common.BaseViews;
import zeta.android.apps.ui.fragment.common.BaseNavigationFragment;
import zeta.android.apps.ui.fragment.products.ProductDetailsFragment;
import zeta.android.apps.ui.fragment.products.ProductsDetailsFragmentParam;
import zeta.android.apps.ui.fragment.search.presentation.SearchResultPresentation;
import zeta.android.apps.ui.fragment.search.presenter.SearchPresenterParam;
import zeta.android.apps.ui.fragment.search.presenter.SearchResultPresenter;
import zeta.android.myntra.models.products.ProductGist;
import zeta.android.myntra.modules.SearchModule;
import zeta.android.utils.view.ViewUtils;
import zeta.android.view.DividerItemDecoration;

@ParametersAreNonnullByDefault
public class SearchResultFragment extends BaseNavigationFragment implements SearchResultPresentation {

    private static final String ARG_HOME_SAVED_STATE_PRESENTER = "ARG_HOME_SAVED_STATE_PRESENTER";

    private Views mViews;
    private SearchAdapter mListViewAdapter;
    private LinearLayoutManager mListLayoutManager;

    //Saved data
    private Parcelable mSavedState;

    @Inject
    SearchResultPresenter mPresenter;

    static class Views extends BaseViews {

        @BindView(R.id.zeta_progress_bar)
        ProgressBar progressBar;

        @BindView(R.id.zeta_list_view)
        RecyclerView listView;

        Views(View root) {
            super(root);
        }
    }

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public void configureDependencies(ZetaAppComponent component) {
        component.zetaSearchComponent(new SearchModule()).inject(this);
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mViews = new Views(rootView);
        mPresenter.onCreateView(this);
        initAdaptersAndViews();
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
        mListLayoutManager = null;
        mListViewAdapter = null;
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
    public void showProgress(boolean show) {
        ViewUtils.setVisibility(mViews.progressBar, show);
    }

    @Override
    public void showSnackBarMessage(@StringRes int message) {
        Snackbar.make(mViews.getRootView(), getString(message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showListView(boolean show) {
        ViewUtils.setVisibility(mViews.listView, show);
    }

    @Override
    public void showListViewFooter(boolean show) {
        //Do nothing
    }

    @Override
    public void showListViewFooterRetry(boolean show) {
        //Do nothing
    }

    @Override
    public void updateImageAdapters(List<ProductGist> productGists, int previousSize) {
        mListViewAdapter.updateImagesModel(productGists, previousSize);
    }

    @Override
    public void navigateToProductDetailsPage(ProductsDetailsFragmentParam param) {
        getNavigationFragmentManager().addFragmentToBackStack(ProductDetailsFragment.newInstance(param));
    }

    //region internal helper methods
    private void initAdaptersAndViews() {
        final Context context = getContext();
        mListViewAdapter = new SearchAdapter();
        mListLayoutManager = new LinearLayoutManager(context);
        mListLayoutManager.setInitialPrefetchItemCount(4);
        mListLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mViews.listView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        mViews.listView.setHasFixedSize(true);
        mViews.listView.setLayoutManager(mListLayoutManager);
        mViews.listView.setAdapter(mListViewAdapter);
    }

    private void registerClickListeners() {
        mViews.listView.addOnScrollListener(new ProductSummaryListScrollListener());
        mListViewAdapter.setAdapterClickListener(new SearchListAdapterListener());
    }

    private void unRegisterClickListeners() {
        mViews.listView.clearOnScrollListeners();
        mListViewAdapter.setAdapterClickListener(null);
    }

    private SearchPresenterParam getPresenterParams() {
        return SearchPresenterParam.create()
                .setSavedState(mSavedState)
                .build();
    }
    //endregion

    //region listeners
    private class SearchListAdapterListener implements SearchAdapter.SearchAdapterListener {
        @Override
        public void onItemClick(ProductGist productGist) {
            mPresenter.onItemClick(productGist);
        }
    }

    private class ProductSummaryListScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //Do nothing
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int visibleItemCount = mListLayoutManager.getChildCount();
            int totalItemCount = mListLayoutManager.getItemCount();
            int firstVisibleItem = mListLayoutManager.findFirstVisibleItemPosition();
            mPresenter.onPageScrolled(firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
    //endregion

}
