package com.jizhe7550.myapplication.ui.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.jizhe7550.myapplication.ui.widget.SnapGravity.*;
import com.jizhe7550.myapplication.R;

public class GravitySnapRecyclerView extends RecyclerView{

    @NonNull
    final private GravitySnapHelper snapHelper;

    private boolean isSnappingEnabled = false;

    public GravitySnapRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public GravitySnapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GravitySnapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs,
                                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.GravitySnapRecyclerView, defStyleAttr, 0);
        int snapGravity = typedArray.getInt(
                R.styleable.GravitySnapRecyclerView_snapGravity, 0);
        switch (snapGravity) {
            case 0:
                snapHelper = new GravitySnapHelper(START);
                break;
            case 1:
                snapHelper = new GravitySnapHelper(TOP);
                break;
            case 2:
                snapHelper = new GravitySnapHelper(END);
                break;
            case 3:
                snapHelper = new GravitySnapHelper(BOTTOM);
                break;
            case 4:
            default:
                snapHelper = new GravitySnapHelper(CENTER);
                break;
//                throw new IllegalArgumentException("Invalid gravity value. Use START " +
//                        "| END | BOTTOM | TOP | CENTER constants");
        }

        snapHelper.setSnapToPadding(typedArray.getBoolean(
                R.styleable.GravitySnapRecyclerView_snapToPadding, false));

        snapHelper.setSnapLastItem(typedArray.getBoolean(
                R.styleable.GravitySnapRecyclerView_snapLastItem, false));

        snapHelper.setMaxFlingSizeFraction(typedArray.getFloat(
                R.styleable.GravitySnapRecyclerView_snapMaxFlingSizeFraction,
                GravitySnapHelper.FLING_SIZE_FRACTION_DISABLE));

        snapHelper.setScrollMsPerInch(typedArray.getFloat(
                R.styleable.GravitySnapRecyclerView_snapScrollMsPerInch, 100f));

        enableSnapping(typedArray.getBoolean(
                R.styleable.GravitySnapRecyclerView_snapEnabled, true));

        typedArray.recycle();
    }

    @Override
    public void smoothScrollToPosition(int position) {
        if (!isSnappingEnabled || !snapHelper.smoothScrollToPosition(position)) {
            super.smoothScrollToPosition(position);
        }
    }

    @Override
    public void scrollToPosition(int position) {
        if (!isSnappingEnabled || !snapHelper.scrollToPosition(position)) {
            super.scrollToPosition(position);
        }
    }

    @NonNull
    public GravitySnapHelper getSnapHelper() {
        return snapHelper;
    }

    public void enableSnapping(Boolean enable) {
        if (enable) {
            snapHelper.attachToRecyclerView(this);
        } else {
            snapHelper.attachToRecyclerView(null);
        }
        isSnappingEnabled = enable;
    }

    public boolean isSnappingEnabled() {
        return isSnappingEnabled;
    }

    public int getCurrentSnappedPosition() {
        return snapHelper.getCurrentSnappedPosition();
    }

    public void snapToNextPosition(Boolean smooth) {
        snapTo(true, smooth);
    }

    public void snapToPreviousPosition(Boolean smooth) {
        snapTo(false, smooth);
    }

    public void setSnapListener(@Nullable GravitySnapHelper.SnapListener listener) {
        snapHelper.setSnapListener(listener);
    }

    public void setItemViewAnimListener(@Nullable GravitySnapHelper.ItemViewAnimListener listener) {
        snapHelper.setItemViewAnimListener(listener);
    }

    private void snapTo(Boolean next, Boolean smooth) {
        final LayoutManager lm = getLayoutManager();
        if (lm != null) {
            final View snapView = snapHelper.findSnapView(lm, false);
            if (snapView != null) {
                final int pos = getChildAdapterPosition(snapView);
                if (next) {
                    if (smooth) {
                        smoothScrollToPosition(pos + 1);
                    } else {
                        scrollToPosition(pos + 1);
                    }
                } else if (pos > 0) {
                    if (smooth) {
                        smoothScrollToPosition(pos - 1);
                    } else {
                        scrollToPosition(pos - 1);
                    }
                }
            }
        }
    }
}
