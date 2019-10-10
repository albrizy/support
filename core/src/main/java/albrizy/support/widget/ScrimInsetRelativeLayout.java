package albrizy.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.RelativeLayout;

public class ScrimInsetRelativeLayout extends RelativeLayout {

    public ScrimInsetRelativeLayout(Context context) {
        super(context);
    }

    public ScrimInsetRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrimInsetRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return ScrimInsetHelper.onApplyWindowInsets(this, insets);
    }
}
