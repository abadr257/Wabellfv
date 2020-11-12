package com.clixifi.wabell.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.clixifi.wabell.R;

import com.clixifi.wabell.data.Response.ImageUrl;
import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.data.Response.User.LoginData;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;


/*Created By Ahmed Ashraf Android Developer @Egypt +201016348221*/
public class StaticMethods {
    public static int User_Status;
    public static String auth = "";
    /*private static Retrofit retrofit = null;*/
    public final static int REQUEST_CAMERA = 0, SELECT_FILE = 1, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 14, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 15, PICK_FROM_GALLERY = 102, CALLL_PERMISION = 2, MY_PERMISSIONS_REQUEST_CAMERA = 100, REQUEST_PICK_IMAGE = 1002;
    public static final int REQUEST_CAMERASTATIC = 90;
    public static final String BASE_URL = "https://api-test.wabell.net";//http://api.wabell.net/ //https://api-test.wabell.net/
    public static final String CONTENT = "application/json";


    /*public static Retrofit RetrofitDataInsc() {
        retrofit = null;

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/
    public static void statusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            activity.getWindow().setStatusBarColor(Color.parseColor("#F4F7FC"));
        }
    }

    public static void fullScrean(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void LoadImage(final Context context, final CircleImageView imageView, String Url, final ProgressBar progressBar) {
        try {
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(Url)
                    .asBitmap()
                    .dontTransform()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (progressBar != null)
                                progressBar.setVisibility(View.GONE);

                            //imageView.setVisibility(View.VISIBLE);
                            final float scale = context.getResources().getDisplayMetrics().density;
                            int pixels = (int) (100 * scale + 0.5f);
                            Bitmap bitmap = Bitmap.createScaledBitmap(resource, pixels, pixels, true);
                            if (imageView != null){
                                imageView.setImageBitmap(bitmap);
                            }

                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            if (progressBar != null)
                                progressBar.setVisibility(View.GONE);
                            if (imageView != null){
                                imageView.setVisibility(View.VISIBLE);
                                imageView.setImageResource(R.drawable.wabell_logode);
                            }

                        }
                    });
        } catch (Exception e) {
        }
    }

    public static void LoadImage(final Context context, final ImageView imageView, String Url, final ProgressBar progressBar) {
        try {
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(Url)
                    .asBitmap()
                    .dontTransform()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (progressBar != null)
                                progressBar.setVisibility(View.GONE);

                            //imageView.setVisibility(View.VISIBLE);
                            final float scale = context.getResources().getDisplayMetrics().density;
                            int pixels = (int) (1000 * scale + 0.5f);
                            Bitmap bitmap = Bitmap.createScaledBitmap(resource, pixels, pixels, true);
                            if (imageView != null){
                                imageView.setImageBitmap(bitmap);
                            }
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            if (progressBar != null)
                                progressBar.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.wabell_logode);
                        }
                    });
        } catch (Exception e) {
        }
    }

    public static boolean checkCameraPermission(Context context, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            } else if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            } else if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            } else
                return true;
        } else {
            return true;
        }
    }

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo networkInfo : info)
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void printJson(String tag, Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.e(tag, gson.toJson(object));
    }

    public static UserResponse<RegisterData> userRegisterResponse = null;
    public static ArrayList<Integer> TopicsIds = null;
    public static String tutorId = null;
    public static LoginData userData = null;
    public static String date = null;
    public static ArrayList<ImageUrl> images = null;
    public static TutorListArray tutors = null;
    public static boolean selected = false;
    public static int selectedPostion = 0;
    public static String typeS = "";
    public static String searchWord = "";
    public static void ClearChash() {
        StaticMethods.userData = null;
        StaticMethods.userRegisterResponse = null;
        //StaticMethods.userResponse =  null  ;
        /*StaticMethods.skillsResponse = null ;
        StaticMethods.categoryResponse = null ;*/
    }
}
