package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.DeleteCertificates;
import com.clixifi.wabell.data.GetCertificates;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.squareup.picasso.Picasso;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class GetOldMediaAdapter extends RecyclerView.Adapter<GetOldMediaAdapter.MyViewHolder> {
    Context context;
    GetCertificates certificates;
    private LayoutInflater mInflater;
    String token;
    public GetOldMediaAdapter(Context context, GetCertificates certificates) {
        this.context = context;
        this.certificates = certificates;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GetOldMediaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.media_img_style, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetOldMediaAdapter.MyViewHolder holder, final int position) {
        if (certificates.getMedia().size() > 0) {
            Picasso.with(context).load(certificates.getMedia().get(position).getFilePath()).into(holder.image);
        }
        holder.closeI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (certificates != null) {
                    deleteFromDatabase(certificates.getMedia().get(position).getId());
                }

                if (certificates.getMedia().size() > 0) {
                    certificates.getMedia().remove(position);
                    //ImagesFile.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, certificates.getMedia().size());
                    //notifyItemRangeChanged(position, ImagesFile.size());
                    notifyDataSetChanged();
                }

            }
        });
    }
    private void deleteFromDatabase(int id) {
        if (StaticMethods.isConnectingToInternet(context)) {
            if (StaticMethods.userRegisterResponse != null) {
                token = "Bearer " + StaticMethods.userRegisterResponse.Data.getToken();
            } else if (StaticMethods.userData != null) {
                token = "Bearer " + StaticMethods.userData.getToken();
            }
            MainApi.deleteCer(token, id, new ConnectionListener<DeleteCertificates>() {
                @Override
                public void onSuccess(ConnectionResponse<DeleteCertificates> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if (connectionResponse.data.isDeleted()) {
                            ToastUtil.showSuccessToast(context, R.string.delete);
                        }
                    }
                }

                @Override
                public void onFail(Throwable throwable) {

                    Log.e(TAG, "onFail: " + throwable.toString());
                }
            });
        } else {
            ToastUtil.showErrorToast(context, R.string.noInternet);
        }
    }
    @Override
    public int getItemCount() {
        return certificates.getMedia().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image, closeI;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            closeI = itemView.findViewById(R.id.close);
        }
    }
}
