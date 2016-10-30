package com.hackmanchester2016.swearjar.ui.home;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by patrickc on 30/10/2016
 */
public class MoneyBagsView extends View {

    private static final float SIDE = 32;

    private static final String MONEY_WINGS = "\uD83D\uDCB8";
    private static final String MONEY_BAG = "\uD83D\uDCB0";
    private static final String MONEY_NOTES = "\uD83D\uDCB7";

    private int fine;
    private Paint paint;
    private Random random;

    private List<PointF> coords = new ArrayList<>();
    private List<String> emojii = new ArrayList<>();

    public MoneyBagsView(Context context) {
        super(context);
        init();
    }

    public MoneyBagsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoneyBagsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(36);

        random = new Random();
    }

    public void sweepUpDaCash(){
        coords.clear();
        emojii.clear();
        invalidate();
    }

    public void makeItRain(int fine){
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "fineProgress", 0, fine/10);
        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0; i < coords.size(); i++){
            PointF point = coords.get(i);
            canvas.drawText(emojii.get(i), point.x, point.y, paint);
        }
    }

    public void setFineProgress(int progress){
        coords.add(new PointF((random.nextFloat() * (getWidth() - 3 * SIDE)) + SIDE, (random.nextFloat() * (getHeight() -  SIDE)) + SIDE));
        emojii.add(getEmoji(progress));
        invalidate();
    }

    private String getEmoji(int progress){
        switch (progress % 3){
            default:
            case 0:
                return MONEY_BAG;
            case 1:
                return MONEY_WINGS;
            case 2:
                return MONEY_NOTES;
        }
    }
}
