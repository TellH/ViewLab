package com.tellh.viewlab.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tellh.viewlab.R;

public class PracticeClipRectView extends View {

    public PracticeClipRectView(Context context) {
        super(context);
    }

    public PracticeClipRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PracticeClipRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final Bitmap bitmap;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path1 = new Path();
    Path path2 = new Path();
    Point point1 = new Point(200, 500);
    Point point2 = new Point(600, 500);
    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
        path1.addCircle(point1.x + 200, point1.y + 200, 150, Path.Direction.CW);

        path2.setFillType(Path.FillType.INVERSE_WINDING);
        path2.addCircle(point2.x + 200, point2.y + 200, 150, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = (getWidth() - bitmap.getWidth()) / 2;
        int top = 100;
        canvas.save();
        canvas.clipRect(left + 50, top + 50, left + 300, top + 400);
        canvas.drawBitmap(bitmap, left, top, paint);
        canvas.restore();

        canvas.save();
        canvas.clipPath(path1);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        canvas.clipPath(path2);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}