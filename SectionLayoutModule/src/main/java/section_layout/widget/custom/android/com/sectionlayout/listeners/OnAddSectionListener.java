package section_layout.widget.custom.android.com.sectionlayout.listeners;

import android.support.annotation.Nullable;

/**
 * Created by Robert Apikyan on 9/5/2017.
 */

public interface OnAddSectionListener<D> {
    /**
     * Will be called after section addition
     *
     * @param sectionData,     the section data
     * @param sectionPosition, the section position
     */
    void onSectionAdded(@Nullable D sectionData, int sectionPosition);
}
