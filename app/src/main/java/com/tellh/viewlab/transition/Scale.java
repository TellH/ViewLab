package com.tellh.viewlab.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tlh on 2017/11/2 :)
 */

public class Scale extends Visibility {

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        view.setScaleX(0);
        view.setScaleY(0);
        final ObjectAnimator animScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1);
        final ObjectAnimator animScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1);
        final AnimatorSet transition = new AnimatorSet();
        transition.playTogether(animScaleX, animScaleY);
        return transition;
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        view.setScaleX(1);
        view.setScaleY(1);
        final ObjectAnimator animScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0);
        final ObjectAnimator animScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0);
        final AnimatorSet transition = new AnimatorSet();
        transition.playTogether(animScaleX, animScaleY);
        return transition;
    }
}
