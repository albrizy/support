package albrizy.support.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings("WeakerAccess")
public abstract class SupportFragment extends Fragment {

    private Unbinder unbinder;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration c) {
        super.onConfigurationChanged(c);
        onOrientationChanged(c.orientation ==
                Configuration.ORIENTATION_LANDSCAPE);
    }

    protected void onOrientationChanged(boolean landscape) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
