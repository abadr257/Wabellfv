package com.clixifi.wabell.ui.tutorMedia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.FragmentTutorMediaBinding;
import com.clixifi.wabell.ui.Adapters.UploadCertificateAdapter;
import com.clixifi.wabell.ui.Adapters.onRemoveImage;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class TutorMedia extends Fragment implements onRemoveImage {



    FragmentTutorMediaBinding binding ;
    View v ;
    MyHandlers handlers ;
    ArrayList<Bitmap> certificate , Ids ;
    ArrayList<File> certificatePaths , IdsPaths ;
    final int REQUEST_PICK_IMAGE_CER = 1002 ,REQUEST_PICK_IMAGE_IDS = 1001 ;
    UploadCertificateAdapter adapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutor_media, container, false);
        v = binding.getRoot();
        handlers = new MyHandlers(getActivity());
        binding.setHandler(handlers);
        initialArrayList();
        adapter = new UploadCertificateAdapter(getActivity() , certificate , this , certificatePaths );
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.recCertificate.setLayoutManager(layoutManager);
        binding.recCertificate.setAdapter(adapter);
        return v;
    }

    private void initialArrayList() {
        certificate = new ArrayList<>();
        Ids = new ArrayList<>();
        certificatePaths = new ArrayList<>();
        IdsPaths = new ArrayList<>();
    }

    @Override
    public void onRemoveImage(ArrayList<Bitmap> bitmaps , ArrayList<File> files ,int position) {
       // certificate.remove(position);
        certificatePaths.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, certificatePaths.size());
        adapter.notifyItemRangeChanged(position, certificate.size());
        /*this.certificate = bitmaps;
        this.certificatePaths = files;*/
    }

    public class MyHandlers {
        Context context ;

        public MyHandlers(Context context) {
            this.context = context;
        }
        public void nextStep(View v){
            ((TutorSteps)getActivity()).step3();
        }
        public void uploadCer(View v){
            Intent getIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            getIntent.setType("image/*");
            startActivityForResult(getIntent, REQUEST_PICK_IMAGE_CER);
        }
        public void uploadImgs(View v){
            Intent getIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            getIntent.setType("image/*");
            startActivityForResult(getIntent, REQUEST_PICK_IMAGE_IDS);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_PICK_IMAGE_CER) {
                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    File f = new File(getRealPathFromURI(getActivity() , uri));
                    //certificate.add(bitmap);
                    certificatePaths.add(f);
                    certificatePaths = adapter.addToList(bitmap , uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }  else if (requestCode == REQUEST_PICK_IMAGE_IDS) {
                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    Ids.add(bitmap);
                    File finalFile = new File(getRealPathFromURI(getActivity(), uri));
                    IdsPaths.add(finalFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
    public static String getRealPathFromURI(Context inContext, Uri uri) {
        Cursor cursor = inContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}