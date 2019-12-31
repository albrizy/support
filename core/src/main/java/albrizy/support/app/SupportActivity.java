package albrizy.support.app;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class SupportActivity extends AppCompatActivity {

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration c) {
        super.onConfigurationChanged(c);
        if (c.orientation == Configuration.ORIENTATION_PORTRAIT ||
            c.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            onOrientationChanged(c.orientation ==
                    Configuration.ORIENTATION_LANDSCAPE);
        }
    }

    protected void onOrientationChanged(boolean landscape) {}
}
