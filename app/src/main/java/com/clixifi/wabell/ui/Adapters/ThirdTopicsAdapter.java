package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.FourthLevel;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.topicChild.ChildItem;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;
import com.clixifi.wabell.helpers.prefs.PrefUtils;
import com.clixifi.wabell.ui.login.LoginScreen;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtilResponse;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.clixifi.wabell.utils.network.MainApiBody;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class ThirdTopicsAdapter extends RecyclerView.Adapter<ThirdTopicsAdapter.MyViewHolder> implements DialogUtilResponse {
    Context context;
    private LayoutInflater mInflater;
    ArrayList<FourthLevel> SubTopics;
    ArrayList<ChildItem> fourth;
    DialogUtil dialogUtil;
    String lang;
    ArrayList<String> names;
    CustomDialog dialog ;
    ArrayList<Integer> ids;
    MyViewHolder holder;
    int position;
    TopicsChildAdapter.MyViewHolder hisHolder;
    int hisPostion;
    int selected = 0, selceted2 = 0;


    public ThirdTopicsAdapter(TopicsChildAdapter.MyViewHolder holder, int position, Context context, ArrayList<FourthLevel> SubTopics, String lang) {
        this.context = context;
        this.SubTopics = SubTopics;
        this.lang = lang;
        dialog = new CustomDialog(context);
        this.mInflater = LayoutInflater.from(context);
        dialogUtil = new DialogUtil(this);
        StaticMethods.TopicsIds = new ArrayList<>();
        this.hisHolder = holder;
        this.hisPostion = position;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.third_child_topics, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (SubTopics.get(position).isMarked()) {
            if (lang.equals("ar")) {
                holder.rel.setBackground(context.getResources().getDrawable(R.drawable.selected_forth));
                holder.txt_name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                hisHolder.checkBox.setChecked(true);
                holder.txt_name.setText(SubTopics.get(position).getTitleAr());
            } else {
                holder.rel.setBackground(context.getResources().getDrawable(R.drawable.selected_forth));
                holder.txt_name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                hisHolder.checkBox.setChecked(true);
                holder.txt_name.setText(SubTopics.get(position).getTitleEn());
            }
        } else {
            if (lang.equals("ar")) {
                holder.txt_name.setText(SubTopics.get(position).getTitleAr());
            } else {
                holder.txt_name.setText(SubTopics.get(position).getTitleEn());
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickItem(position, holder, SubTopics.get(position).getId());


            }
        });

    }

    private void cancleThis(int id) {
        if (StaticMethods.isConnectingToInternet(context)) {

            String token,userid;
            if (StaticMethods.userRegisterResponse != null) {
                token = "Bearer " + StaticMethods.userRegisterResponse.Data.getToken();
                userid = StaticMethods.userRegisterResponse.Data.getUserId();
            } else {
                token = "Bearer " + StaticMethods.userData.getToken();
                userid = StaticMethods.userData.getUserId();
            }
            RequestBody body = null;
            try {
                body = MainApiBody.cancelTopicBody(id,userid);
            } catch (Exception e) {

            }
            MainApi.cancelTopic(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if (connectionResponse.data.isResult()) {
                            Log.e(TAG, "onSuccess: " + "Cancel");
                            dialog.DismissDialog();

                        }
                    }
                }

                @Override
                public void onFail(Throwable throwable) {

                }
            });
        } else {
            ToastUtil.showErrorToast(context, R.string.noInternet);
        }
    }

    private void onClickItem(int position, MyViewHolder holder, int id) {
        this.holder = holder;
        this.position = position;
        if (holder.txt_name.getCurrentTextColor() == context.getResources().getColor(R.color.colorWhite)) {
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.third_child_style));
            Log.e(TAG, "onClick: " + "I`m here");
            holder.txt_name.setTextColor(context.getResources().getColor(R.color.notActive));
            dialog.ShowDialog();
            cancleThis(SubTopics.get(position).getId());
        } else {
            names = new ArrayList<>();
            ids = new ArrayList<>();
            fourth = new ArrayList<>();
            getSelectedFourth(id);
        }


    }

    public int getSelectedFourth(int id) {

        if (StaticMethods.isConnectingToInternet(context)) {
            String token;
            if (StaticMethods.userRegisterResponse != null) {
                token = "Bearer " + StaticMethods.userRegisterResponse.Data.getToken();
            } else {
                token = "Bearer " + StaticMethods.userData.getToken();
            }

            RequestBody body = null;
            try {
                body = MainApiBody.fourthLevelBody(id);
            } catch (Exception e) {

            }
            MainApi.getSelectedFouth(token, body, new ConnectionListener<ChildResponse>() {
                @Override
                public void onSuccess(ConnectionResponse<ChildResponse> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if (connectionResponse.data.getSubcategory() != null) {
                            for (int i = 0; i < connectionResponse.data.getSubcategory().size(); i++) {
                                if (connectionResponse.data.getSubcategory().get(i).IsMarked) {
                                    selected = connectionResponse.data.getSubcategory().get(i).getId();
                                }
                            }
                            fourth = connectionResponse.data.getSubcategory();
                            if (fourth != null) {
                                Log.e(TAG, "LOGE : " + "here");
                                if (fourth.size() > 0) {
                                    int size = fourth.size();
                                    Log.e(TAG, "LOGE SIZE: " + size);
                                    for (int i = 0; i < size; i++) {
                                        if (lang.equals("ar")) {
                                            names.add(fourth.get(i).getTitleAr());
                                            ids.add(fourth.get(i).getId());
                                        } else {
                                            names.add(fourth.get(i).getTitleEn());
                                            ids.add(fourth.get(i).getId());
                                        }
                                    }
                                    dialogUtil.showSingleChooiceArrayList(selected, context, "Select Level", R.string.ok, names, "fourth", ids);
                                }
                            }
                            Log.e(TAG, "selected: " + selected);

                        }
                    }
                }

                @Override
                public void onFail(Throwable throwable) {

                }
            });
        } else {
            ToastUtil.showErrorToast(context, R.string.noInternet);
        }
        return selected;
    }

    @Override
    public int getItemCount() {
        if (SubTopics != null) {
            return SubTopics.size();
        } else {
            return 0;
        }
    }

    @Override
    public void selectedValueSingleChoice(int position) {

    }

    @Override
    public void selectedValueSingleChoice(int id, String arrayType) {
        //StaticMethods.TopicsIds.removeAll(ids);
        //StaticMethods.TopicsIds.add(ids.get(id));
        Log.e(TAG, "selectedValueSingleChoice: " + StaticMethods.TopicsIds);
        holder.rel.setBackground(context.getResources().getDrawable(R.drawable.selected_forth));
        holder.txt_name.setTextColor(context.getResources().getColor(R.color.colorWhite));
        hisHolder.checkBox.setChecked(true);
        dialog.ShowDialog();
        addUserTopic(ids.get(id));

    }

    private void addUserTopic(int integer) {
        if (StaticMethods.isConnectingToInternet(context)) {
            String token , id;
            if (StaticMethods.userRegisterResponse != null) {
                token = "Bearer " + StaticMethods.userRegisterResponse.Data.getToken();
                id = StaticMethods.userRegisterResponse.Data.getUserId();
            } else {
                token = "Bearer " + StaticMethods.userData.getToken();
                id = StaticMethods.userData.getUserId();
            }
            RequestBody body = null;
            try {
                body = MainApiBody.addTopics(integer , id);
            } catch (Exception e) {

            }
            MainApi.addTopic(token, body, new ConnectionListener<ResultBoolean>() {
                @Override
                public void onSuccess(ConnectionResponse<ResultBoolean> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if (connectionResponse.data.isResult()) {
                            Log.e(TAG, "onSuccess: " + "Added");
                            dialog.DismissDialog();
                        }
                    }
                }

                @Override
                public void onFail(Throwable throwable) {

                }
            });
        } else {
            ToastUtil.showErrorToast(context, R.string.noInternet);
        }
    }

    @Override
    public void selectedMultiChoicelang(ArrayList<String> choices, ArrayList<String> postions, String arrayType) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        RelativeLayout rel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rel = itemView.findViewById(R.id.rel_selected);
            txt_name = itemView.findViewById(R.id.nameThirdChild);
        }
    }

    public boolean isIn(int pos) {
        if (StaticMethods.TopicsIds != null) {
            int id = SubTopics.get(pos).getSubTopics().get(pos).getId();
            if (Arrays.asList(StaticMethods.TopicsIds).contains(id)) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }
}
