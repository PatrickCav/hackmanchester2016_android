package com.hackmanchester2016.swearjar.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.hackmanchester2016.swearjar.R;

/**
 * Created by dant on 29/10/2016.
 */

public class MaterialPageIndicator extends View implements ViewPager.OnPageChangeListener{

    private static final String TAG = "MaterialPageIndicator";

    private Paint emptyIndicatorPaint = new Paint();

    private Paint fullIndicatorPaint = new Paint();

    private ViewPager viewPager;

    private float indicatorSpacing;

    private float indicatorSize;

    private int currentPage = 0;

    private float scrollOffset = 0;

    private RectF activeIndicatorBounds = new RectF();

    public MaterialPageIndicator(Context context) {

        super(context);

    }

    public MaterialPageIndicator(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(attrs);

    }

    public MaterialPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        init(attrs);

    }

    private void setupPaints(){

        emptyIndicatorPaint.setAntiAlias(true);

        fullIndicatorPaint.setAntiAlias(true);

    }

    private void init(AttributeSet attrs){

        TypedArray attributesArray = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialPageIndicator, 0, 0);

        emptyIndicatorPaint.setColor(attributesArray.getColor(R.styleable.MaterialPageIndicator_emptyStateColor, Color.WHITE));

        fullIndicatorPaint.setColor(attributesArray.getColor(R.styleable.MaterialPageIndicator_filledStateColor, Color.BLACK));

        indicatorSize = attributesArray.getDimension(R.styleable.MaterialPageIndicator_indicatorSize,
                getContext().getResources().getDimension(R.dimen.default_indicator_spacing));

        indicatorSpacing = attributesArray.getDimension(R.styleable.MaterialPageIndicator_indicatorSpacing,
                getContext().getResources().getDimension(R.dimen.default_indicator_spacing));

        attributesArray.recycle();

        setupPaints();

    }

    public void setViewPager(ViewPager viewPager){

        this.viewPager = viewPager;

        viewPager.addOnPageChangeListener(this);

        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        float width = getMeasuredWidth();

        float height = getMeasuredHeight();

        int numberOfPages = getNumberOfPages();

        float edge = (width - ((numberOfPages * indicatorSize) + (numberOfPages - 1) * indicatorSpacing))/2;

        float centreVertical = height/2;

        float radius = indicatorSize/2;

        drawEmptyDots(canvas, edge, centreVertical, radius);

        drawActivePage(canvas, edge, centreVertical, radius);

    }

    private void drawActivePage(Canvas canvas, float leftEdge, float centreVertical, float radius){

        leftEdge += currentPage * (indicatorSize + indicatorSpacing);

        float indicatorWidth;

        if(scrollOffset <= 0.5){

            indicatorWidth = indicatorSize + 2 * scrollOffset * (indicatorSize + indicatorSpacing);

        } else{

            indicatorWidth = indicatorSize + 2 * (1 - scrollOffset) * (indicatorSize + indicatorSpacing);

            leftEdge += 2 * (scrollOffset - 0.5f) * (indicatorSize + indicatorSpacing);

        }

        activeIndicatorBounds.set(leftEdge, centreVertical - radius, leftEdge + indicatorWidth, centreVertical + radius);

        canvas.drawRoundRect(activeIndicatorBounds, radius, radius, fullIndicatorPaint);

    }

    private void drawEmptyDots(Canvas canvas, float leftEdge, float centreVertical, float radius){

        int numberOfPages = getNumberOfPages();

        for(int i = 0; i < numberOfPages; i++){

            canvas.drawCircle(leftEdge + radius, centreVertical, radius, emptyIndicatorPaint);

            leftEdge += (indicatorSize + indicatorSpacing);

        }

    }

    private int getNumberOfPages(){

        int pages = 0;

        if(viewPager != null && viewPager.getAdapter() != null){

            pages = viewPager.getAdapter().getCount();

        }

        if(isInEditMode()){

            pages = 1;

        }

        return pages;

    }

    @Override

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        scrollOffset = positionOffset;

        currentPage = position;

        invalidate();

    }

    @Override

    public void onPageSelected(int position) { }

    @Override

    public void onPageScrollStateChanged(int state) { }

}