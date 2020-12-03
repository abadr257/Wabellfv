package com.clixifi.wabell.utils.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.StringRes;


import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;
import com.clixifi.wabell.utils.StaticMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DialogUtil {
    DialogUtilResponse response;
    DialougUtilsDate dateresponse;

    public DialogUtil(DialogUtilResponse response) {
        this.response = response;
    }

    public DialogUtil(DialougUtilsDate response) {
        this.dateresponse = response;
    }

    public DialogUtil(DialogUtilResponse response, DialougUtilsDate date) {
        this.response = response;
        this.dateresponse = date;
    }

    public static int ImageInfoIcon = 1;
    public static int ImageErrorIcon = 2;
    public static int Imagenonr = 0;

    public void showSingleActionDialog(Context context, @StringRes int title,
                                       @StringRes int text, boolean showImageDialoug, int ImageDialougType,
                                       AppDialog.OkButtonClickListener clickListener) {
        showDialog(context, title, text, 0, ImageDialougType, clickListener);
    }

    public void showSingleActionDialog(Context context, @StringRes int text,
                                       AppDialog.OkButtonClickListener clickListener) {
        showDialog(context, 0, text, 0, 0, clickListener);
    }

    public void showSingleActionDialog(Context context, @StringRes int title, @StringRes int text, int ImageDialougType) {
        showDialog(context, title, text, 0, ImageDialougType, null);
    }

    public void showSingleActionDialog(Context context, @StringRes int title, @StringRes int text, int ImageDialougType, AppDialog.OkButtonClickListener clickListener) {
        showDialog(context, title, text, 0, ImageDialougType, clickListener);
    }

    public static void showTwoActionDialog(Context context, @StringRes int title,
                                           @StringRes int text, int ImageDialougType, AppDialog.RightButtonClickListener clickListener
            , AppDialog.LeftButtonClickListener leftButtonClickListener) {

        showDialog(context, title, text, 0, 0, ImageDialougType, leftButtonClickListener, clickListener);
    }


    public void showTwoActionDialog(Context context,
                                    @StringRes int text, AppDialog.RightButtonClickListener clickListener
            , AppDialog.LeftButtonClickListener leftButtonClickListener) {

        showDialog(context, 0, text, 0, 0, 0, leftButtonClickListener, clickListener);
    }

    public void showTwoActionDialog(Context context, @StringRes int title, @StringRes int text) {
        showDialog(context, title, text, 0, 0, 0, null, null);
    }

//    public static void showDialougSingleChooice(Context context, @StringRes int title, @StringRes int okButtonText, List<String> listarray,AppDialog.OkButtonClickListener okButtonClickListener) {
//         showDialogSingleChooice(context, title, okButtonText, listarray,okButtonClickListener );
//    }


    private static void showDialog(Context context, @StringRes int title, @StringRes int text,
                                   @StringRes int leftButtonText, @StringRes int rightButtonText
            , int ImageDialougType, AppDialog.LeftButtonClickListener leftButtonClickListener
            , AppDialog.RightButtonClickListener rightButtonClickListener) {

        AppDialog.SmallDialog smallDialog = new AppDialog.SmallDialog(context);
        smallDialog.dialogTitle(title).dialogText(text).showImageDialoug(
                false, ImageDialougType);
        if (leftButtonText != 0) {
            smallDialog.leftButtonText(leftButtonText);
        }
        if (rightButtonText != 0) {
            smallDialog.rightButtonText(rightButtonText);
        }
        if (leftButtonClickListener != null) {
            smallDialog.leftButtonClickListener(leftButtonClickListener);
        }
        if (rightButtonClickListener != null) {
            smallDialog.rightButtonClickListener(rightButtonClickListener);
        }
        smallDialog.show();
    }

    private void showDialog(Context context, @StringRes int title, @StringRes int text,
                            @StringRes int okButtonText, int ImageDialougType, AppDialog.OkButtonClickListener okButtonClickListener) {

        AppDialog.SmallDialog smallDialog = new AppDialog.SmallDialog(context);
        smallDialog.dialogTitle(title).dialogText(text).showImageDialoug(true, ImageDialougType);
        if (okButtonText != 0) {
            smallDialog.okButtonText(okButtonText);
        }
        if (okButtonClickListener != null) {
            smallDialog.okButtonClickListener(okButtonClickListener);
        }
        smallDialog.show();
    }

    public int indexofarray = -1;

    public void showDialogSingleChooice(final Context context, @StringRes int title, @StringRes int okButtonText, List<String> listarray) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(title));
        //idcity = idscity.get(0);
        builder.setSingleChoiceItems(listarray.toArray(new String[listarray.size()]),
                -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int poistion) {
                        indexofarray = poistion;
                        Log.e("position1", "" + indexofarray);

                    }
                });
        builder.setPositiveButton(context.getResources().getString(okButtonText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {

                response.selectedValueSingleChoice(indexofarray);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void showDialogSingleChooice(final Context context, @StringRes int title, @StringRes int okButtonText, final List<String> listarray, final String arrayType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(title));
        //idcity = idscity.get(0);
        indexofarray = -1;
        builder.setSingleChoiceItems(listarray.toArray(new String[listarray.size()]),
                -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int poistion) {
                        indexofarray = poistion;

                        Log.e("position1", "" + indexofarray);

                    }
                });
        builder.setPositiveButton(context.getResources().getString(okButtonText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if (indexofarray != -1)
                    response.selectedValueSingleChoice(indexofarray, arrayType);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showSingleChooiceArrayList(final Context context, @StringRes int title, @StringRes int okButtonText, final ArrayList<String> listarray, final String arrayType, final ArrayList<Integer> url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(title));
        //idcity = idscity.get(0);
        indexofarray = -1;
        builder.setSingleChoiceItems(listarray.toArray(new String[listarray.size()]),
                -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int poistion) {
                        indexofarray = poistion;
                        Log.e("position1", "" + url.get(poistion));

                    }
                });
        builder.setPositiveButton(context.getResources().getString(okButtonText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if (indexofarray != -1)
                    response.selectedValueSingleChoice(indexofarray, arrayType);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showSingleChooiceArrayList(int selceted, final Context context, String title, @StringRes int okButtonText, final ArrayList<String> listarray, final String arrayType, final ArrayList<Integer> ids) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        //idcity = idscity.get(0);
        indexofarray = -1;
        int index = -1 ;
        if (selceted == 0) {
            index = -1;
        } else {
            if(ids != null){
                try {
                    for (int id : ids) {
                        if (id == selceted) {
                            index = ids.indexOf(selceted);
                        }
                    }
                } catch (Exception e) {
                    Log.e(GifHeaderParser.TAG, "Hi Ahmed ");
                }
            }

        }
        builder.setSingleChoiceItems(listarray.toArray(new String[listarray.size()]),
                index, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int poistion) {
                        indexofarray = poistion;
                        Log.e("position1", "" + ids.get(poistion));

                    }
                });
        builder.setPositiveButton(context.getResources().getString(okButtonText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if (indexofarray != -1)
                    response.selectedValueSingleChoice(indexofarray, arrayType);

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showSingleChooiceSkills(final Context context, @StringRes int title, @StringRes int okButtonText, final ArrayList<String> listarray, final String arrayType, final ArrayList<Integer> ids) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(title));
        //idcity = idscity.get(0);
        indexofarray = -1;
        builder.setSingleChoiceItems(listarray.toArray(new String[listarray.size()]),
                -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int poistion) {
                        indexofarray = poistion;

                        Log.e("position1", "" + ids.get(poistion));

                    }
                });
        builder.setPositiveButton(context.getResources().getString(okButtonText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if (indexofarray != -1)
                    response.selectedValueSingleChoice(indexofarray, arrayType);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showDialogDatePicker(final Context context) {
        DatePickerDialog dialog = new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        // set max date
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        //dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
    }

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //  updateLabel();
            dateresponse.DateFormatResponse(myCalendar);
        }
    };

    public void showDialogWithMultiChoicelang(final Context context, @StringRes int title, @StringRes int okButtonText, final List<String> listarray, final String arrayType) {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final ArrayList<String> selected = new ArrayList<>();
        final ArrayList<String> selectedLangCode = new ArrayList<>();
        //final List<String> langCode = Arrays.asList(context.getResources().getStringArray(R.array.languages));
        builder.setTitle(context.getResources().getString(title))
                .setMultiChoiceItems(listarray.toArray(new String[listarray.size()]), null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked){
                            selected.add(listarray.get(which));
                            selectedLangCode.add(langCode.get(which));
                        }else if(selected.contains(listarray.get(which))){
                            selected.remove(listarray.get(which));
                            selectedLangCode.remove(langCode.get(which));
                        }
                    }

                })

                // Set the action buttons
                .setPositiveButton(context.getResources().getString(okButtonText), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        response.selectedMultiChoicelang(selected , selectedLangCode , arrayType);
                        Log.e(TAG, "onClick: Postions "+selectedLangCode );
                        Log.e(TAG, "onClick: Selections "+selected );
                    }
                })

                .show();*/
    }

    public void showDialogWithMultiChoiceskills(final Context context, @StringRes int title, @StringRes int okButtonText, final ArrayList<String> listarray, final String arrayType, final ArrayList<Integer> ids) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final ArrayList<String> selected = new ArrayList<>();
        final ArrayList<String> selectedSkillCode = new ArrayList<>();
        //final List<String> langCode = Arrays.asList(context.getResources().getStringArray(R.array.languages));
        builder.setTitle(context.getResources().getString(title))
                .setMultiChoiceItems(listarray.toArray(new String[listarray.size()]), null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selected.add(listarray.get(which));
                            selectedSkillCode.add(String.valueOf(ids.get(which)));
                        } else if (selected.contains(listarray.get(which))) {
                            selected.remove(listarray.get(which));
                            selectedSkillCode.remove(String.valueOf(ids.get(which)));
                        }
                    }

                })

                // Set the action buttons
                .setPositiveButton(context.getResources().getString(okButtonText), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        response.selectedMultiChoicelang(selected, selectedSkillCode, arrayType);
                        Log.e(TAG, "onClick:Skills Postions " + selectedSkillCode);
                        Log.e(TAG, "onClick: Skills Selections " + selected);
                    }
                })

                .show();
    }
}
