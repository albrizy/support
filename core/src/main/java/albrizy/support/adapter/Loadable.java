package albrizy.support.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

@SuppressWarnings("WeakerAccess")
public class Loadable extends OnScrollListener {

    @NonNull
    private final OnLoadListener listener;
    private boolean loadMoreEnabled = true;
    private boolean isLoading;
    private int dy;

    public Loadable(@NonNull OnLoadListener listener) {
        this.listener = listener;
    }

    public boolean isLoadMoreEnabled() {
        return loadMoreEnabled;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.loadMoreEnabled = loadMoreEnabled;
    }

    public void notifyLoadingCompleted() {
        isLoading = false;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
        this.dy = dy;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView rv, int newState) {
        if (!loadMoreEnabled || isLoading) return;
        if (newState == SCROLL_STATE_IDLE && dy > 0) {
            LayoutManager lm = rv.getLayoutManager();
            if (canScroll(lm)) {
                isLoading = true;
                listener.onLoad();
            }
        }
    }

    private static boolean canScroll(LayoutManager lm) {
        return lm instanceof LinearLayoutManager && ((LinearLayoutManager) lm)
                .findLastVisibleItemPosition() >= lm.getItemCount() -1;
    }

    public interface OnLoadListener {
        void onLoad();
    }
}
