/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tellh.viewlab.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * A transition between a FAB & another surface using a circular reveal moving along an arc.
 * <p>
 * See: https://www.google.com/design/spec/motion/transforming-material.html#transforming-material-radial-transformation
 */
public class FabTransform extends Transition {

    private static final String EXTRA_FAB_COLOR = "EXTRA_FAB_COLOR";
    private static final String EXTRA_FAB_ICON_RES_ID = "EXTRA_FAB_ICON_RES_ID";
    private static final long DEFAULT_DURATION = 240L;
    private static final String PROP_BOUNDS = "viewlab:fabTransform:bounds";
    private static final String[] TRANSITION_PROPERTIES = {
            PROP_BOUNDS
    };

    private final int color;
    private final int icon;
    private Interpolator interpolator;

    public FabTransform(@ColorInt int fabColor, @DrawableRes int fabIconResId) {
        color = fabColor;
        icon = fabIconResId;
        setDuration(DEFAULT_DURATION);
    }

/*
    public FabTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.FabTransform);
            if (!a.hasValue(R.styleable.FabTransform_fabColor)
                    || !a.hasValue(R.styleable.FabTransform_fabIcon)) {
                throw new IllegalArgumentException("Must provide both color & icon.");
            }
            color = a.getColor(R.styleable.FabTransform_fabColor, Color.TRANSPARENT);
            icon = a.getResourceId(R.styleable.FabTransform_fabIcon, 0);
            if (getDuration() < 0) {
                setDuration(DEFAULT_DURATION);
            }
        } finally {
            a.recycle();
        }
    }
*/

    /**
     * Configure {@code intent} with the extras needed to initialize this transition.
     */
    public static void addExtras(@NonNull Intent intent, @ColorInt int fabColor,
                                 @DrawableRes int fabIconResId) {
        intent.putExtra(EXTRA_FAB_COLOR, fabColor);
        intent.putExtra(EXTRA_FAB_ICON_RES_ID, fabIconResId);
    }

