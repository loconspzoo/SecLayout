package section_layout.widget.custom.android.com.sectionlayout;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import java.util.LinkedList;

import section_layout.widget.custom.android.com.sectionlayout.listeners.OnAddSectionListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnAddSectionRequestListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnAllSectionsRemoveRequestListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnAllSectionsRemovedListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnRemoveSectionListener;
import section_layout.widget.custom.android.com.sectionlayout.listeners.OnRemoveSectionRequestListener;
import view_component.lib_android.com.view_component.base_view.ControllerComponent;
import view_component.lib_android.com.view_component.base_view.functional_interfaces.Request;

/**
 * Created by Robert Apikyan on 8/22/2017.
 */

public class SectionLayoutViewControllerComponent<D> extends ControllerComponent<SectionLayoutViewComponent> implements SectionManager<D> {
    private final LinkedList<SectionLayout.ViewHolder<D>> viewHolders = new LinkedList<>();
    private SectionLayout.Adapter adapter;
    private final Notifier<D> notifier = new Notifier<>();

    @Override
    public void onCreate(@NonNull SectionLayoutViewComponent viewComponent) {
        super.onCreate(viewComponent);
    }

    SectionLayoutViewControllerComponent<D> withAdapter(@NonNull SectionLayout.Adapter adapter) {
        this.adapter = adapter;
        return this;
    }

    @Override
    public SectionManager<D> addSection(@Nullable final D sectionData) {
        insertSectionAtPosition(sectionData, nextAvailablePosition());
        return this;
    }

    @Override
    public SectionManager<D> addNullableSection() {
        return addSection(null);
    }

    @Override
    public SectionManager<D> insertSectionAtPosition(@Nullable final D sectionData, @IntRange(from = 0) final int sectionPosition) {
        // request for section addition action
        if (notifier.onAddSectionRequest(sectionData, sectionPosition)) {
            requestHolderComponent(new Request<SectionLayoutViewComponent>() {
                @Override
                public void onResult(@NonNull SectionLayoutViewComponent sectionLayoutViewComponent) {
                    assertNullAdapter();
                    assertWrongInsertPosition(sectionPosition);
                    // create view holder
                    //noinspection unchecked,ConstantConditions
                    SectionLayout.ViewHolder<D> vh = adapter.onCreateViewHolder(LayoutInflater.from(getContext()), getViewComponent().getRootViewGroup(), adapter.getViewType(sectionData, sectionPosition));
                    // section position
                    vh.setSectionPosition(sectionPosition);
                    // bind view holder
                    //noinspection unchecked
                    adapter.onBindViewHolder(vh, sectionData, sectionPosition);
                    // add view holder to list
                    viewHolders.add(sectionPosition, vh);
                    // add to component linear layout
                    sectionLayoutViewComponent.getRootViewGroup().addView(vh.getSectionView(), sectionPosition);

                    syncPositions(sectionPosition + 1);

                    notifier.onSectionAdded(sectionData, sectionPosition);
                }
            });
        }
        logViewHolders();
        return this;
    }

    @Override
    public SectionManager<D> removeFirstSectionWithData(final D sectionData) {
        removeSection(sectionData, true);
        logViewHolders();
        return this;
    }

    @Override
    public SectionManager<D> removeAllSectionsWithData(D sectionData) {
        removeSection(sectionData, false);
        logViewHolders();
        return this;
    }

    @Override
    public SectionManager<D> removeLastSection() {
        final int index = size() - 1;
        assertIndexOutOfBounds(index, "SectionManager#removeLastSection");
        requestHolderComponent(new Request<SectionLayoutViewComponent>() {
            @Override
            public void onResult(@NonNull SectionLayoutViewComponent sectionLayoutViewComponent) {
                if (notifier.onRemoveSectionRequest(viewHolders.getLast().getSectionData())) {
                    sectionLayoutViewComponent.getRootViewGroup().removeViewAt(index);
                    viewHolders.removeLast();
                    notifier.onSectionRemoved(index);
                }
            }
        });
        logViewHolders();
        return this;
    }

    @Override
    public SectionManager<D> removeFirstSection() {
        assertIndexOutOfBounds(0, "SectionManager#removeFirstSection");
        requestHolderComponent(new Request<SectionLayoutViewComponent>() {
            @Override
            public void onResult(@NonNull SectionLayoutViewComponent sectionLayoutViewComponent) {
                if (notifier.onRemoveSectionRequest(viewHolders.getFirst().getSectionData())) {
                    sectionLayoutViewComponent.getRootViewGroup().removeViewAt(0);
                    viewHolders.removeFirst();
                    syncPositions(0);
                    notifier.onSectionRemoved(0);
                }
            }
        });
        logViewHolders();
        return this;
    }

    @Override
    public SectionManager<D> removeSectionAtPosition(@IntRange(from = 0) final int sectionPosition) {
        assertIndexOutOfBounds(sectionPosition, "SectionManager#removeSectionAtPosition");
        removeSection(sectionPosition);
        return this;
    }

    @Override
    public SectionManager<D> removeAllSections() {
        requestHolderComponent(new Request<SectionLayoutViewComponent>() {
            @Override
            public void onResult(@NonNull SectionLayoutViewComponent sectionLayoutViewComponent) {
                if (notifier.onAllSectionsRemoveRequest()) {
                    sectionLayoutViewComponent.getRootViewGroup().removeAllViews();
                    viewHolders.clear();
                    notifier.onAllSectionsRemoved();
                }
            }
        });
        logViewHolders();
        return this;
    }

    int size() {
        return viewHolders.size();
    }

