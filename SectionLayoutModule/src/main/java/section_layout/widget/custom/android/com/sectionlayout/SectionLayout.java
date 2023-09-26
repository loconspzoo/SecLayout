package section_layout.widget.custom.android.com.sectionlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.AttrRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnAddSectionListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnAddSectionRequestListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnAllSectionsRemoveRequestListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnAllSectionsRemovedListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnRemoveSectionListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnRemoveSectionRequestListener;
import view_component.lib_android.com.view_component.base_view.layouts.ComponentLinearLayout;

/**
 * Basic usage
 * sectionLayout data type is defined as String;
 * <p>
 * sectionLayout.withAdapter(new ItemAdapter())
 * .addSection("section one")
 * .addSection("section two");
 *
 * @param <D> Section Data type
 */
public class SectionLayout<D> extends ComponentLinearLayout<SectionLayoutViewComponent, SectionLayoutViewControllerComponent<D>> {

    public SectionLayout(@NonNull Context context) {
        super(context);
    }

    public SectionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SectionLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NonNull
    @Override
    public SectionLayoutViewComponent createViewComponent(@NonNull LayoutInflater layoutInflater) {
        return new SectionLayoutViewComponent(this);
    }

    @NonNull
    @Override
    public SectionLayoutViewControllerComponent<D> createControllerComponent() {
        return new SectionLayoutViewControllerComponent<>();
    }

    public SectionManager<D> withAdapter(@NonNull Adapter adapter) {
        return getControllerComponent().withAdapter(adapter);
    }

    public SectionManager<D> addSection(D sectionData) {
        return getControllerComponent().addSection(sectionData);
    }

    public SectionManager<D> addNullableSection() {
        return getControllerComponent().addNullableSection();
    }

    public SectionManager<D> insertSectionAtPosition(D sectionData, @IntRange(from = 0) int sectionPosition) {
        return getControllerComponent().insertSectionAtPosition(sectionData, sectionPosition);
    }

    public SectionManager<D> removeFirstSectionWithData(D sectionData) {
        return getControllerComponent().removeFirstSectionWithData(sectionData);
    }

    public SectionManager<D> removeAllSectionsWithData(D sectionData) {
        return getControllerComponent().removeAllSectionsWithData(sectionData);
    }

    public SectionManager<D> removeLastSection() {
        return getControllerComponent().removeLastSection();
    }

    public SectionManager<D> removeFirstSection() {
        return getControllerComponent().removeFirstSection();
    }

    public SectionManager<D> removeSectionAtPosition(@IntRange(from = 0) int sectionPosition) {
        return getControllerComponent().removeSectionAtPosition(sectionPosition);
    }

    public SectionManager<D> removeAllSections() {
        return getControllerComponent().removeAllSections();
    }

    public ViewHolder<D> getViewHolderForAdapterPosition(int adapterPosition) {
        return getControllerComponent().getViewHolderForAdapterPosition(adapterPosition);
    }

    @Nullable
    public ViewHolder<D> getFirstViewHolderWithData(D sectionData) {
        return getControllerComponent().getFirstViewHolderWithData(sectionData);
    }

    /**
     * Use this method to fetch current items count
     *
     * @return items count
     */
    public int size() {
        return getControllerComponent().size();
    }

    public void setOnAddSectionListener(OnAddSectionListener<D> onAddSectionListener) {
        getControllerComponent().setOnAddSectionListener(onAddSectionListener);
    }

    public void setOnRemoveSectionListener(OnRemoveSectionListener onRemoveSectionListener) {
        getControllerComponent().setOnRemoveSectionListener(onRemoveSectionListener);
    }

    public void setOnAddSectionRequestListener(OnAddSectionRequestListener<D> onAddSectionRequestListener) {
        getControllerComponent().setOnAddSectionRequestListener(onAddSectionRequestListener);
    }

    public void setOnRemoveSectionRequestListener(OnRemoveSectionRequestListener<D> onRemoveSectionRequestListener) {
        getControllerComponent().setOnRemoveSectionRequestListener(onRemoveSectionRequestListener);
    }

    public void setOnAllSectionsRemovedListener(OnAllSectionsRemovedListener onAllSectionsRemovedListener) {
        getControllerComponent().setOnAllSectionsRemovedListener(onAllSectionsRemovedListener);
    }

