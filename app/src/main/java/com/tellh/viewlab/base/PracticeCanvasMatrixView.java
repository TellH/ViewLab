package com.tellh.viewlab.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tellh.viewlab.R;

public class PracticeCanvasMatrixView extends View {

    public PracticeCanvasMatrixView(Context context) {
        super(context);
    }

    public PracticeCanvasMatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeCanvasMatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final Bitmap bitmap;
    private Paint paint = new Paint();
    private Matrix matrix = new Matrix();

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = 0;
        int top = 0;
        matrix.reset();
        // 平移，旋转变换
        matrix.postRotate(45, bitmap.getWidth() * 0.5f, bitmap.getHeight() * 0.5f);
        matrix.postTranslate(400, 0);
        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();

    }
}