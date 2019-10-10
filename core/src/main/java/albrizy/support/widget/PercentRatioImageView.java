package albrizy.support.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import albrizy.support.R;

public class PercentRatioImageView extends AppCompatImageView {

    private static final int ORIENTATION_HORIZONTAL = 0;
    private static final int ORIENTATION_VERTICAL = 1;

    private int orientation;
    private float ratio;

    public PercentRatioImageView(Context context) {
        super(context);
    }

    public PercentRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PercentRatioImageView);
        orientation = a.getInt(R.styleable.PercentRatioImageView_orientation, ORIENTATION_HORIZONTAL);
        ratio = a.getFloat(R.styleable.PercentRatioImageView_ratio, 1.0f);
        a.recycle();
    }

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(orientation == ORIENTATION_HORIZONTAL) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredWidth() / ratio));
        } else {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            setMeasuredDimension((int) (getMeasuredHeight() * ratio), getMeasuredHeight());
        }
    }
}
