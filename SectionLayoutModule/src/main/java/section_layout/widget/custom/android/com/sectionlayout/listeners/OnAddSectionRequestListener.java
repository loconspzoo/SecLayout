package section_layout.widget.custom.android.com.sectionlayout.listeners;

import androidx.annotation.Nullable;

/**
 * Created by Robert Apikyan on 9/5/2017.
 */

public interface OnAddSectionRequestListener<D> {
    /**
     * Will be called before section addition
     * @param sectionData, the section data
     * @param sectionPosition, the section position
     * @return true -> the section will be added to section layout: false -> add request will be ignored
     */
    boolean onAddSectionRequest(@Nullable D sectionData, int sectionPosition);
}
