package albrizy.support.widget;

import android.view.ViewGroup;
import android.view.WindowInsets;

class ScrimInsetHelper {

    static WindowInsets onApplyWindowInsets(ViewGroup group, WindowInsets insets) {
        for (int i = 0; i < group.getChildCount(); i++) {
            group.getChildAt(i).dispatchApplyWindowInsets(insets);
        }
        return insets;
    }
}
