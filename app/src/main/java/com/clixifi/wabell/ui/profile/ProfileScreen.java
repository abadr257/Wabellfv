package com.clixifi.wabell.ui.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.User.UserProfile;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.databinding.FragmentProfileScreenBinding;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;


public class ProfileScreen extends Fragment implements ProfileInteface {

    FragmentProfileScreenBinding binding;
    View v;
    ProfileHandler profile;
    CustomDialog dialog;
    ProfilePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_screen, container, false);
        v = binding.getRoot();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        profile = new ProfileHandler(getActivity());
        binding.setHandler(profile);
        dialog = new CustomDialog(getActivity());
        presenter = new ProfilePresenter(this);

        binding.setOnEdit(false);
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

    private void fillUserData() {
        dialog.ShowDialog();
        presenter.getProfileData(getActivity());
        if (binding.txtEditAll.getText().toString().equals("Save")) {
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
    public void onSuccess(UserResponse<UserProfile> profile) {
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
        if (profile.DataProfile.getAvailableDays() != null || profile.DataProfile.getAvailableTimes() != null ) {
            binding.edWorkDetails.setText(profile.DataProfile.getAvailableDays()+"\n"+profile.DataProfile.getAvailableTimes());
        }

        binding.edEmail.setText(profile.DataProfile.getEmail());
        binding.edName.setText(profile.DataProfile.getName());
        binding.userName.setText(profile.DataProfile.getName());
        binding.edPhone.setText(profile.DataProfile.getPhoneNumber());
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
        if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
            if (binding.txtEditAll.getText().toString().equals("حفظ")) {
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
            } else if (binding.txtEditAll.getText().toString().equals("تعديل")) {
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
            if (binding.txtEditAll.getText().toString().equals("Save")) {
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
            } else if (binding.txtEditAll.getText().toString().equals("Edit")) {
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

    public class ProfileHandler {
        Context context;

        public ProfileHandler(Context context) {
            this.context = context;
        }

        public void uploadImg(View v) {

        }

        public void onEditAll(View v) {

            String Name, email,
                    phone, Experience, Education, Tagline, Biography;
            int LocationId;


            if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
                if (binding.txtEditAll.getText().toString().equals("حفظ")) {
                    dialog.ShowDialog();
                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();
                    Experience = binding.edExp.getText().toString();
                    Education = binding.edEdu.getText().toString();
                    Tagline = binding.edTagLine.getText().toString();
                    Biography = binding.edBiography.getText().toString();
                    presenter.updateTutorProfile(getActivity(), Name, email, phone, 2, Experience, Education, Tagline, Biography);
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
                } else if (binding.txtEditAll.getText().toString().equals("تعديل")) {
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
                if (binding.txtEditAll.getText().toString().equals("Save")) {
                    binding.setOnEdit(false);
                    dialog.ShowDialog();
                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();
                    Experience = binding.edExp.getText().toString();
                    Education = binding.edEdu.getText().toString();
                    Tagline = binding.edTagLine.getText().toString();
                    Biography = binding.edBiography.getText().toString();
                    presenter.updateTutorProfile(getActivity(), Name, email, phone, 2, Experience, Education, Tagline, Biography);
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
                } else if (binding.txtEditAll.getText().toString().equals("Edit")) {
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
            dialog.ShowDialog();
            if (StaticMethods.userRegisterResponse != null) {
                if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                    String Name, email,
                            phone, Experience, Education, Tagline, Biography;
                    int LocationId;

                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();
                    Experience = binding.edExp.getText().toString();
                    Education = binding.edEdu.getText().toString();
                    Tagline = binding.edTagLine.getText().toString();
                    Biography = binding.edBiography.getText().toString();
                    presenter.updateTutorProfile(getActivity(), Name, email, phone, 2, Experience, Education, Tagline, Biography);
                } else {
                    String Name, email, phone;
                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();

                    presenter.updateStudentProfile(getActivity(), Name, email, phone, 2);
                }
            } else if (StaticMethods.userData != null) {
                if (StaticMethods.userData.getUserType().equals("tutor")) {
                    String Name, email,
                            phone, Experience, Education, Tagline, Biography;
                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();
                    Experience = binding.edExp.getText().toString();
                    Education = binding.edEdu.getText().toString();
                    Tagline = binding.edTagLine.getText().toString();
                    Biography = binding.edBiography.getText().toString();
                    presenter.updateTutorProfile(getActivity(), Name, email, phone, 2, Experience, Education, Tagline, Biography);
                } else {
                    String Name, email, phone;
                    Name = binding.edName.getText().toString();
                    email = binding.edEmail.getText().toString();
                    phone = binding.edPhone.getText().toString();

                    presenter.updateStudentProfile(getActivity(), Name, email, phone, 2);
                }
            }

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
    }
}