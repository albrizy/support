package albrizy.support.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DiffUtil.DiffResult;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public abstract class RVAdapter<T> extends Adapter<RVHolder> {

    private List<T> items;
    private final LayoutInflater inflater;

    public RVAdapter(Context context, @NonNull List<T> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void addItems(List<T> items) {
        this.items.addAll(items);
    }

    public void addItem(T item) {
        this.items.add(item);
    }

    public void swapItems(List<T> items) {
        this.swapItems(items, new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return RVAdapter.this.items.size();
            }

            @Override
            public int getNewListSize() {
                return items.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return areContentsTheSame(oldItemPosition, newItemPosition);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return RVAdapter.this.items.get(oldItemPosition)
                        .equals(items.get(newItemPosition));
            }
        });
    }

    public void swapItems(List<T> items, DiffUtil.Callback callback) {
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this);
    }

    public List<T> getItems() {
        return items;
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

    public void clear() {
        try {
            items.clear();
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void release() {
        try {
            items.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
