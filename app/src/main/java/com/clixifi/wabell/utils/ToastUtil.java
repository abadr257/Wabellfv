package com.clixifi.wabell.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

import es.dmoral.toasty.Toasty;


public class ToastUtil {


    public static void showToast(Context context, @StringRes int toast) {
        showToastMessage(context, context.getString(toast), Toast.LENGTH_LONG);
    }

    public static void showToast(Context context, String toast) {
        showToastMessage(context, toast, Toast.LENGTH_LONG);
    }

    public static void showToast(Context context, @StringRes int toast, int length) {
        showToastMessage(context, context.getString(toast), length);
    }

    public static void showToast(Context context, String toast, int length) {
        showToastMessage(context, toast, length);
    }


    public static void showSuccessToast(Context context, @StringRes int toast) {
        showSuccessToastMessage(context, context.getString(toast), Toast.LENGTH_LONG);
    }

    public static void showSuccessToast(Context context, String toast) {
        showSuccessToastMessage(context, toast, Toast.LENGTH_LONG);
    }

    public static void showSuccessToast(Context context, @StringRes int toast, int length) {
        showSuccessToastMessage(context, context.getString(toast), length);
    }

    public static void showSuccessToast(Context context, String toast, int length) {
        showSuccessToastMessage(context, toast, length);
    }


    public static void showWarningToast(Context context, @StringRes int toast) {
        showWarningToastMessage(context, context.getString(toast), Toast.LENGTH_LONG);
    }

    public static void showWarningToast(Context context, String toast) {
        showWarningToastMessage(context, toast, Toast.LENGTH_LONG);
    }

    public static void showWarningToast(Context context, @StringRes int toast, int length) {
        showWarningToastMessage(context, context.getString(toast), length);
    }

    public static void showWarningToast(Context context, String toast, int length) {
        showWarningToastMessage(context, toast, length);
    }

    public static void showInfoToast(Context context, @StringRes int toast) {
        showInfoToastMessage(context, context.getString(toast), Toast.LENGTH_LONG);
    }

    public static void showInfoToast(Context context, String toast) {
        showInfoToastMessage(context, toast, Toast.LENGTH_LONG);
    }

    public static void showInfoToast(Context context, @StringRes int toast, int length) {
        showInfoToastMessage(context, context.getString(toast), length);
    }

    public static void showInfoToast(Context context, String toast, int length) {
        showInfoToastMessage(context, toast, length);
    }

    public static void showErrorToast(Context context, @StringRes int toast) {
        showErrorToastMessage(context, context.getString(toast), Toast.LENGTH_LONG);
    }

    public static void showErrorToast(Context context, String toast) {
        showErrorToastMessage(context, toast, Toast.LENGTH_LONG);
    }

    public static void showErrorToast(Context context, @StringRes int toast, int length) {
        showErrorToastMessage(context, context.getString(toast), length);
    }

    public static void showErrorToast(Context context, String toast, int length) {
        showErrorToastMessage(context, toast, length);
    }

    private static void showToastMessage(Context context, String toast, int length) {
        Toast.makeText(context, toast, length).show();
    }

    private static void showSuccessToastMessage(Context context, String toast, int length) {
        Toasty.success(context, toast, length).show();
    }

    private static void showWarningToastMessage(Context context, String toast, int length) {
        Toasty.warning(context, toast, length).show();
    }

    private static void showInfoToastMessage(Context context, String toast, int length) {
        Toasty.info(context, toast, length).show();
    }
    private static void showErrorToastMessage(Context context, String toast, int length) {
        Toasty.error(context, toast, length).show();
    }

}
