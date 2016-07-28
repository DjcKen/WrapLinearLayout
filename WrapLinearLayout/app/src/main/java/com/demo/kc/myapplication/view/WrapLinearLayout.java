package com.demo.kc.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.kc.myapplication.R;

import java.util.Hashtable;

/**
 * 多行多列的LinearLayout
 *
 * @author Jake
 * @date 2016/7/28
 */
public class WrapLinearLayout extends LinearLayout {

    private static final int DEFAULT_ROWS = 2;
    private static final int DEFAULT_COLUMNS = 2;
    private static final int DEFAULT_HORIZONAL_SPACEING = 10;
    private static final int DEFAULT_VERTICAL_SPACEING = 10;
    private int mRows;//行数
    private int mColumns;//列数
    private float mHorizonalSpaceing;//行距
    private float mVerticalSpaceing;//列距
    private int mLeft, mRight, mTop, mBottom;
    private Hashtable<View, Position> mHashMap = new Hashtable<>();

    public WrapLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public WrapLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WrapLinearLayout);
        mRows = a.getInt(R.styleable.WrapLinearLayout_wrapNumRows, DEFAULT_ROWS);
        mColumns = a.getInt(R.styleable.WrapLinearLayout_wrapNumColumns, DEFAULT_COLUMNS);
        mHorizonalSpaceing = a.getDimension(R.styleable.WrapLinearLayout_wrapHorizonalSpaceing, dipTopx(context, DEFAULT_HORIZONAL_SPACEING));
        mVerticalSpaceing = a.getDimension(R.styleable.WrapLinearLayout_wrapVerticalSpaceing, dipTopx(context, DEFAULT_VERTICAL_SPACEING));
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        int x = 0;
        int y = 0;
        mLeft = 0;
        mRight = 0;
        mTop = 0;
        mBottom = 0;

        int j = 0;
        int row = 0;

        int childWidth = (int) ((width - mHorizonalSpaceing * (mColumns + 1)) / mColumns);
        int childHeight = (int) ((height - mVerticalSpaceing * (mRows + 1)) / mRows);

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
            x += childWidth;
            Position position = new Position();
            mLeft = getPosition(i - j, i, childWidth);
            mRight = mLeft + childWidth;
            if (x > width) {
                x = childWidth;
                y += childHeight;
                j = i;
                row++;
                mLeft = 0;
                mRight = mLeft + childWidth;
                mTop = y;
            }
            mBottom = mTop + childHeight;
            y = mTop;
            int column = i - j;
            position.left = (int) (mLeft + (column == 0 ? mHorizonalSpaceing : mHorizonalSpaceing * (column + 1)));
            position.top = (int) (mTop + (row == 0 ? mVerticalSpaceing : mVerticalSpaceing * (row + 1)));
            position.right = (int) (mRight + (column == 0 ? mHorizonalSpaceing : mHorizonalSpaceing * (column + 1)));
            position.bottom = (int) (mBottom + (row == 0 ? mVerticalSpaceing : mVerticalSpaceing * (row + 1)));
            mHashMap.put(child, position);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Position position = mHashMap.get(child);
            if (position != null) {
                child.layout(position.left, position.top, position.right, position.bottom);
            }
        }
    }

    private class Position {
        int left, top, right, bottom;
    }

    public int getPosition(int indexInRow, int childIndex, int childWidth) {
        if (indexInRow > 0) {
            return getPosition(indexInRow - 1, childIndex - 1, childWidth) + childWidth;
        }
        return getPaddingLeft();
    }

    private int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
