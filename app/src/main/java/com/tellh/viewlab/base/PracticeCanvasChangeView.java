package com.tellh.viewlab.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tellh.viewlab.R;

public class PracticeCanvasChangeView extends View {

    public PracticeCanvasChangeView(Context context) {
        super(context);
    }

    public PracticeCanvasChangeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeCanvasChangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final Bitmap bitmap;
    private Paint paint = new Paint();

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = 0;
        int top = 0;
        canvas.drawBitmap(bitmap, left, top, paint);
        // 平移，旋转变换
        // 先旋转后平移，顺序反着写
        canvas.save();
        canvas.translate(400, 0);
        canvas.rotate(45, bitmap.getWidth() * 0.5f, bitmap.getHeight() * 0.5f);
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();

        // 缩放变换
        canvas.save();
        top = +800;
        canvas.scale(0.5f, 0.5f, left + bitmap.getWidth() / 2, top + bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();

        // 错切
        canvas.save();
        canvas.skew(0, 0.5f);
        top += 300;
        canvas.scale(0.5f, 0.5f, left + bitmap.getWidth() / 2, top + bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();

    }
}