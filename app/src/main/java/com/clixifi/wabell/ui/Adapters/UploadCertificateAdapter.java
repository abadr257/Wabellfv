package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.clixifi.wabell.data.Response.topic.Topics;
import com.clixifi.wabell.ui.viewAllCertificates.AllCertificates;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class UploadCertificateAdapter extends RecyclerView.Adapter<UploadCertificateAdapter.MyViewHolder> {


    ArrayList<Bitmap> certificate;
    ArrayList<File> ImagesFile;
    Context context;
    private LayoutInflater mInflater;
    onRemoveImage remove;
    GetCertificates certificates;
    String token;

    public UploadCertificateAdapter(Context context, ArrayList<Bitmap> certificate, onRemoveImage remove, ArrayList<File> ImagesFile, GetCertificates certificates) {
        this.context = context;
        this.certificate = certificate;
        this.ImagesFile = ImagesFile;
        this.remove = remove;
        this.mInflater = LayoutInflater.from(context);
        this.certificates = certificates;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.media_img_style, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (certificates.getMedia().size() > 0) {
            Picasso.with(context).load(certificates.getMedia().get(position).getFilePath()).into(holder.image);
        }
        if(certificate.size() > 0){
            holder.image.setImageBitmap(certificate.get(position));
        }

        holder.closeI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(certificates != null){
                    deleteFromDatabase(certificates.getMedia().get(position).getId());
                }

                remove.onRemoveImage(certificate, ImagesFile, position);
                if(certificate.size() > 0){
                    certificate.remove(position);
                    ImagesFile.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, certificate.size());
                    notifyItemRangeChanged(position, ImagesFile.size());
                    notifyDataSetChanged();
                }
                if(certificates.getMedia().size() > 0){
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
            MainApi.deleteCer(token,id, new ConnectionListener<DeleteCertificates>() {
                @Override
                public void onSuccess(ConnectionResponse<DeleteCertificates> connectionResponse) {
                    if (connectionResponse.data != null) {
                        if(connectionResponse.data.isDeleted()){
                            ToastUtil.showSuccessToast(context , R.string.delete);
                        }
                    }
                }

                @Override
                public void onFail(Throwable throwable) {

                    Log.e(TAG, "onFail: " + throwable.toString());
                }
            });
        } else {
            ToastUtil.showErrorToast(context , R.string.noInternet);
        }
    }

    public ArrayList<File> addToList(Bitmap image, Uri uri) {
        if (certificate != null && ImagesFile != null) {
            certificate.add(image);
            File file = new File(getRealPathFromURI(context, uri));
            ImagesFile.add(file);
            notifyDataSetChanged();
        } else {
            certificate = new ArrayList<>();
            ImagesFile = new ArrayList<>();
            File file = new File(getRealPathFromURI(context, uri));
            ImagesFile.add(file);
            certificate.add(image);
            notifyDataSetChanged();
        }
        return ImagesFile;
    }

    @Override
    public int getItemCount() {
         if (certificates != null) {
            return (certificates.getMedia().size()+certificate.size());
        } else {
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image, closeI;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            closeI = itemView.findViewById(R.id.close);
        }
    }


    public static String getRealPathFromURI(Context inContext, Uri uri) {
        Cursor cursor = inContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


}
