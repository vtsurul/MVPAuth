package com.softdesign.mvpauth.ui.custom_views.behaviors;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.softdesign.mvpauth.R;

public class AvatarBehavior<V extends ImageView> extends AppBarLayout.ScrollingViewBehavior {

    private static final String TAG = "AvatarBehavior";

    private final int mMaxAppbarHeight;
    private final int mMaxAvatarHeight;

    private Context mContext;

    public AvatarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        mMaxAppbarHeight = context.getResources().getDimensionPixelOffset(R.dimen.account_header_size);
        mMaxAvatarHeight = context.getResources().getDimensionPixelOffset(R.dimen.account_avatar_size);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float currentFriction = currentFriction(mMaxAppbarHeight, dependency.getBottom());
        int currentHeight = lerp(mMaxAvatarHeight, currentFriction);

        child.setY(dependency.getBottom() - child.getHeight()/2);
        child.setX(parent.getWidth()/2 - mMaxAvatarHeight/2);

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.height = currentHeight;
        child.setLayoutParams(lp);

        return super.onDependentViewChanged(parent, child, dependency);
    }

    private float currentFriction(int end, int currentValue) {
        return (float) currentValue / end;
    }

    private int lerp(int end, float friction) {
        return (int) (end * friction);
    }
}
