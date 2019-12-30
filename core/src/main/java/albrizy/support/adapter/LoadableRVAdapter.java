package albrizy.support.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import albrizy.support.R;
import albrizy.support.adapter.Loadable.OnLoadListener;

@SuppressWarnings("WeakerAccess")
public abstract class LoadableRVAdapter<T> extends RVAdapter<T> {

    private final Loadable loadable;

    public LoadableRVAdapter(Context context, @NonNull OnLoadListener onLoadListener) {
        this(context, new ArrayList<>(), onLoadListener);
    }

    public LoadableRVAdapter(Context context,
            @NonNull List<T> items,
            @NonNull OnLoadListener onLoadListener) {
        super(context, items);
        this.loadable = new Loadable(onLoadListener);
    }

    public boolean isLoadMoreEnabled() {
        return loadable.isLoadMoreEnabled();
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        loadable.setLoadMoreEnabled(loadMoreEnabled);
    }

    public void notifyLoadingCompleted() {
        loadable.notifyLoadingCompleted();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        rv.addOnScrollListener(loadable);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView rv) {
        rv.removeOnScrollListener(loadable);
    }

    @Override
    public void onBindViewHolder(@NonNull RVHolder base, int position) {
        if (base.getItemViewType() == getLoadingType()) {
            onBindLoadingHolder(base);
        } else onBindHolder(base, position);
    }

    protected abstract void onBindHolder(@NonNull RVHolder base, int position);
    protected void onBindLoadingHolder(@NonNull RVHolder base) {}

    public int getLoadingType() {
        return R.layout.asa_loading_holder;
    }

    public abstract int getItemType(int position);

    @Override
    public int getItemViewType(int position) {
        return position >= getItems().size()
                ? getLoadingType()
                : getItemType(position);
    }

    @Override
    public int getItemCount() {
        return loadable.isLoadMoreEnabled()
                ? getItems().size() + 1
                : getItems().size();
    }
}
