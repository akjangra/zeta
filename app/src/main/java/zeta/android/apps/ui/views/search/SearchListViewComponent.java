package zeta.android.apps.ui.views.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.annotation.ParametersAreNonnullByDefault;

import butterknife.BindView;
import zeta.android.apps.R;
import zeta.android.apps.appconfig.GlideConfigModule;
import zeta.android.apps.ui.common.BaseViews;
import zeta.android.myntra.models.products.ProductGist;
import zeta.android.utils.view.ViewUtils;

@ParametersAreNonnullByDefault
public class SearchListViewComponent extends FrameLayout {

    private Views mViews;

    static class Views extends BaseViews {

        @BindView(R.id.zeta_product_image)
        ImageView productImage;

        @BindView(R.id.zeta_product_image_title)
        TextView imageTitle;

        @BindView(R.id.zeta_product_image_subtitle)
        TextView imageSubTitle;

        Views(View rootView) {
            super(rootView);
        }
    }

    public SearchListViewComponent(Context context) {
        super(context);
        init();
    }

    public SearchListViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchListViewComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setItemData(ProductGist productGist) {
        Context context = getContext();
        Glide.with(context)
                .load(productGist.getProductImage())
                .thumbnail(GlideConfigModule.SIZE_MULTIPLIER)
                .crossFade()
                .into(mViews.productImage);

        final String title = productGist.getProductTitle();
        mViews.imageTitle.setContentDescription(context.getString(R.string.zeta_product_image_cd, title));
        mViews.imageTitle.setText(title);
        mViews.imageSubTitle.setText(productGist.getProductBrand());
        ViewUtils.setMultipleToVisible(mViews.productImage, mViews.imageTitle, mViews.imageSubTitle);
    }

    private void init() {
        final Context context = getContext();
        inflate(context, R.layout.view_product_list_item, this);
        mViews = new Views(this);
    }

}
