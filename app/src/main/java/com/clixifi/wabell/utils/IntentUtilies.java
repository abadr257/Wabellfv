package com.clixifi.wabell.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentUtilies {
    public static void openActivity(Context context, Class aClass) {
        openActivity(context, aClass, null, false);
    }

    public static void openActivityWithBundle(Context context, Class aClass, Bundle bundle) {
        openActivity(context, aClass, bundle, false);
    }

    public static void openActivityWithBundleInNewStack(Context context, Class aClass, Bundle bundle) {
        openActivity(context, aClass, bundle, true);
    }

    public static void openActivityInNewStack(Context context, Class aClass) {
        openActivity(context, aClass, null, true);
    }

    public static void openActivity(Context context, String className) {
        openActivity(context, className, null, false);
    }

    public static void openActivityWithBundle(Context context, String className, Bundle bundle) {
        openActivity(context, className, bundle, false);
    }

    public static void openActivityWithBundleInNewStack(Context context, String className, Bundle bundle) {
        openActivity(context, className, bundle, true);
    }

    public static void openActivityInNewStack(Context context, String className) {
        openActivity(context, className, null, true);
    }


    public static void openActivity(Context context, Class aClass, Bundle bundle, boolean clearStack) {
        Intent intent;
        intent = new Intent(context, aClass);
        if (clearStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void openActivity(Context context, String actionName, Bundle bundle, boolean clearStack) {
        Intent intent;
        try {
            intent = new Intent(context, Class.forName(actionName));
            if (clearStack) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}