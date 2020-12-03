package com.clixifi.wabell.ui.registerTutor;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
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
    boolean inputTypeChanged = false ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    String email , phone , UserName , pass ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_tutor, container, false);
        v = binding.getRoot();
        handlers = new MyHandlers(getActivity());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandler(handlers);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(getActivity())) {

            // Force a right-aligned text entry, otherwise latin character input,
            // like "abc123", will jump to the left and may even disappear!
            binding.edPhone.setTextDirection(View.TEXT_DIRECTION_RTL);

            // Make the "Enter password" hint display on the right hand side
            binding.edPhone.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(getActivity())) {

            // Force a right-aligned text entry, otherwise latin character input,
            // like "abc123", will jump to the left and may even disappear!
            binding.edPassword.setTextDirection(View.TEXT_DIRECTION_RTL);

            // Make the "Enter password" hint display on the right hand side
            binding.edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }
        binding.edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isRTL(getActivity())) {
                    if (s.length() > 0) {
                        if (!inputTypeChanged) {

                            // When a character is typed, dynamically change the EditText's
                            // InputType to PASSWORD, to show the dots and conceal the typed characters.
                            binding.edPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                            // Move the cursor to the correct place (after the typed character)
                            binding.edPassword.setSelection(s.length());

                            inputTypeChanged = true;
                        }
                    } else {
                        // Reset EditText: Make the "Enter password" hint display on the right
                        binding.edPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

                        inputTypeChanged = false;
                    }
                }
            }

        });
        initialViews();
        return v;
    }
    public static boolean isRTL(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return context.getResources().getConfiguration().getLayoutDirection()
                    == View.LAYOUT_DIRECTION_RTL;
            // Another way:
            // Define a boolean resource as "true" in res/values-ldrtl
            // and "false" in res/values
            // return context.getResources().getBoolean(R.bool.is_right_to_left);
        } else {
            return false;
        }
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
            binding.edCity.setText(citiesList.get(position).getName());
            binding.edNeighborhood.setText("");
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
                 email = binding.edEmail.getText().toString();
                 pass = binding.edPassword.getText().toString();
                 phone = binding.edPhone.getText().toString();
                 UserName = binding.edName.getText().toString();
                if (email.isEmpty() || pass.isEmpty() || phone.isEmpty() || UserName.isEmpty()) {
                    dialog.DismissDialog();
                    ToastUtil.showErrorToast(getActivity(), R.string.empty);
                } else if (phone.length() < 9) {
                    dialog.DismissDialog();
                    ToastUtil.showErrorToast(getActivity(), R.string.validPhone);
                } else if (isEmailValid(email)) {
                    Log.e(TAG, "registerView: " + locationId);
                    registerWithFirebase(email ,pass);
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
            dialog.ShowDialog();
            if(!binding.edCity.getText().toString().isEmpty()){
                tutorPresenter.getAres(getActivity(), locationId);
            }else {
                ToastUtil.showErrorToast(getActivity() , R.string.emptyCity);
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
    }

    public void registerWithFirebase(String emailF , String passF){

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(emailF, passF)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            final String uid = current_user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            String device_token = FirebaseInstanceId.getInstance().getToken();
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", binding.edName.getText().toString());
                            userMap.put("image", "default");
                            userMap.put("thumb_image", "default");
                            userMap.put("device_token", device_token);
                            userMap.put("user_type", "tutor");

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.e(TAG, "onComplete: " + "Success");
                                        tutorPresenter.tutorRegister(getActivity(), email, pass, phone, UserName, locationId, UserType , uid);
                                    }
                                }
                            });
                        }else {
                            dialog.DismissDialog();
                            ToastUtil.showErrorToast(getActivity(),R.string.already);
                        }
                    }
                });
    }
}