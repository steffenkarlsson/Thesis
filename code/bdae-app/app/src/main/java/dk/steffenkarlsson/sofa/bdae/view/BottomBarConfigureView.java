package dk.steffenkarlsson.sofa.bdae.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dk.steffenkarlsson.sofa.bdae.R;
import dk.steffenkarlsson.sofa.bdae.extra.ViewCache;

/**
 * Created by steffenkarlsson on 5/31/16.
 */
public class BottomBarConfigureView extends BaseFrameLayout implements ViewCache.ICacheableView {

    public BottomBarConfigureView(Context context) {
        super(context);
    }

    public BottomBarConfigureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.content_configure_view;
    }

    @Override
    public boolean shouldCache() {
        return true;
    }

    @Override
    public View getRoot() {
        return this;
    }
}
