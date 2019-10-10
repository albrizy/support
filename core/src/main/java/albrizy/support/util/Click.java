package albrizy.support.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

@SuppressWarnings("WeakerAccess")
public class Click {

    public static final String EXTRA_KEY = "extra_key";
    public static final String EXTRA_VALUE = "extra_value";
    public static final String EXTRA_ITEM = "extra_item";

    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_SUBTITLE = "extra_subtitle";

    public static final String EXTRA_INT = "extra_int";
    public static final String EXTRA_LONG = "extra_long";
    public static final String EXTRA_BOOL = "extra_bool";
    public static final String EXTRA_STRING = "extra_string";

    private static final String URL_STORE_APP_INFO ="https://play.google.com/store/apps/details?id=";
    private static final String URL_STORE_DEV_PAGE ="https://play.google.com/store/apps/dev?id=";

    public static void openStoreAppInfo(Context context, String pkgName) {
        try {
            openURL(context, URL_STORE_APP_INFO + pkgName);
        } catch (ActivityNotFoundException ignored) {}
    }

    public static void openStoreDevPage(Context context, String devId) {
        try {
            openURL(context, URL_STORE_DEV_PAGE + devId);
        } catch (ActivityNotFoundException ignored) {}
    }

    public static void openURL(Context context, String url) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                | Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        );
        context.startActivity(intent);
    }
}