    SectionLayout.ViewHolder<D> getViewHolderForAdapterPosition(int adapterPosition) {
        assertIndexOutOfBounds(adapterPosition, "SectionLayoutViewControllerComponent#getViewHolderForAdapterPosition");
        return viewHolders.get(adapterPosition);
    }

    @Nullable
    SectionLayout.ViewHolder<D> getFirstViewHolderWithData(D sectionData) {
        if (sectionData != null) {
            for (int i = 0; i < viewHolders.size(); i++) {
                SectionLayout.ViewHolder<D> viewHolder = viewHolders.get(i);
                if (sectionData.equals(viewHolder.getSectionData())) {
                    return viewHolder;
                }
            }
        }
        return null;
    }

    /**
     * Iterates and removes the sections with specified section data
     *
     * @param sectionData to be removed
     * @param removeFirst false all section with specified data will be removed, true only the first one will be removed
     */
    private void removeSection(final D sectionData, final boolean removeFirst) {
        requestHolderComponent(new Request<SectionLayoutViewComponent>() {
            @Override
            public void onResult(@NonNull SectionLayoutViewComponent sectionLayoutViewComponent) {
                if (sectionData != null) {
                    for (int i = 0; i < viewHolders.size(); i++) {
                        SectionLayout.ViewHolder<D> holder = viewHolders.get(i);
                        if (sectionData.equals(holder.getSectionData())) {
                            boolean isRemoved = removeSection(i);
                            if (removeFirst) {
                                break;
                            }
                            if (isRemoved) {
                                i--;
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean removeSection(int sectionPosition) {
        if (getViewComponent() != null) {
            if (notifier.onRemoveSectionRequest(viewHolders.get(sectionPosition).getSectionData())) {
                getViewComponent().getRootViewGroup().removeViewAt(sectionPosition);
                viewHolders.remove(sectionPosition);
                syncPositions(sectionPosition);
                notifier.onSectionRemoved(sectionPosition);
                return true;
            }
        }
        logViewHolders();
        return false;
    }

    private int nextAvailablePosition() {
        return getViewComponent() != null ? getViewComponent().getRootViewGroup().getChildCount() : 0;
    }

    private void syncPositions(int from) {
        for (int i = from; i < viewHolders.size(); i++) {
            SectionLayout.ViewHolder<D> holder = viewHolders.get(i);
            holder.setSectionPosition(i);
        }
    }

    private void logViewHolders() {
//        for (int i = 0; i < viewHolders.size(); i++) {
//            ViewHolder vh = viewHolders.get(i);
//            Log.d("Loog", "vh.getSectionPosition() = " + vh.getSectionPosition()
//                    + " " +
//                    "vh.getSectionData() = " + vh.getSectionData());
//        }
    }

    /////////////////// Assertions /////////////////////////
    private void assertWrongInsertPosition(int position) {
        if (position > viewHolders.size() || position < 0) {
            throw new IndexOutOfBoundsException("SectionManager#insertSectionAtPosition() -> Out of bounds, invalid sectionPosition value, " +
                    "sectionPosition = " + position + "," + " size() = " + size());
        }
    }

    private void assertIndexOutOfBounds(int position, String methodName) {
        if (position >= viewHolders.size() || position < 0) {
            throw new IndexOutOfBoundsException(methodName + " -> Out of bounds, invalid sectionPosition value, " + "sectionPosition = " + position + "," + " size() = " + size());
        }
    }

    private void assertNullAdapter() {
        if (adapter == null) {
            throw new RuntimeException("Section can't be added with null adapter, at first call SectionManager::withAdapter method, and pass non null adapter");
        }
    }

    ////////////////////// Listeners /////////////////////////
    void setOnAddSectionListener(OnAddSectionListener<D> onAddSectionListener) {
        notifier.setOnAddSectionListener(onAddSectionListener);
    }

    void setOnRemoveSectionListener(OnRemoveSectionListener onRemoveSectionListener) {
        notifier.setOnRemoveSectionListener(onRemoveSectionListener);
    }

    void setOnAddSectionRequestListener(OnAddSectionRequestListener<D> onAddSectionRequestListener) {
        notifier.setOnAddSectionRequestListener(onAddSectionRequestListener);
    }

    void setOnRemoveSectionRequestListener(OnRemoveSectionRequestListener<D> onRemoveSectionRequestListener) {
        notifier.setOnRemoveSectionRequestListener(onRemoveSectionRequestListener);
    }

    void setOnAllSectionsRemovedListener(OnAllSectionsRemovedListener onAllSectionsRemovedListener) {
        notifier.setOnAllSectionsRemovedListener(onAllSectionsRemovedListener);
    }

    void setOnAllSectionsRemoveRequestListener(OnAllSectionsRemoveRequestListener onAllSectionsRemoveRequestListener) {
        notifier.setOnAllSectionsRemoveRequestListener(onAllSectionsRemoveRequestListener);
    }

    OnAddSectionListener<D> getOnAddSectionListener() {
        return notifier.getOnAddSectionListener();
    }

    OnRemoveSectionListener getOnRemoveSectionListener() {
        return notifier.getOnRemoveSectionListener();
    }

    OnAddSectionRequestListener<D> getOnAddSectionRequestListener() {
        return notifier.getOnAddSectionRequestListener();
    }

    OnRemoveSectionRequestListener<D> getOnRemoveSectionRequestListener() {
        return notifier.getOnRemoveSectionRequestListener();
    }

    OnAllSectionsRemovedListener getOnAllSectionsRemovedListener() {
        return notifier.getOnAllSectionsRemovedListener();
    }

    OnAllSectionsRemoveRequestListener getOnAllSectionsRemoveRequestListener() {
        return notifier.getOnAllSectionsRemoveRequestListener();
    }
}
