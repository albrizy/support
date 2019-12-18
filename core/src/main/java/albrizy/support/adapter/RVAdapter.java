package albrizy.support.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public abstract class RVAdapter<T> extends RecyclerView.Adapter<RVHolder> {

    @NonNull
    private List<T> items;
    private final LayoutInflater inflater;

    public RVAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public RVAdapter(Context context, @NonNull List<T> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @NonNull
    public List<T> getItems() {
        return items;
    }

    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    protected View inflate(ViewGroup parent, @LayoutRes int layoutRes) {
        return inflater.inflate(layoutRes, parent, false);
    }

    @NonNull
    protected View inflate(ViewGroup parent, @LayoutRes int layoutRes, boolean attachToRoot) {
        return inflater.inflate(layoutRes, parent, attachToRoot);
    }

    @NonNull
    @Override
    public RVHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        return new RVHolder(inflate(parent, type));
    }

    public void setItems(@NonNull List<T> items) {
        this.items = items;
    }

    public void addItems(List<T> items) {
        this.items.addAll(items);
    }

    public void addItem(T item) {
        this.items.add(item);
    }

    public void clear() {
        items.clear();
    }

    public void clear(boolean notify) {
        items.clear();
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void release() {
        items.clear();
    }
}
