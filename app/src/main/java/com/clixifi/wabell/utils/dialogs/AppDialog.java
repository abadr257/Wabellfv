package com.clixifi.wabell.utils.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clixifi.wabell.R;


public class AppDialog {
    public interface LeftButtonClickListener {
        void onLeftButtonClick(AlertDialog alertDialog);
    }
    public interface RightButtonClickListener {
        void onRightButtonClick(AlertDialog alertDialog);
    }
    public interface OkButtonClickListener {
        void onOKButtonClick(AlertDialog alertDialog);
    }
    static class SmallDialog {
        private Context context;
        private int dialogTitle, dialogText, dialogQuestion;
        private int rightButtonText, leftButtonText,okButtonText,ImageDrawableType;
        private boolean showimagedialoug, showQuestion, showText,showLinearsActions;
        private TextView titleTextView, textTextView, questionTextView;
        private TextView leftButton, rightButton,okButton;
        private View dialogView;
        private LeftButtonClickListener leftButtonClickListener;
        private RightButtonClickListener rightButtonClickListener;
        private OkButtonClickListener okButtonClickListener;
        private AlertDialog dialog;
        private LinearLayout linearSingleAction;
        private LinearLayout linearTwoAction ;
        private ImageView ImageDialoug;

        SmallDialog(Context context) {
            this.context = context;
        }
        SmallDialog dialogTitle(int title) {
            this.dialogTitle = title;
            return this;
        }
        SmallDialog dialogText(int text) {
            this.dialogText = text;
            return this;
        }
        public SmallDialog dialogQuestion(int question) {
            this.dialogQuestion = question;
            return this;
        }
        public SmallDialog leftButtonText(int text) {
            this.leftButtonText = text;
            return this;
        }
        public SmallDialog rightButtonText(int text) {
            this.rightButtonText = text;
            return this;
        }
        public SmallDialog okButtonText(int text) {
            this.okButtonText = text;
            return this;
        }
        public SmallDialog showText(boolean showText) {
            this.showText = showText;
            return this;
        }
        public SmallDialog showQuestion(boolean showQuestion) {
            this.showQuestion = showQuestion;
            return this;
        }
        public SmallDialog showImageDialoug(boolean showLinearsAction,int ImageDrawableType) {
            this.showLinearsActions = showLinearsAction;
            this.ImageDrawableType = ImageDrawableType;
            return this;
        }
        public SmallDialog leftButtonClickListener(LeftButtonClickListener leftButtonClickListener) {
            this.leftButtonClickListener = leftButtonClickListener;
            return this;
        }
        public SmallDialog rightButtonClickListener(RightButtonClickListener rightButtonClickListener) {
            this.rightButtonClickListener = rightButtonClickListener;
            return this;
        }
        public SmallDialog okButtonClickListener(OkButtonClickListener okButtonClickListener) {
            this.okButtonClickListener = okButtonClickListener;
            return this;
        }
        public void show() {
            findViews();
            showDialog();
        }
        private void showDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialogView);
            dialog = builder.create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
            dialog.show();
        }
        private void findViews() {
            dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_small_layout, null);

            titleTextView = dialogView.findViewById( R.id.tv_dialog_title);
            textTextView = dialogView.findViewById(R.id.tv_dialog_text);
            questionTextView = dialogView.findViewById(R.id.tv_dialog_question);
            leftButton = dialogView.findViewById(R.id.tv_dialog_cancel);
            rightButton = dialogView.findViewById(R.id.tv_dialog_ok);
            linearSingleAction = dialogView.findViewById( R.id.lin_dialoug_oneaction);
            linearTwoAction = dialogView.findViewById( R.id.lin_dialoug_twoaction);
            ImageDialoug = dialogView.findViewById( R.id.imgdialoug);
            okButton = dialogView.findViewById( R.id.tv_dialog_oksin);
            viewsVisibility();
        }
        private void viewsVisibility() {

            titleTextView.setText(context.getString(R.string.logout));
            textTextView.setVisibility(View.VISIBLE);
            if (dialogText != 0) {
                textTextView.setText(dialogText);
            }
            else {
                textTextView.setVisibility(View.GONE);
            }
            questionTextView.setVisibility(View.GONE);
            Log.e("showlineasflag",showLinearsActions+"");
            if(showLinearsActions){
                viewsOneActrion();
            }else {
                viewsTwoActrion();
            }
            SetImageOptions();
        }
        private void viewsOneActrion() {
            // dialoug is single Action
            linearSingleAction.setVisibility(View.VISIBLE);
            linearTwoAction.setVisibility(View.GONE);

            if (okButtonText != 0) {
                okButton.setText(okButtonText);
            }
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (okButtonClickListener != null) {
                        okButtonClickListener.onOKButtonClick(dialog);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        }
        private void viewsTwoActrion() {
            // dialoug is single Action
            linearTwoAction.setVisibility(View.VISIBLE);
            linearSingleAction.setVisibility(View.GONE);

            // left Button Text

            if (leftButtonText != 0) {
                leftButton.setText(leftButtonText);
            }
            leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (leftButtonClickListener != null) {
                        leftButtonClickListener.onLeftButtonClick(dialog);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            // right button.
            if (rightButtonText != 0) {
                rightButton.setText(rightButtonText);
            }
            rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightButtonClickListener != null) {
                        rightButtonClickListener.onRightButtonClick(dialog);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        }
        private  void SetImageOptions(){
            // set IMage Options

//
//            if(ImageDrawableType == DialogUtil.Imagenonr){
//                ImageDialoug.setVisibility(View.GONE);
//            }
//            else if(ImageDrawableType == DialogUtil.ImageErrorIcon){
//                ImageDialoug.setVisibility(View.VISIBLE);
//                ImageDialoug.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.ic_cancle));
//            } else if(ImageDrawableType == DialogUtil.ImageInfoIcon){
//                ImageDialoug.setVisibility(View.VISIBLE);
//                ImageDialoug.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.ic_info));
//            }
        }
    }
}

