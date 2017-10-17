package com.tellh.viewlab.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tlh on 2017/10/11 :)
 */

public class HeartView extends View {
    private Paint paint;
    private Path path;
    private int viewWith;
    private int viewHeight;

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        path = new Path();
        viewHeight = 700;
        viewWith = 700;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path.addArc(200, 200, 400, 400, -225, 225);
            path.arcTo(400, 200, 600, 400, -180, 225, false);
            path.lineTo(400, 542);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(viewWith, MeasureSpec.getMode(widthMeasureSpec)),
                MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.getMode(heightMeasureSpec)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}
