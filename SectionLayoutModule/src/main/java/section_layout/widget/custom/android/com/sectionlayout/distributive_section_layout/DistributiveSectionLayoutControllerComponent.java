package section_layout.widget.custom.android.com.sectionlayout.distributive_section_layout;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import section_layout.widget.custom.android.com.sectionlayout.SectionLayoutViewComponent;
import section_layout.widget.custom.android.com.sectionlayout.SectionLayoutViewControllerComponent;

/**
 * Created by Robert Apikyan on 9/5/2017.
 */

public class DistributiveSectionLayoutControllerComponent<D> extends SectionLayoutViewControllerComponent<D> {
    private boolean willDistributeEvenly;

    void distributeEvenly(boolean willDistributeEvenly) {
        this.willDistributeEvenly = willDistributeEvenly;
    }

    /**
     * measure items width, and make them equal to each other
     */
    void measureDistribute(int widthMeasureSpec) {
        SectionLayoutViewComponent viewComponent = getViewComponent();
        //noinspection ConstantConditions, measureDistributeEvenly
        ViewGroup parent = viewComponent.getRootViewGroup();
        if (parent.getChildCount() > 0) {
            int maxWidth = 0;

            if (isWidthSetToMatchParent(parent)) {
                // MATCH PARENT
                if (willDistributeEvenly) {
                    maxWidth = View.MeasureSpec.getSize(widthMeasureSpec) / parent.getChildCount();
                    setChildesWidth(maxWidth, parent);
                } else {
                    //MATCH PARENT and distributeEvenly = false
                    double maxLength = 0;
                    double k;
                    View child;
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        child = parent.getChildAt(i);
                        maxLength += child.getMeasuredWidth();
                    }

                    k = (View.MeasureSpec.getSize(widthMeasureSpec)) / maxLength;
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        child = parent.getChildAt(i);
                        child.getLayoutParams().width = (int) (child.getMeasuredWidth() * k);
                    }
                }
            } else if (isWidthSetToWrapContent(parent)) {
                //WRAP CONTENT and distributeEvenly = true
                if (willDistributeEvenly) {
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        int nextChildWidth = parent.getChildAt(i).getMeasuredWidth();
                        maxWidth = Math.max(maxWidth, nextChildWidth);
                        setChildesWidth(maxWidth, parent);
                    }
                }// if wrap content and distributeEvenly = false, no need to measure
            } else {
                // if parent has fixed size and distributeEvenly = true
                if (willDistributeEvenly) {
                    maxWidth = parent.getLayoutParams().width / parent.getChildCount();
                    setChildesWidth(maxWidth, parent);
                }
            }
        }
    }

    private void setChildesWidth(int maxWidth, ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            parent.getChildAt(i).getLayoutParams().width = maxWidth;
        }
    }

    private boolean isWidthSetToMatchParent(ViewGroup parent) {
        return LinearLayout.LayoutParams.MATCH_PARENT == parent.getLayoutParams().width;
    }

    private boolean isWidthSetToWrapContent(ViewGroup parent) {
        return LinearLayout.LayoutParams.WRAP_CONTENT == parent.getLayoutParams().width;
    }
}
