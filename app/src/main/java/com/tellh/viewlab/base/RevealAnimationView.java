package com.tellh.viewlab.base;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;

import com.tellh.viewlab.R;

public class RevealAnimationView extends LinearLayout {

    public RevealAnimationView(Context context) {
        super(context);
    }

    public RevealAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RevealAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // previously invisible view
            View myView = findViewById(R.id.colorView);

            // get the final radius for the clipping circle
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

            // create the animator for this view (the start radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(
                            myView, (int) event.getX(), (int) event.getY(), 0, finalRadius);

            // make the view visible and start the animation
            myView.setVisibility(View.VISIBLE);
            anim.start();
            return true;
        }
        return super.onTouchEvent(event);
    }
}