    public void setOnAllSectionsRemoveRequestListener(OnAllSectionsRemoveRequestListener onAllSectionsRemoveRequestListener) {
        getControllerComponent().setOnAllSectionsRemoveRequestListener(onAllSectionsRemoveRequestListener);
    }

    public OnAddSectionListener<D> getOnAddSectionListener() {
        return getControllerComponent().getOnAddSectionListener();
    }

    public OnRemoveSectionListener getOnRemoveSectionListener() {
        return getControllerComponent().getOnRemoveSectionListener();
    }

    public OnAddSectionRequestListener<D> getOnAddSectionRequestListener() {
        return getControllerComponent().getOnAddSectionRequestListener();
    }

    public OnRemoveSectionRequestListener<D> getOnRemoveSectionRequestListener() {
        return getControllerComponent().getOnRemoveSectionRequestListener();
    }

    public OnAllSectionsRemovedListener getOnAllSectionsRemovedListener() {
        return getControllerComponent().getOnAllSectionsRemovedListener();
    }

    public OnAllSectionsRemoveRequestListener getOnAllSectionsRemoveRequestListener() {
        return getControllerComponent().getOnAllSectionsRemoveRequestListener();
    }

    /**
     * @param <D> View holders Data type
     */
    public abstract static class ViewHolder<D> {
        private int sectionPosition;
        private final View sectionView;
        private D sectionData;

        public ViewHolder(@NonNull View sectionView) {
            this.sectionView = sectionView;
        }

        /**
         * This method will be called inside {@link Adapter#onBindViewHolder(ViewHolder, Object, int)} method,
         * in order to pass view holders data
         *
         * @param sectionData Nullable section data
         */
        final void bind(@Nullable D sectionData) {
            this.sectionData = sectionData;
            onBind(sectionData);
        }

        /**
         * @return section position
         */
        public int getSectionPosition() {
            return sectionPosition;
        }

        /**
         * @return Section root view
         */
        public View getSectionView() {
            return sectionView;
        }

        /**
         * @return Nullable section data,
         */
        @Nullable
        public D getSectionData() {
            return sectionData;
        }

        void setSectionPosition(int sectionPosition) {
            this.sectionPosition = sectionPosition;
        }

        /**
         * This method will be called inside view holders {@link #bind(Object)} method, after setting sectionData
         *
         * @param sectionData, Section view holders data
         */
        protected abstract void onBind(D sectionData);
    }

    /**
     * Make subclass of {@link Adapter} and pass inside {@link SectionLayout#withAdapter(Adapter)} method
     *
     * @param <D> Section item data type
     */
    public abstract static class Adapter<D, VH extends ViewHolder<D>> {
        private static final int DEFAULT_VIEW_TYPE = 0xfffe8a7b;

        /**
         * Method will be called inside {@link SectionManager#insertSectionAtPosition(Object, int)} method in order
         * to create {@link NonNull} view holder;
         *
         * @param layoutInflater, NonNull LayoutInflater
         * @param viewType,       Item view type, Override method {@link Adapter#getViewType(Object, int)} and return view type depending on
         *                        sectionData or sectionPosition. Default value is {@link Adapter#DEFAULT_VIEW_TYPE}
         * @return NonNull ViewHolder
         */
        @NonNull
        protected abstract VH onCreateViewHolder(@NonNull LayoutInflater layoutInflater, ViewGroup parent, int viewType);

        /**
         * Method will be called inside {@link SectionManager#insertSectionAtPosition(Object, int)} method,
         * in order to pass section data inside NonNull viewHolder
         *
         * @param viewHolder,      NonNull viewHolder
         * @param sectionData,     Nullable sectionData, {@link SectionManager#addNullableSection()}
         * @param sectionPosition, the section position;
         */
        protected void onBindViewHolder(@NonNull VH viewHolder, @Nullable D sectionData, @IntRange(from = 0) int sectionPosition) {
            viewHolder.bind(sectionData);
        }

        /**
         * Override this method and return your View type depending on sectionData and sectionPosition values
         *
         * @param sectionData     Nullable section data
         * @param sectionPosition the section position
         * @return view type. Default value is {@link Adapter#DEFAULT_VIEW_TYPE}
         */
        protected int getViewType(@Nullable D sectionData, int sectionPosition) {
            return DEFAULT_VIEW_TYPE;
        }
    }
}
