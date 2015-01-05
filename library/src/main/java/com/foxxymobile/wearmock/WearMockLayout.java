package com.foxxymobile.wearmock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

/**
 * ViewGroup for the WearMockLayout
 */
public class WearMockLayout extends FrameLayout {


    private Drawable faceDrawable;
    private int containerSize;
    private int skinWidth;
    private int skinHeight;
    private int skinMarginLeft;
    private int skinMarginTop;

    public WearMockLayout(Context context) {
        super(context);
        init();
    }

    public WearMockLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WearMockLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        // Calculate the PPI factor between the skin and the device
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float ppiFactor = metrics.xdpi/getResources().getInteger(R.integer.skin_PPI);

        // Get container size
        containerSize = (int) (getResources().getDimensionPixelSize(R.dimen.container_size)*ppiFactor);
        skinWidth = (int) (getResources().getDimensionPixelSize(R.dimen.skin_width)*ppiFactor);
        skinHeight = (int) (getResources().getDimensionPixelSize(R.dimen.skin_height)*ppiFactor);
        skinMarginLeft = (int) (getResources().getDimensionPixelSize(R.dimen.skin_margin_left)*ppiFactor);
        skinMarginTop = (int) (getResources().getDimensionPixelSize(R.dimen.skin_margin_top)*ppiFactor);


        //setBackgroundResource(android.R.color.white);

        faceDrawable = getResources().getDrawable(R.drawable.lg_g_watch_r);
        setWillNotDraw(false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        View container = getChildAt(0);
        if(container!=null)
            container.measure(MeasureSpec.makeMeasureSpec(containerSize,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(containerSize,MeasureSpec.EXACTLY));

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        View container = getChildAt(0);
        if(container!=null)
            container.layout((right - containerSize) / 2, (bottom - containerSize) / 2, (right - containerSize) / 2 + containerSize, (bottom - containerSize) / 2 + containerSize);

    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        int containerLeft = (getWidth()- containerSize) / 2;
        int containerTop = (getHeight() - containerSize) / 2;
        int skinLeft = containerLeft-skinMarginLeft;
        int skinTop = containerTop-skinMarginTop;

        faceDrawable.setBounds(skinLeft,skinTop,skinLeft+skinWidth,skinTop+skinHeight);
        faceDrawable.draw(canvas);
    }

}