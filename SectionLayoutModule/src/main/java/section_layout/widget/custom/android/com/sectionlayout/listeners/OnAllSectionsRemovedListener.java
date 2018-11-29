package section_layout.widget.custom.android.com.sectionlayout.listeners;

import section_layout.widget.custom.android.com.sectionlayout.SectionManager;

/**
 * Only for {@link SectionManager#removeAllSections()} method
 */
public interface OnAllSectionsRemovedListener {
    /**
     * Will be called after all sections removed
     */
    void onAllSectionsRemoved();
}
