package section_layout.widget.custom.android.com.sectionlayout.distributive_section_layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import section_layout.widget.custom.android.com.sectionlayout.R;
import section_layout.widget.custom.android.com.sectionlayout.SectionLayout;
import section_layout.widget.custom.android.com.sectionlayout.SectionLayoutViewControllerComponent;

/**
 * Created by Robert Apikyan on 9/5/2017.
 */

/**
 * Subclass of {@link SectionLayout}
 * Supports distribute_evenly attribute.
 * distribute_evenly = true, then childes will be measured with equal width.
 * @param <D> Section Data type
 */
public class DistributiveSectionLayout<D> extends SectionLayout<D> {

    public DistributiveSectionLayout(@NonNull Context context) {
        super(context);
    }

    public DistributiveSectionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initArgs(context,attrs,0);
    }

    public DistributiveSectionLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initArgs(context,attrs,defStyleAttr);
    }

    private void initArgs(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DistributiveSectionLayout,defStyleAttr,0);
        try {
            distributeEvenly(typedArray.getBoolean(R.styleable.DistributiveSectionLayout_distribute_evenly,false));
        }finally {
            typedArray.recycle();
        }
    }

    @NonNull
    @Override
    public SectionLayoutViewControllerComponent<D> createControllerComponent() {
        return new DistributiveSectionLayoutControllerComponent<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        ((DistributiveSectionLayoutControllerComponent) getControllerComponent()).measureDistribute(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void distributeEvenly(boolean willDistributeEvenly) {
        DistributiveSectionLayoutControllerComponent.class.cast(getControllerComponent()).distributeEvenly(willDistributeEvenly);
    }
}
