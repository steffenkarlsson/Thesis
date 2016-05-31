package dk.steffenkarlsson.sofa.bdae;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import dk.steffenkarlsson.sofa.bdae.extra.TransitionAnimation;

/**
 * Created by steffenkarlsson on 5/31/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String BUNDLE_TRANSITION = "BUNDLE_TRANSITION";

    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Nullable
    @Bind(R.id.progress)
    protected ProgressBar mLoadingSpinner;

    private TransitionAnimation mOutTransition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOutTransition = TransitionAnimation.values()[getIntent().getIntExtra(BUNDLE_TRANSITION, 0)];
        getIntent().removeExtra(BUNDLE_TRANSITION);

        int layoutRes = getLayoutResource();
        if (layoutRes == -1)
            throw new RuntimeException("No layout defined in getLayoutResource");

        setContentView(layoutRes);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hasLoadingSpinner())
            mLoadingSpinner.getIndeterminateDrawable().setColorFilter(
                    getResources().getColor(R.color.colorSpinner),
                    android.graphics.PorterDuff.Mode.MULTIPLY);

        initActionBar();
    }

    protected void initActionBar() {
        if (showActionBar()) {
            setSupportActionBar(mToolbar);

            if (getTitleResource() != -1)
                setActionbarTitle(getTitleResource());

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                if (showBackButton()) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        }
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    @StringRes
    protected abstract int getTitleResource();

    protected boolean hasLoadingSpinner() {
        return true;
    }

    protected boolean showBackButton() {
        return false;
    }

    protected boolean showActionBar() {
        return true;
    }

    protected void setActionbarTitle(int titleResId) {
        setActionbarTitle(getString(titleResId));
    }

    protected void setActionbarTitle(String title) {
        if (getSupportActionBar() != null && showActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected void setLoadingSpinnerVisible(boolean visible) {
        if (hasLoadingSpinner())
            mLoadingSpinner.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        applyBackTransition();
    }

    protected void applyBackTransition() {
        overridePendingTransition(
                TransitionAnimation.getAnimation(TransitionAnimation.FADE_IN),
                TransitionAnimation.getOutAnimation(mOutTransition)
        );
    }

    public void launchActivity(Intent intent, TransitionAnimation transitionAnimation) {
        intent.putExtra(BUNDLE_TRANSITION, transitionAnimation.ordinal());
        startActivity(intent);
        overridePendingTransition(
                TransitionAnimation.getAnimation(transitionAnimation),
                TransitionAnimation.getAnimation(TransitionAnimation.FADE_OUT)
        );
    }

    public void launchActivityForResult(Intent intent, int requestCode, TransitionAnimation transitionAnimation) {
        intent.putExtra(BUNDLE_TRANSITION, transitionAnimation.ordinal());
        startActivityForResult(intent, requestCode);
        overridePendingTransition(
                TransitionAnimation.getAnimation(transitionAnimation),
                TransitionAnimation.getAnimation(TransitionAnimation.FADE_OUT)
        );
    }

    public Intent getActivityIntent(Context context, Class clzz) {
        return getActivityIntent(context, clzz, null);
    }

    public Intent getActivityIntent(Context context, Class clzz, Bundle extras) {
        Intent intent = new Intent(context, clzz);
        if (extras != null)
            intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
