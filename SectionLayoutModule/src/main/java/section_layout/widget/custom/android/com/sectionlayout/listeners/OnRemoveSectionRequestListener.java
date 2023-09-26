package section_layout.widget.custom.android.com.sectionlayout.listeners;

import androidx.annotation.Nullable;

/**
 * Created by Robert Apikyan on 9/5/2017.
 */

public interface OnRemoveSectionRequestListener<D> {
    /**
     * Will be called before section remove action performing
     *
     * @param sectionData, the removed section data
     */
    boolean onRemoveSectionRequest(@Nullable D sectionData);
}
