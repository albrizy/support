package albrizy.support.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class Click {

    private Click() {}

    private static final String URL_STORE_APP_INFO ="https://play.google.com/store/apps/details?id=";
    private static final String URL_STORE_DEV_PAGE ="https://play.google.com/store/apps/dev?id=";

    public static void openStoreAppInfo(Context context, String appId) {
        openURL(context, URL_STORE_APP_INFO + appId);
    }

    public static void openStoreAppDev(Context context, String devId) {
        openURL(context, URL_STORE_DEV_PAGE + devId);
    }

    public static void openURL(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                | Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        );
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context,
                    "No application can handle this request. Please install a web browser",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
