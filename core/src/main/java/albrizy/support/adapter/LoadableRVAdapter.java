package albrizy.support.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import albrizy.support.R;

@SuppressWarnings("WeakerAccess")
public abstract class LoadableRVAdapter<T> extends RVAdapter<T> {

    private final Loadable loadable;

    public LoadableRVAdapter(Context context, @NonNull Loadable.OnLoadListener onLoadListener) {
        this(context, onLoadListener, new ArrayList<>());
    }

    public LoadableRVAdapter(Context context, @NonNull Loadable.OnLoadListener onLoadListener, @NonNull List<T> items) {
        super(context, items);
        this.loadable = new Loadable(onLoadListener);
    }

    public void setLoadMoreEnabled(boolean enabled) {
        loadable.setLoadMoreEnabled(enabled);
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

    @NonNull
    @Override
    public RVHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        return type == getLoadingType()
                ? onCreateLoadingHolder(parent, type)
                : onCreateHolder(parent, type);
    }

    protected RVHolder onCreateLoadingHolder(@NonNull ViewGroup parent, int type) {
        return new RVHolder(inflate(parent, type));
    }

    protected abstract RVHolder onCreateHolder(@NonNull ViewGroup parent, int type);

    @Override
    public void onBindViewHolder(@NonNull RVHolder base, int position) {
        if (base.getItemViewType() == getLoadingType()) {
            onBindLoadingHolder(base, position);
        } else onBindHolder(base, position);
    }

    protected abstract void onBindHolder(@NonNull RVHolder base, int position);

    protected void onBindLoadingHolder(@NonNull RVHolder base, int position) {}

    public int getLoadingType() {
        return R.layout.asa_loading_holder;
    }

    public abstract int getItemType(int position);

    @Override
    public int getItemViewType(int position) {
        return position < getItems().size()
                ? getItemType(position)
                : getLoadingType();
    }

    @Override
    public int getItemCount() {
        return loadable.isLoadMoreEnabled()
                ? getItems().size() + 1
                : getItems().size();
    }
}
