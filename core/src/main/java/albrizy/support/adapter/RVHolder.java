package albrizy.support.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("WeakerAccess")
public class RVHolder extends RecyclerView.ViewHolder {

    public RVHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void onDetach() {}
}
