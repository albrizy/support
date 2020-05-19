package albrizy.support.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import albrizy.support.R;

@SuppressWarnings("WeakerAccess")
public abstract class RVAdapter<T> extends RecyclerView.Adapter<RVHolder> {

    @NonNull
    private List<T> items;
    private final LayoutInflater inflater;

    @Nullable
    private OnLoadMoreListener onLoadMoreListener;
    private boolean loadMoreEnabled;
    private boolean isLoading;

    public RVAdapter(
            Context context,
            @NonNull List<T> items) {
        this(context, items, null);
    }

    public RVAdapter(
            Context context,
            @NonNull List<T> items,
            @Nullable OnLoadMoreListener onLoadMoreListener) {
        this.inflater = LayoutInflater.from(context);
        this.onLoadMoreListener = onLoadMoreListener;
        this.items = items;

        setLoadMoreEnabled(onLoadMoreListener != null);
    }

    @NonNull
    public List<T> getItems() {
        return items;
    }

    @NonNull
    public T getItem(int position) {
        return items.get(position);
    }

    public void setItems(@NonNull List<T> items) {
        this.items = items;
    }

    public void addItems(@NonNull List<T> items) {
        this.items.addAll(items);
    }

    public void addItem(@NonNull T item) {
        this.items.add(item);
    }

    protected abstract int getItemType(int position);

    protected int getLoadingType() {
        return R.layout.support_loading_holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position < items.size()
                ? getItemType(position)
                : getLoadingType();
    }

    @Override
    public int getItemCount() {
        return loadMoreEnabled
                ? items.size() + 1
                : items.size();
    }

    protected View inflate(@NonNull ViewGroup parent, @LayoutRes int layout) {
        return inflate(parent, layout, false);
    }

    protected View inflate(@NonNull ViewGroup parent, @LayoutRes int layout, boolean attachToRoot) {
        return inflater.inflate(layout, parent, attachToRoot);
    }

    @NonNull
    @Override
    public final RVHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        return type == getLoadingType()
                ? new RVHolder(inflate(parent, type))
                : onCreateHolder(parent, type);
    }

    @NonNull
    protected abstract RVHolder onCreateHolder(@NonNull ViewGroup parent, int type) ;
    protected abstract void onBindHolder(@NonNull RVHolder holder, int position);

    @Override
    public final void onBindViewHolder(@NonNull RVHolder holder, int position) {
        if (holder.getItemViewType() == getLoadingType() && position > 0) {
            if (loadMoreEnabled
                    && !isLoading
                    && onLoadMoreListener != null) {
                isLoading = true;
                onLoadMoreListener.onLoadMore();
            }
            return;
        }
        onBindHolder(holder, position);
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.loadMoreEnabled = loadMoreEnabled;
    }

    public void notifyLoadingCompleted() {
        this.isLoading = false;
    }

    public void clear() {
        try {
            items.clear();
            notifyDataSetChanged();
        } catch (Exception ignored) {}
    }

    public void release() {
        try {
            items.clear();
        } catch (Exception ignored) {}
        onLoadMoreListener = null;
    }
}
