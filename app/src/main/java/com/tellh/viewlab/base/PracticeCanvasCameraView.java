package com.tellh.viewlab.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tellh.viewlab.R;

public class PracticeCanvasCameraView extends View {

    public PracticeCanvasCameraView(Context context) {
        super(context);
    }

    public PracticeCanvasCameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeCanvasCameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final Bitmap bitmap;
    private Paint paint = new Paint();
    private Camera camera = new Camera();

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = 100;
        int top = 100;
        canvas.save();

        camera.save();
        camera.rotateX(-50); // 旋转 Camera 的三维空间
        camera.setLocation(0, 0, 40);
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        camera.restore();

        canvas.drawBitmap(bitmap, left, top, paint);

        canvas.restore();

        canvas.save();


        camera.save(); // 保存 Camera 的状态
        camera.rotateX(-30); // 旋转 Camera 的三维空间
        float centerX = bitmap.getWidth() / 2;
        float centerY = bitmap.getHeight() / 2;
        canvas.translate(centerX, centerY); // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(-centerX, -centerY); // 旋转之前把绘制内容移动到轴心（原点）
        camera.restore(); // 恢复 Camera 的状态

        top += 500;
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();
    }
}