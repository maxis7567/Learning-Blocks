package com.nova.maxis7567.tavan.pricing;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import static com.nova.maxis7567.tavan.pricing.PriceCardsPagerAdapter.BIG_SCALE;

public class PriceCardLinearLayout extends ConstraintLayout {
    private float mScale = BIG_SCALE;

    public PriceCardLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PriceCardLinearLayout(Context context) {
        super(context);
    }

    public void setScaleBoth(float scale) {
        this.mScale = scale;
        this.invalidate();    // If you want to see the mScale every time you set
        // mScale you need to have this line here,
        // invalidate() function will call onDraw(Canvas)
        // to redraw the view for you
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // The main mechanism to display mScale animation, you can customize it
        // as your needs
        float w = this.getWidth();
        float h = this.getHeight();
        canvas.scale(mScale, mScale, w / 2, h / 2);

        super.onDraw(canvas);
    }
}