package com.example.administrator.customturnpageview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2017/10/16.
 */

public class TurnPageView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degree;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 360);

    public TurnPageView(Context context) {
        super(context);
    }

    public TurnPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TurnPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @SuppressWarnings("unused")
    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        // 第一遍绘制
        canvas.save();
        canvas.rotate(-degree, centerX, centerY);
        canvas.clipRect(0, centerY, getWidth(), getHeight());
        canvas.rotate(degree, centerX, centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        // 第二遍绘制
        canvas.save();

        camera.save();
        canvas.rotate(-degree, centerX, centerY);
        camera.rotateX(-45);
        canvas.translate(centerX, centerY);
        camera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        camera.restore();

        canvas.clipRect(0, 0, getWidth(), centerY);
        canvas.rotate(degree, centerX, centerY);

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}