    /**
     * Create a {@link FabTransform} from the supplied {@code activity} extras and set as its
     * shared element enter/return transition.
     */
    public static boolean setup(@NonNull Activity activity, @Nullable View target) {
        final Intent intent = activity.getIntent();
        if (!intent.hasExtra(EXTRA_FAB_COLOR) || !intent.hasExtra(EXTRA_FAB_ICON_RES_ID)) {
            return false;
        }

        final int color = intent.getIntExtra(EXTRA_FAB_COLOR, Color.TRANSPARENT);
        final int icon = intent.getIntExtra(EXTRA_FAB_ICON_RES_ID, -1);
        final FabTransform sharedEnter = new FabTransform(color, icon);
        if (target != null) {
            sharedEnter.addTarget(target);
        }
        activity.getWindow().setSharedElementEnterTransition(sharedEnter);
        return true;
    }

    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(final ViewGroup sceneRoot,
                                   final TransitionValues startValues,
                                   final TransitionValues endValues) {
        if (startValues == null || endValues == null) return null;

        final Rect startBounds = (Rect) startValues.values.get(PROP_BOUNDS);
        final Rect endBounds = (Rect) endValues.values.get(PROP_BOUNDS);

        final boolean fromFab = endBounds.width() > startBounds.width();
        final View view = startValues.view; // startValues和endValues里的View都是同一个View---Dialog
        final Rect dialogBounds = fromFab ? endBounds : startBounds;
        final Rect fabBounds = fromFab ? startBounds : endBounds;
        interpolator =
                AnimationUtils.loadInterpolator(sceneRoot.getContext(),
                        android.R.interpolator.fast_out_slow_in);
        final long duration = getDuration();
        final long halfDuration = duration / 2;
        final long twoThirdsDuration = duration * 2 / 3;

        if (!fromFab) { // 将Dialog的状态在动画变换之前还原
//             Force measure / layout the dialog back to it's original bounds
            view.measure(
                    makeMeasureSpec(dialogBounds.width(), View.MeasureSpec.EXACTLY),
                    makeMeasureSpec(dialogBounds.height(), View.MeasureSpec.EXACTLY));
            view.layout(dialogBounds.left, dialogBounds.top, dialogBounds.right, dialogBounds.bottom);
        }

        final int translationX = startBounds.centerX() - endBounds.centerX();
        final int translationY = startBounds.centerY() - endBounds.centerY();
        if (fromFab) { // 将Fab的状态在动画变换之间还原
            view.setTranslationX(translationX);
            view.setTranslationY(translationY);
        }

        // Add a color overlay to fake appearance of the FAB
        // 通过用背景色和图标作为前置面具来将Dialog伪装成fab
        final ColorDrawable fabColor = new ColorDrawable(color);
        fabColor.setBounds(0, 0, dialogBounds.width(), dialogBounds.height());
        if (!fromFab) fabColor.setAlpha(0);
        view.getOverlay().add(fabColor);

        // Add an icon overlay again to fake the appearance of the FAB
        final Drawable fabIcon =
                ContextCompat.getDrawable(sceneRoot.getContext(), icon).mutate();
        final int iconLeft = (dialogBounds.width() - fabIcon.getIntrinsicWidth()) / 2;
        final int iconTop = (dialogBounds.height() - fabIcon.getIntrinsicHeight()) / 2;
        fabIcon.setBounds(iconLeft, iconTop,
                iconLeft + fabIcon.getIntrinsicWidth(),
                iconTop + fabIcon.getIntrinsicHeight());
        if (!fromFab) fabIcon.setAlpha(0);
        view.getOverlay().add(fabIcon);

        // Circular clip from/to the FAB size
        // 用Reveal动画来进行进场和退场
        final Animator circularReveal;
        if (fromFab) {
            circularReveal = ViewAnimationUtils.createCircularReveal(view,
                    view.getWidth() / 2,
                    view.getHeight() / 2,
                    startBounds.width() / 2,
                    (float) Math.hypot(endBounds.width() / 2, endBounds.height() / 2));
            circularReveal.setInterpolator(interpolator);
        } else {
            circularReveal = ViewAnimationUtils.createCircularReveal(view,
                    view.getWidth() / 2,
                    view.getHeight() / 2,
                    (float) Math.hypot(startBounds.width() / 2, startBounds.height() / 2),
                    endBounds.width() / 2);
            circularReveal.setInterpolator(interpolator);

            // Persist the end clip i.e. stay at FAB size after the reveal has run
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setOutlineProvider(new ViewOutlineProvider() {
                        @Override
                        public void getOutline(View view, Outline outline) {
                            final int left = (view.getWidth() - fabBounds.width()) / 2;
                            final int top = (view.getHeight() - fabBounds.height()) / 2;
                            outline.setOval(
                                    left, top, left + fabBounds.width(), top + fabBounds.height());
                            view.setClipToOutline(true);
                        }
                    });
                }
            });
        }
        circularReveal.setDuration(duration);

        // Translate to end position along an arc
        // 变换时加上沿路径移动的动画
        final Animator translate = ObjectAnimator.ofFloat(
                view,
                View.TRANSLATION_X,
                View.TRANSLATION_Y,
                fromFab ? getPathMotion().getPath(translationX, translationY, 0, 0)
                        : getPathMotion().getPath(0, 0, -translationX, -translationY));
        translate.setDuration(duration);
        translate.setInterpolator(interpolator);

        // Fade contents of non-FAB view in/out
        // Dialog内容渐变
        List<Animator> fadeContents = null;
        if (view instanceof ViewGroup) {
            final ViewGroup vg = ((ViewGroup) view);
            fadeContents = new ArrayList<>(vg.getChildCount());
            for (int i = vg.getChildCount() - 1; i >= 0; i--) {
                final View child = vg.getChildAt(i);
                final Animator fade =
                        ObjectAnimator.ofFloat(child, View.ALPHA, fromFab ? 1f : 0f);
                if (fromFab) {
                    child.setAlpha(0f);
                }
                fade.setDuration(twoThirdsDuration);
                fade.setInterpolator(interpolator);
                fadeContents.add(fade);
            }
        }

        // Fade in/out the fab color & icon overlays
        final Animator colorFade = ObjectAnimator.ofInt(fabColor, "alpha", fromFab ? 0 : 255);
        final Animator iconFade = ObjectAnimator.ofInt(fabIcon, "alpha", fromFab ? 0 : 255);
        if (!fromFab) {
            colorFade.setStartDelay(halfDuration);
            iconFade.setStartDelay(halfDuration);
        }
        colorFade.setDuration(halfDuration);
        iconFade.setDuration(halfDuration);
        colorFade.setInterpolator(interpolator);
        iconFade.setInterpolator(interpolator);

        // Work around issue with elevation shadows. At the end of the return transition the shared
        // element's shadow is drawn twice (by each activity) which is jarring. This workaround
        // still causes the shadow to snap, but it's better than seeing it double drawn.
        Animator elevation = null;
        if (!fromFab) {
            elevation = ObjectAnimator.ofFloat(view, View.TRANSLATION_Z, -view.getElevation());
            elevation.setDuration(duration);
            elevation.setInterpolator(interpolator);
        }

        // Run all animations together
        final AnimatorSet transition = new AnimatorSet();
        transition.playTogether(circularReveal, translate, colorFade, iconFade);
        transition.playTogether(fadeContents);
        if (elevation != null) transition.play(elevation);
        if (fromFab) {
            transition.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // Clean up
                    view.getOverlay().clear();
                }
            });
        }
        return transition;
    }

    private void captureValues(TransitionValues transitionValues) {
        final View view = transitionValues.view;
        if (view == null || view.getWidth() <= 0 || view.getHeight() <= 0) return;

        transitionValues.values.put(PROP_BOUNDS, new Rect(view.getLeft(), view.getTop(),
                view.getRight(), view.getBottom()));
    }
}
