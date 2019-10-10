package albrizy.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.LinearLayout;

public class ScrimInsetLinearLayout extends LinearLayout {

    public ScrimInsetLinearLayout(Context context) {
        super(context);
    }

    public ScrimInsetLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrimInsetLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return ScrimInsetHelper.onApplyWindowInsets(this, insets);
    }
}
