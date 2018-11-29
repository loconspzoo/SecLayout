package section_layout.widget.custom.android.com.sectionlayout;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

/**
 * Created by Robert Apikyan on 8/23/2017.
 */

public interface SectionManager<D> {
    /**
     * Add section to section layout with last item position
     *
     * @param sectionData Nullable section data
     * @return SectionManager
     */
    SectionManager<D> addSection(@Nullable D sectionData);

    /**
     * Add section with null section data
     *
     * @return SectionManager
     */
    SectionManager<D> addNullableSection();

    /**
     * Insert section for the given position, with nullable section data and valid section position
     *
     * @param sectionData     the section data
     * @param sectionPosition the section inset position
     * @return SectionManager
     */
    SectionManager<D> insertSectionAtPosition(@Nullable D sectionData, @IntRange(from = 0) int sectionPosition);

    /**
     * Removes the first section, with data equals to given sectionData
     *
     * @param sectionData the section data
     * @return SectionManager
     */
    SectionManager<D> removeFirstSectionWithData(D sectionData);

    /**
     * Removes all sections, with data equals to given sectionData
     *
     * @param sectionData the section data
     * @return SectionManager
     */
    SectionManager<D> removeAllSectionsWithData(D sectionData);

    /**
     * Removes the last section
     *
     * @return SectionManager
     */
    SectionManager<D> removeLastSection();

    /**
     * Removes the first section
     *
     * @return SectionManager
     */
    SectionManager<D> removeFirstSection();

    /**
     * Removes section from specified position
     *
     * @param sectionPosition, section position
     * @return SectionManager
     */
    SectionManager<D> removeSectionAtPosition(@IntRange(from = 0) int sectionPosition);

    /**
     * Removes all sections
     *
     * @return SectionManager
     */
    SectionManager<D> removeAllSections();
}
