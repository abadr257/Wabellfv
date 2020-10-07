package com.clixifi.wabell.ui.registerTutor;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.data.Response.areas.AreasItem;
import com.clixifi.wabell.data.Response.cities.CityItem;
import com.clixifi.wabell.databinding.FragmentRegisterTutorBinding;
import com.clixifi.wabell.ui.register.RegisterScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtilResponse;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class RegisterTutor extends Fragment implements DialogUtilResponse, TutorInterface {

    FragmentRegisterTutorBinding binding;
    View v;
    MyHandlers handlers;
    DialogUtil dialogUtil;
    TutorPresenter tutorPresenter;
    CustomDialog dialog;
    ArrayList<CityItem> citiesList;
    ArrayList<AreasItem> areasList;
    int locationId = 1;
    String UserType = "tutor";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_tutor, container, false);
        v = binding.getRoot();
        handlers = new MyHandlers(getActivity());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandler(handlers);
        initialViews();
        return v;
    }

    private void initialViews() {
        dialog = new CustomDialog(getActivity());
        tutorPresenter = new TutorPresenter(this);
        tutorPresenter.getCities(getActivity());
        //tutorPresenter.getAres(getActivity() , 1);
        dialogUtil = new DialogUtil(this);

    }

    @Override
    public void selectedValueSingleChoice(int position) {

    }

    @Override
    public void selectedValueSingleChoice(int position, String arrayType) {
        if (arrayType.equals("city")) {
            locationId = citiesList.get(position).getId();
            tutorPresenter.getAres(getActivity(), locationId);
            binding.edCity.setText(citiesList.get(position).getName());
        } else if (arrayType.equals("area")) {
            locationId = areasList.get(position).getId();
            binding.edNeighborhood.setText(areasList.get(position).getName());
        }
    }

    @Override
    public void selectedMultiChoicelang(ArrayList<String> choices, ArrayList<String> postions, String arrayType) {

    }

    @Override
    public void onSuccess(UserResponse<RegisterData> data) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(getActivity(), R.string.registerSuccess);
        Bundle bundle = new Bundle();
        bundle.putString("email", data.Data.getEmail());
        bundle.putString("type", UserType);
        bundle.putBoolean("forget", false);
        StaticMethods.userRegisterResponse = data;
        StaticMethods.printJson("RegisterData", data.Data.getEmail());
        ((RegisterScreen) getActivity()).openVerificationScreen(bundle);
    }

    @Override
    public void onFail(boolean fail, String error) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity(), error);
    }

    @Override
    public void onNoConnection(boolean noConnection) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity(), R.string.noInternet);
    }

    @Override
    public void onCity(ArrayList<CityItem> cityItems) {
        citiesList = new ArrayList<>();
        citiesList = cityItems;
    }

    @Override
    public void onArea(ArrayList<AreasItem> areasItems) {
        areasList = new ArrayList<>();
        areasList = areasItems;
    }

    public class MyHandlers {
        Context context;

        public MyHandlers(Context context) {
            this.context = context;
        }

        public void goLogin(View v) {
            ((RegisterScreen) getActivity()).goToLogin();
        }

        public void registerView(View v) {
            dialog.ShowDialog();
            if (binding.checkTerms.isChecked()) {
                String email = binding.edEmail.getText().toString();
                String pass = binding.edPassword.getText().toString();
                String phone = binding.edPhone.getText().toString();
                String UserName = binding.edName.getText().toString();
                if (email.isEmpty() || pass.isEmpty() || phone.isEmpty() || UserName.isEmpty()) {
                    dialog.DismissDialog();
                    ToastUtil.showErrorToast(getActivity(), R.string.empty);
                } else if (phone.length() < 9) {
                    dialog.DismissDialog();
                    ToastUtil.showErrorToast(getActivity(), R.string.validPhone);
                } else if (isEmailValid(email)) {
                    Log.e(TAG, "registerView: " + locationId);
                    tutorPresenter.tutorRegister(getActivity(), email, pass, phone, UserName, locationId, UserType);
                } else {
                    dialog.DismissDialog();
                    ToastUtil.showErrorToast(getActivity(), R.string.emailValid);
                }
            } else {
                dialog.DismissDialog();
                ToastUtil.showErrorToast(getActivity(), R.string.terms);
            }

        }

        public void terms(View v) {
            ((RegisterScreen) getActivity()).terms();
        }

        public void privacy(View v) {
            ((RegisterScreen) getActivity()).privacy();
        }

        public void city(View v) {
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

        public boolean isEmailValid(String email) {
            String regExpn =
                    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

            CharSequence inputStr = email;

            Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);

            if (matcher.matches())
                return true;
            else
                return false;
        }

        public void area(View v) {
            if (areasList != null) {
                ArrayList<String> areasName = new ArrayList<>();
                ArrayList<Integer> areasId = new ArrayList<>();
                for (AreasItem item : areasList) {
                    areasName.add(item.getName());
                    areasId.add(item.getId());
                }
                dialogUtil.showSingleChooiceArrayList(getActivity(), R.string.neighborhood, R.string.ok, areasName, "area", areasId);
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
    }
}