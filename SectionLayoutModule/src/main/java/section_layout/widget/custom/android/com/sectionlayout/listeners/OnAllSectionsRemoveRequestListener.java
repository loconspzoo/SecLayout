package section_layout.widget.custom.android.com.sectionlayout.listeners;

/**
 * Created by Robert Apikyan on 9/5/2017.
 */

public interface OnAllSectionsRemoveRequestListener {
    /**
     * Will be called before all section remove action
     *
     * @return true -> all sections will be removed: false -> remove all sections request will be ignored
     */
    boolean onAllSectionsRemoveRequest();
}
