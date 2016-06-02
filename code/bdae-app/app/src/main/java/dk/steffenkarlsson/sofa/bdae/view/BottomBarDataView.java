package dk.steffenkarlsson.sofa.bdae.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import dk.steffenkarlsson.sofa.bdae.IActivityHandler;
import dk.steffenkarlsson.sofa.bdae.R;

/**
 * Created by steffenkarlsson on 5/31/16.
 */
public class BottomBarDataView extends BasePagerControllerView {

    public BottomBarDataView(Context context) {
        super(context);
    }

    public BottomBarDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean hasOptionsMenu() {
        return false;
    }

    @Override
    public void setContent(IActivityHandler handler) {
        super.setContent(handler);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.content_data_view;
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
