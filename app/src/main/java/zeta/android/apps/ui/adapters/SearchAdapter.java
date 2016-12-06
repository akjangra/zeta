package zeta.android.apps.ui.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import zeta.android.adapter.BaseHeaderAndFooterAdapter;
import zeta.android.apps.R;
import zeta.android.apps.ui.views.search.SearchListViewComponent;
import zeta.android.myntra.models.products.ProductGist;

@ParametersAreNonnullByDefault
public class SearchAdapter extends BaseHeaderAndFooterAdapter {

    private List<ProductGist> mProductGists;
    private SearchAdapterListener mListener;

    public SearchAdapter() {
        mProductGists = new ArrayList<>(0);
    }

    public void setAdapterClickListener(@Nullable SearchAdapterListener listener) {
        mListener = listener;
    }

    public void updateImagesModel(List<ProductGist> productGists, int previousSize) {
        mProductGists = productGists;
        if (previousSize == 0) {
            notifyDataSetChanged();
        } else {
            final int positionStart = previousSize + 1;
            notifyItemRangeInserted(positionStart, mProductGists.size());
        }
    }

    @Override
    protected boolean shouldShowHeader() {
        return false;
    }

    @Override
    protected boolean shouldShowFooter() {
        return false;
    }

    @Override
    protected View onCreateHeaderView(ViewGroup viewGroup) {
        return null;
    }

    @Override
    protected View onCreateFooterView(ViewGroup viewGroup) {
        return null;
    }

    @Override
    protected View onCreateRegularView(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.view_flickr_list_item_inflatable, parent, false);
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {
        //Do nothing
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
        //Do nothing
    }

    @Override
    protected int getRegularItemCount() {
        return mProductGists.size();
    }

    @Override
    protected int getRegularItemViewType(int i) {
        return 0;
    }

    @Override
    protected void onBindRegularViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ProductGist productGist = mProductGists.get(position);
        final SearchListViewComponent listViewComponent = ((SearchListViewComponent) viewHolder.itemView);
        listViewComponent.setItemData(productGist);
        listViewComponent.setOnClickListener(v -> {
            if (mListener == null) {
                return;
            }
            mListener.onItemClick(productGist);
        });
    }

    public interface SearchAdapterListener {
        void onItemClick(ProductGist productGist);
    }
}
