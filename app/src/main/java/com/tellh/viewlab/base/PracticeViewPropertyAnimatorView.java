package com.tellh.viewlab.base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PracticeViewPropertyAnimatorView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PracticeViewPropertyAnimatorView(Context context) {
        super(context);
    }

    public PracticeViewPropertyAnimatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeViewPropertyAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(300, 300, 300, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
/*            this.animate()
                    .translationX(500)
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(1000)
                    .start();*/

/*            ObjectAnimator.ofFloat(this, "translationX", 500)
                    .setDuration(1000)
                    .start();*/

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(500);
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    PracticeViewPropertyAnimatorView.this.setTranslationX(value);
                }
            });
            valueAnimator.start();
            return true;
        }
        return super.onTouchEvent(event);
    }
}