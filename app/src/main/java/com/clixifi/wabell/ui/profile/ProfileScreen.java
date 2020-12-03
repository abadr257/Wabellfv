package com.clixifi.wabell.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.clixifi.wabell.R;
import com.clixifi.wabell.data.DeleteCertificates;
import com.clixifi.wabell.data.GetCertificates;
import com.clixifi.wabell.data.MediaResponse;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserProfile;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.data.Response.areas.AreasItem;
import com.clixifi.wabell.data.Response.cities.CityItem;
import com.clixifi.wabell.databinding.FragmentProfileScreenBinding;
import com.clixifi.wabell.ui.Adapters.CertificatesAdapter;
import com.clixifi.wabell.ui.login.LoginScreen;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.ui.registerTutor.TutorInterface;
import com.clixifi.wabell.ui.registerTutor.TutorPresenter;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileView;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtilResponse;
import com.facebook.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.clixifi.wabell.ui.tutorMedia.TutorMedia.getRealPathFromURI;


public class ProfileScreen extends Fragment implements ProfileInteface, DialogUtilResponse, TutorInterface {

    FragmentProfileScreenBinding binding;
    View v, dialogView;
    ProfileHandler profile;
    CustomDialog dialog;
    ProfilePresenter presenter;
    final int REQUEST_PICK_IMAGE_PROFILE = 1002;
    ArrayList<File> profileImage;
    CertificatesAdapter adapter;
    String type;
    boolean editProfilePic = false;
    AlertDialog.Builder dialogBuilder;
    ArrayList<CityItem> citiesList;
    ArrayList<AreasItem> areasList;
    DialogUtil dialogUtil;
    TutorPresenter tutorPresenter;
    int locationId = 0;
    EditText edCity, edArea;
    UserResponse<UserProfile> data;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    HashMap<String, String> userMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_screen, container, false);
        v = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        userMap = new HashMap<>();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        profile = new ProfileHandler(getActivity());
        binding.setHandler(profile);
        inflater = this.getLayoutInflater();
        dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogView = inflater.inflate(R.layout.location_dialog, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        edCity = dialogView.findViewById(R.id.ed_city);
        edArea = dialogView.findViewById(R.id.ed_Neighborhood);
        tutorPresenter = new TutorPresenter(this);
        tutorPresenter.getCities(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(getActivity())) {
            binding.edPhone.setTextDirection(View.TEXT_DIRECTION_RTL);
            binding.edPhone.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }
        dialog = new CustomDialog(getActivity());
        presenter = new ProfilePresenter(this);
        binding.setOnEdit(false);
        dialogUtil = new DialogUtil(this);
        if (StaticMethods.userRegisterResponse != null) {
            if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                binding.setOnStudent(true);
            } else {
                binding.setOnStudent(false);
            }
        } else if (StaticMethods.userData != null) {
            if (StaticMethods.userData.getUserType().equals("tutor")) {
                binding.setOnStudent(true);
            } else {
                binding.setOnStudent(false);
            }
        }
        fillUserData();
        return v;
    }

    public static boolean isRTL(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return context.getResources().getConfiguration().getLayoutDirection()
                    == View.LAYOUT_DIRECTION_RTL;
        } else {
            return false;
        }
    }

    private void fillUserData() {
        dialog.ShowDialog();
        presenter.getProfileData(getActivity());
        if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.save))) {
            binding.edName.setFocusable(true);
            binding.edPhone.setFocusable(true);
            binding.edEmail.setFocusable(true);
            binding.edEdu.setFocusable(true);
            binding.edExp.setFocusable(true);
            binding.edTagLine.setFocusable(true);
            binding.edBiography.setFocusable(true);
        }
    }

    @Override
    public void onSuccessProfile(UserResponse<UserProfile> profile) {

        this.data = profile;

        locationId = profile.DataProfile.getDistrictId();

        if (profile.DataProfile.getLocation() != null) {
            binding.edLocation.setText(profile.DataProfile.getLocation());
        }
        if (profile.DataProfile.getBiography() != null) {
            binding.edBiography.setText(profile.DataProfile.getBiography());
        }
        if (profile.DataProfile.getTagline() != null) {
            binding.edTagLine.setText(profile.DataProfile.getTagline());
        }
        if (profile.DataProfile.getEducation() != null) {
            binding.edEdu.setText(profile.DataProfile.getEducation());
        }
        if (profile.DataProfile.getExperience() != null) {
            binding.edExp.setText(profile.DataProfile.getExperience());
        }
        if (profile.DataProfile.getTopics() != null) {
            binding.edTopics.setText(profile.DataProfile.getTopics());
        }

        if (profile.DataProfile.getProfilePicture() != null) {
            Picasso.with(getActivity()).load(profile.DataProfile.getProfilePicture()).into(binding.userImg);
            //StaticMethods.LoadImage(getActivity(), binding.userImg, profile.DataProfile.getProfilePicture(), null);
        }
        if (LocaleManager.getLanguage(getActivity()).equals("en")) {
            if (profile.DataProfile.getEngTopics() != null) {
                StringBuffer engTop = new StringBuffer();
                for (int i = 0; i < profile.DataProfile.getEngTopics().size(); i++) {
                    engTop.append(profile.DataProfile.getEngTopics().get(i));
                    engTop.append("\n");
                }
                binding.edTopics.setText(engTop);
                if (profile.DataProfile.getAvailableDays() != null || profile.DataProfile.getAvailableTimes() != null) {
                    binding.edWorkDetails.setText(profile.DataProfile.getHourPrice() + " SAR / Hr \n" + profile.DataProfile.getAvailableDays() + "\n" + profile.DataProfile.getAvailableTimes());
                }
            }

        } else {
            if (profile.DataProfile.getArTopics() != null) {
                StringBuffer arTop = new StringBuffer();
                for (int i = 0; i < profile.DataProfile.getArTopics().size(); i++) {
                    arTop.append(profile.DataProfile.getArTopics().get(i));
                    arTop.append("\n");
                }
                binding.edTopics.setText(arTop);
                if (profile.DataProfile.getAvailableDays() != null || profile.DataProfile.getAvailableTimes() != null) {
                    binding.edWorkDetails.setText(profile.DataProfile.getHourPrice() + " ريال / س \n" + profile.DataProfile.getArAvailableDaysText() + "\n" + profile.DataProfile.getArAvailableTimesText());
                }
            }
        }

        binding.edEmail.setText(profile.DataProfile.getEmail());
        binding.edName.setText(profile.DataProfile.getName());
        binding.userName.setText(profile.DataProfile.getName());
        binding.edPhone.setText(profile.DataProfile.getPhoneNumber());
        if (profile.DataProfile.getFiles() != null) {
            if (profile.DataProfile.getFiles().size() == 0) {
                binding.txtNoCer.setVisibility(View.VISIBLE);
            } else if (profile.DataProfile.getFiles().size() > 0) {
                adapter = new CertificatesAdapter(getActivity(), null, profile.DataProfile.getFiles());
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                binding.recCertificate.setLayoutManager(layoutManager);
                binding.recCertificate.setAdapter(adapter);
            }
        }

        dialog.DismissDialog();
    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity(), R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity(), R.string.noInternet);
    }

    @Override
    public void onUpdate(boolean updated) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(getActivity(), R.string.updatedSuccessfully);
        if (StaticMethods.userData != null) {
            if (StaticMethods.userData.getUserType().equals("tutor")) {
                ((MainScreen) getActivity()).logout();
            }
        } else {
            if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                ((MainScreen) getActivity()).logout();
            }
        }
        if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
            if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.save))) {
                binding.setOnEdit(false);
                binding.edName.setFocusableInTouchMode(false);
                binding.edName.setFocusable(false);
                binding.edPhone.setFocusableInTouchMode(false);
                binding.edPhone.setFocusable(false);
                binding.edEmail.setFocusableInTouchMode(false);
                binding.edEmail.setFocusable(false);
                binding.edEdu.setFocusableInTouchMode(false);
                binding.edEdu.setFocusable(false);
                binding.edExp.setFocusableInTouchMode(false);
                binding.edExp.setFocusable(false);
                binding.edTagLine.setFocusableInTouchMode(false);
                binding.edTagLine.setFocusable(false);
                binding.edBiography.setFocusableInTouchMode(false);
                binding.edBiography.setFocusable(false);
                binding.txtEditAll.setText(getActivity().getResources().getString(R.string.edit));
            } else if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.edit))) {
                binding.setOnEdit(true);
                binding.edName.setFocusableInTouchMode(true);
                binding.edName.setFocusable(true);
                binding.edPhone.setFocusableInTouchMode(true);
                binding.edPhone.setFocusable(true);
                binding.edEmail.setFocusableInTouchMode(true);
                binding.edEmail.setFocusable(true);
                binding.edEdu.setFocusableInTouchMode(true);
                binding.edEdu.setFocusable(true);
                binding.edExp.setFocusableInTouchMode(true);
                binding.edExp.setFocusable(true);
                binding.edTagLine.setFocusableInTouchMode(true);
                binding.edTagLine.setFocusable(true);
                binding.edBiography.setFocusableInTouchMode(true);
                binding.edBiography.setFocusable(true);
                binding.txtEditAll.setText(getActivity().getResources().getString(R.string.save));
            }
        } else {
            if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.save))) {
                binding.setOnEdit(false);
                binding.edName.setFocusableInTouchMode(false);
                binding.edName.setFocusable(false);
                binding.edPhone.setFocusableInTouchMode(false);
                binding.edPhone.setFocusable(false);
                binding.edEmail.setFocusableInTouchMode(false);
                binding.edEmail.setFocusable(false);
                binding.edEdu.setFocusableInTouchMode(false);
                binding.edEdu.setFocusable(false);
                binding.edExp.setFocusableInTouchMode(false);
                binding.edExp.setFocusable(false);
                binding.edTagLine.setFocusableInTouchMode(false);
                binding.edTagLine.setFocusable(false);
                binding.edBiography.setFocusableInTouchMode(false);
                binding.edBiography.setFocusable(false);
                binding.txtEditAll.setText(getActivity().getResources().getString(R.string.edit));

            } else if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.edit))) {
                binding.setOnEdit(true);
                binding.edName.setFocusableInTouchMode(true);
                binding.edName.setFocusable(true);
                binding.edPhone.setFocusableInTouchMode(true);
                binding.edPhone.setFocusable(true);
                binding.edEmail.setFocusableInTouchMode(true);
                binding.edEmail.setFocusable(true);
                binding.edEdu.setFocusableInTouchMode(true);
                binding.edEdu.setFocusable(true);
                binding.edExp.setFocusableInTouchMode(true);
                binding.edExp.setFocusable(true);
                binding.edTagLine.setFocusableInTouchMode(true);
                binding.edTagLine.setFocusable(true);
                binding.edBiography.setFocusableInTouchMode(true);
                binding.edBiography.setFocusable(true);
                binding.txtEditAll.setText(getActivity().getResources().getString(R.string.save));
            }
        }
    }

    @Override
    public void onUpdateProfile(MediaResponse media) {
        dialog.DismissDialog();
        String device_token = FirebaseInstanceId.getInstance().getToken();
        userMap.put("image", media.getImgFilePaths().get(0));
        userMap.put("name", binding.edName.getText().toString());
        userMap.put("thumb_image", "default");
        userMap.put("device_token", device_token);
        if (StaticMethods.userRegisterResponse != null) {
            if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                userMap.put("user_type", "tutor");
            } else {
                userMap.put("user_type", "student");
            }
        } else {
            if (StaticMethods.userData.getUserType().equals("tutor")) {
                userMap.put("user_type", "tutor");
            } else {
                userMap.put("user_type", "student");
            }
        }

        mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.e(GifHeaderParser.TAG, "fireBaseUpdate Image: " + "updated");
                }
            }
        });
        Picasso.with(getActivity()).load(media.getImgFilePaths().get(0)).into(binding.userImg);
        //StaticMethods.LoadImage(getActivity(), binding.userImg, media.getImgFilePaths().get(0), null);
        editProfilePic = true;
    }

    @Override
    public void onGetCer(GetCertificates cer) {

    }

    @Override
    public void onDelete(DeleteCertificates delete) {

    }

    @Override
    public void selectedValueSingleChoice(int position) {

    }

    @Override
    public void selectedValueSingleChoice(int position, String arrayType) {
        if (arrayType.equals("city")) {
            locationId = citiesList.get(position).getId();
            edCity.setText(citiesList.get(position).getName());
            edArea.setText("");
        } else if (arrayType.equals("area")) {
            locationId = areasList.get(position).getId();
            edArea.setText(areasList.get(position).getName());
            binding.edLocation.setText(edCity.getText().toString() + " , " + edArea.getText().toString());
        }
    }

    @Override
    public void selectedMultiChoicelang(ArrayList<String> choices, ArrayList<String> postions, String arrayType) {

    }

    @Override
    public void onSuccess(UserResponse<RegisterData> data) {

    }

    @Override
    public void onFail(boolean fail, String error) {

    }

    @Override
    public void onNoConnection(boolean noConnection) {

    }

    @Override
    public void onCity(ArrayList<CityItem> cityItems) {
        citiesList = new ArrayList<>();
        citiesList = cityItems;
    }

    @Override
    public void onArea(ArrayList<AreasItem> areasItems) {
        dialog.DismissDialog();
        areasList = new ArrayList<>();
        areasList = areasItems;
        if (areasList != null) {
            ArrayList<String> areasName = new ArrayList<>();
            ArrayList<Integer> areasId = new ArrayList<>();
            for (AreasItem item : areasList) {
                areasName.add(item.getName());
                areasId.add(item.getId());
            }
            dialogUtil.showSingleChooiceArrayList(getActivity(), R.string.city, R.string.ok, areasName, "area", areasId);
        }
    }

    public class ProfileHandler {
        Context context;

        public ProfileHandler(Context context) {
            this.context = context;
        }

        public void editLocation(View v) {
            if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
                if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.save))) {
                    binding.edLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openDialogChooseLocation();
                        }
                    });
                }
            } else {
                if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.save))) {
                    binding.edLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openDialogChooseLocation();
                        }
                    });
                }
            }
        }

        public void uploadImg(View v) {
            Intent getIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            getIntent.setType("image/*");
            startActivityForResult(getIntent, REQUEST_PICK_IMAGE_PROFILE);
        }

        public void onEditAll(View v) {
            if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
                if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.save))) {
                    if (editProfilePic) {
                        editProfilePic = false;
                        binding.setOnEdit(false);
                        binding.edName.setFocusableInTouchMode(false);
                        binding.edName.setFocusable(false);
                        binding.edPhone.setFocusableInTouchMode(false);
                        binding.edPhone.setFocusable(false);
                        binding.edEmail.setFocusableInTouchMode(false);
                        binding.edEmail.setFocusable(false);
                        binding.edEdu.setFocusableInTouchMode(false);
                        binding.edEdu.setFocusable(false);
                        binding.edExp.setFocusableInTouchMode(false);
                        binding.edExp.setFocusable(false);
                        binding.edTagLine.setFocusableInTouchMode(false);
                        binding.edTagLine.setFocusable(false);
                        binding.edBiography.setFocusableInTouchMode(false);
                        binding.edBiography.setFocusable(false);
                        binding.txtEditAll.setText(getActivity().getResources().getString(R.string.edit));
                    } else {
                        dialog.ShowDialog();
                        onSaveEdit(null);
                    }

                } else if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.edit))) {

                    binding.setOnEdit(true);
                    binding.edName.setFocusableInTouchMode(true);
                    binding.edName.setFocusable(true);

                    binding.edPhone.setFocusableInTouchMode(true);
                    binding.edPhone.setFocusable(true);
                    binding.edEmail.setFocusableInTouchMode(true);
                    binding.edEmail.setFocusable(true);
                    binding.edEdu.setFocusableInTouchMode(true);
                    binding.edEdu.setFocusable(true);
                    binding.edExp.setFocusableInTouchMode(true);
                    binding.edExp.setFocusable(true);
                    binding.edTagLine.setFocusableInTouchMode(true);
                    binding.edTagLine.setFocusable(true);
                    binding.edBiography.setFocusableInTouchMode(true);
                    binding.edBiography.setFocusable(true);
                    binding.txtEditAll.setText(getActivity().getResources().getString(R.string.save));

                }
            } else {
                if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.save))) {
                    if (editProfilePic) {
                        binding.setOnEdit(false);
                        editProfilePic = false;
                        binding.edName.setFocusableInTouchMode(false);
                        binding.edName.setFocusable(false);
                        binding.edPhone.setFocusableInTouchMode(false);
                        binding.edPhone.setFocusable(false);
                        binding.edEmail.setFocusableInTouchMode(false);
                        binding.edEmail.setFocusable(false);
                        binding.edEdu.setFocusableInTouchMode(false);
                        binding.edEdu.setFocusable(false);
                        binding.edExp.setFocusableInTouchMode(false);
                        binding.edExp.setFocusable(false);
                        binding.edTagLine.setFocusableInTouchMode(false);
                        binding.edTagLine.setFocusable(false);
                        binding.edBiography.setFocusableInTouchMode(false);
                        binding.edBiography.setFocusable(false);
                        binding.txtEditAll.setText(getActivity().getResources().getString(R.string.edit));
                    } else {
                        dialog.ShowDialog();
                        onSaveEdit(null);
                    }

                } else if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.edit))) {

                    binding.setOnEdit(true);
                    binding.edName.setFocusableInTouchMode(true);
                    binding.edName.setFocusable(true);
                    binding.edPhone.setFocusableInTouchMode(true);
                    binding.edPhone.setFocusable(true);
                    binding.edEmail.setFocusableInTouchMode(true);
                    binding.edEmail.setFocusable(true);
                    binding.edEdu.setFocusableInTouchMode(true);
                    binding.edEdu.setFocusable(true);
                    binding.edExp.setFocusableInTouchMode(true);
                    binding.edExp.setFocusable(true);
                    binding.edTagLine.setFocusableInTouchMode(true);
                    binding.edTagLine.setFocusable(true);
                    binding.edBiography.setFocusableInTouchMode(true);
                    binding.edBiography.setFocusable(true);
                    binding.txtEditAll.setText(getActivity().getResources().getString(R.string.save));
                }
            }


        }

        public void onEditMedia(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", 2);
            bundle.putBoolean("isEdit", true);
            ((MainScreen) getActivity()).onEditMedia(bundle);
        }

        public void onEditWork(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", 4);
            bundle.putBoolean("isEdit", true);
            ((MainScreen) getActivity()).onEditWorkDetails(bundle);
        }

        public void onEditTopics(View v) {
            Bundle bundle = new Bundle();
            if (StaticMethods.userRegisterResponse != null) {
                if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                    bundle.putString("type", "tutor");
                    bundle.putBoolean("isEdit", true);
                    bundle.putInt("position", 0);
                } else {
                    bundle.putString("type", "student");
                    bundle.putBoolean("isEdit", true);
                    bundle.putInt("position", 0);
                }
            } else if (StaticMethods.userData != null) {
                if (StaticMethods.userData.getUserType().equals("tutor")) {
                    bundle.putString("type", "tutor");
                    bundle.putBoolean("isEdit", true);
                    bundle.putInt("position", 0);
                } else {
                    bundle.putString("type", "student");
                    bundle.putBoolean("isEdit", true);
                    bundle.putInt("position", 0);
                }
            }
            ((MainScreen) getActivity()).onEditTopics(bundle);
        }

        public void onSaveEdit(View v) {

            if (StaticMethods.userRegisterResponse != null) {
                if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                    String Name, email,
                            phone, Experience, Education, Tagline, Biography;


                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();
                    Experience = binding.edExp.getText().toString();
                    Education = binding.edEdu.getText().toString();
                    Tagline = binding.edTagLine.getText().toString();
                    Biography = binding.edBiography.getText().toString();
                    //onEditDo();
                    presenter.updateTutorProfile(getActivity(), Name, email, phone, locationId, Experience, Education, Tagline, Biography);
                } else {
                    String Name, email, phone;
                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();
                    //onEditDo();
                    presenter.updateStudentProfile(getActivity(), Name, email, phone, locationId);
                }
            } else if (StaticMethods.userData != null) {
                if (StaticMethods.userData.getUserType().equals("tutor")) {
                    onEditDo();
                    String Name, email,
                            phone, Experience, Education, Tagline, Biography;
                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();
                    Experience = binding.edExp.getText().toString();
                    Education = binding.edEdu.getText().toString();
                    Tagline = binding.edTagLine.getText().toString();
                    Biography = binding.edBiography.getText().toString();
                    presenter.updateTutorProfile(getActivity(), Name, email, phone, locationId, Experience, Education, Tagline, Biography);
                } else {
                    //onEditDo();
                    String Name, email, phone;
                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();
                    presenter.updateStudentProfile(getActivity(), Name, email, phone, locationId);
                }
            }

        }
    }

    private void openDialogChooseLocation() {


        edCity.setText(data.DataProfile.getCity());
        edArea.setText(data.DataProfile.getArea());
        ImageView close = dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        edCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (citiesList != null) {
                    ArrayList<String> citiesName = new ArrayList<>();
                    ArrayList<Integer> citiesId = new ArrayList<>();
                    for (CityItem item : citiesList) {
                        citiesName.add(item.getName());
                        citiesId.add(item.getId());
                    }
                    dialogUtil.showSingleChooiceArrayList(getActivity(), R.string.city, R.string.ok, citiesName, "city", citiesId);
                }
            }
        });
        edArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.ShowDialog();
                String city = "";
                city = edCity.getText().toString();
                if (!city.isEmpty()) {
                    dialog.ShowDialog();
                    tutorPresenter.getAres(getActivity(), locationId);
                } else {
                    dialog.DismissDialog();
                    ToastUtil.showErrorToast(getActivity(), R.string.emptyCity);
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getProfileData(getActivity());
        dialog.ShowDialog();
    }

    private void onEditDo() {
        if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
            if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.edit))) {
                //presenter.updateTutorProfile(getActivity(), Name, email, phone, 2, Experience, Education, Tagline, Biography);
                binding.setOnEdit(false);
                binding.edName.setFocusableInTouchMode(false);
                binding.edName.setFocusable(false);
                binding.edPhone.setFocusableInTouchMode(false);
                binding.edPhone.setFocusable(false);
                binding.edEmail.setFocusableInTouchMode(false);
                binding.edEmail.setFocusable(false);
                binding.edEdu.setFocusableInTouchMode(false);
                binding.edEdu.setFocusable(false);
                binding.edExp.setFocusableInTouchMode(false);
                binding.edExp.setFocusable(false);
                binding.edTagLine.setFocusableInTouchMode(false);
                binding.edTagLine.setFocusable(false);
                binding.edBiography.setFocusableInTouchMode(false);
                binding.edBiography.setFocusable(false);
                binding.txtEditAll.setText(getActivity().getResources().getString(R.string.save));
            } else if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.save))) {
                binding.setOnEdit(true);
                binding.edName.setFocusableInTouchMode(true);
                binding.edName.setFocusable(true);
                binding.edPhone.setFocusableInTouchMode(true);
                binding.edPhone.setFocusable(true);
                binding.edEmail.setFocusableInTouchMode(true);
                binding.edEmail.setFocusable(true);
                binding.edEdu.setFocusableInTouchMode(true);
                binding.edEdu.setFocusable(true);
                binding.edExp.setFocusableInTouchMode(true);
                binding.edExp.setFocusable(true);
                binding.edTagLine.setFocusableInTouchMode(true);
                binding.edTagLine.setFocusable(true);
                binding.edBiography.setFocusableInTouchMode(true);
                binding.edBiography.setFocusable(true);
                binding.txtEditAll.setText(getActivity().getResources().getString(R.string.edit));
            }
        } else {
            if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.edit))) {
                binding.setOnEdit(false);

                //presenter.updateTutorProfile(getActivity(), Name, email, phone, 2, Experience, Education, Tagline, Biography);
                binding.edName.setFocusableInTouchMode(false);
                binding.edName.setFocusable(false);
                binding.edPhone.setFocusableInTouchMode(false);
                binding.edPhone.setFocusable(false);
                binding.edEmail.setFocusableInTouchMode(false);
                binding.edEmail.setFocusable(false);
                binding.edEdu.setFocusableInTouchMode(false);
                binding.edEdu.setFocusable(false);
                binding.edExp.setFocusableInTouchMode(false);
                binding.edExp.setFocusable(false);
                binding.edTagLine.setFocusableInTouchMode(false);
                binding.edTagLine.setFocusable(false);
                binding.edBiography.setFocusableInTouchMode(false);
                binding.edBiography.setFocusable(false);
                binding.txtEditAll.setText(getActivity().getResources().getString(R.string.save));
            } else if (binding.txtEditAll.getText().toString().equals(getActivity().getResources().getString(R.string.save))) {
                binding.setOnEdit(true);
                binding.edName.setFocusableInTouchMode(true);
                binding.edName.setFocusable(true);
                binding.edPhone.setFocusableInTouchMode(true);
                binding.edPhone.setFocusable(true);
                binding.edEmail.setFocusableInTouchMode(true);
                binding.edEmail.setFocusable(true);
                binding.edEdu.setFocusableInTouchMode(true);
                binding.edEdu.setFocusable(true);
                binding.edExp.setFocusableInTouchMode(true);
                binding.edExp.setFocusable(true);
                binding.edTagLine.setFocusableInTouchMode(true);
                binding.edTagLine.setFocusable(true);
                binding.edBiography.setFocusableInTouchMode(true);
                binding.edBiography.setFocusable(true);
                binding.txtEditAll.setText(getActivity().getResources().getString(R.string.edit));
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_PICK_IMAGE_PROFILE) {
                Uri uri = data.getData();
                profileImage = new ArrayList<>();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    //binding.userImg.setImageBitmap(bitmap);
                    File f = new File(getRealPathFromURI(getActivity(), uri));
                    profileImage.add(f);
                    JSONArray ProfileImage = new JSONArray(profileImage);
                    Log.e(TAG, "onActivityResult: " + ProfileImage.length());
                    dialog.ShowDialog();
                    if (StaticMethods.userRegisterResponse != null) {
                        if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                            presenter.uploadImageProfileTutor(getActivity(), profileImage);
                        } else {
                            presenter.uploadImageProfileStudent(getActivity(), profileImage);
                        }
                    } else if (StaticMethods.userData != null) {
                        if (StaticMethods.userData.getUserType().equals("tutor")) {
                            presenter.uploadImageProfileTutor(getActivity(), profileImage);
                        } else {
                            presenter.uploadImageProfileStudent(getActivity(), profileImage);
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}