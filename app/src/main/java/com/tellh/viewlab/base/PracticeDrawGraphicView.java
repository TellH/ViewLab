package com.tellh.viewlab.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PracticeDrawGraphicView extends View {
    private Paint paint;

    public PracticeDrawGraphicView(Context context) {
        super(context);
        init();
    }

    public PracticeDrawGraphicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PracticeDrawGraphicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.reset();
        // 实心圆
        canvas.drawCircle(300, 300, 100, paint);

        // 空心圆
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(600, 300, 100, paint);

        // 蓝色实心圆
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(600, 600, 100, paint);

        // 线宽为 20 的空心圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(300, 600, 100, paint);

        // 画矩形
        paint.reset();
        canvas.drawRect(200, 900, 500, 1050, paint);

        // 画点
        paint.reset();
        paint.setStrokeWidth(50);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(600, 900, paint);

        paint.reset();
        paint.setStrokeWidth(50);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(700, 900, paint);

        // 画椭圆
        paint.reset();
        canvas.drawOval(new RectF(200, 1200, 500, 1350), paint);

        // 划线
        paint.reset();
        paint.setStrokeWidth(20);
        canvas.drawLine(600, 1200, 900, 1350, paint);

        // 画弧形
        /*
        startAngle 是弧形的起始角度（x 轴的正向，即正右的方向，是 0 度的位置；顺时针为正角度，逆时针为负角度），
        sweepAngle 是弧形划过的角度；
        useCenter 表示是否连接到圆心，如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形。
         */
        paint.reset();
        canvas.drawArc(200, 1400, 800, 2000, -110, 100, true, paint); // 绘制扇形
        canvas.drawArc(200, 1400, 800, 2000, 20, 140, false, paint); // 绘制弧形
        paint.setStyle(Paint.Style.STROKE); // 画线模式
        paint.setStrokeWidth(10);
        canvas.drawArc(200, 1400, 800, 2000, 180, 60, false, paint); // 绘制弧线
    }
}