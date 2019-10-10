package albrizy.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.FrameLayout;

public class ScrimInsetFrameLayout extends FrameLayout {

    public ScrimInsetFrameLayout(Context context) {
        super(context);
    }

    public ScrimInsetFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrimInsetFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return ScrimInsetHelper.onApplyWindowInsets(this, insets);
    }
}
