package albrizy.support.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

@SuppressWarnings("WeakerAccess")
public class TintUtil {

    private TintUtil() {}

    @Nullable
    public static Drawable tint(Context context, @DrawableRes int resId, @ColorInt int color) {
        Drawable dr = ContextCompat.getDrawable(context, resId);
        return tint(dr, color);
    }

    @Nullable
    public static Drawable tint(@Nullable Drawable drawable, @ColorInt int color) {
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable.mutate());
            DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
            DrawableCompat.setTint(drawable, color);
            return drawable;
        }
        return null;
    }

    @Nullable
    public static Drawable tint(@Nullable Drawable drawable, @NonNull ColorStateList csl) {
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable.mutate());
            DrawableCompat.setTintList(drawable, csl);
            return drawable;
        }
        return null;
    }
}
