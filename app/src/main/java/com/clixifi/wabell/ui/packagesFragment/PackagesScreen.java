package com.clixifi.wabell.ui.packagesFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.CurrentPackages;
import com.clixifi.wabell.data.GetResult;
import com.clixifi.wabell.data.PackagesArray;
import com.clixifi.wabell.data.Response.User.ResultForProfile;
import com.clixifi.wabell.databinding.FragmentPackagesScreenBinding;
import com.clixifi.wabell.ui.Adapters.PackagesAdapter;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.ToastUtil;

import static android.content.ContentValues.TAG;

public class PackagesScreen extends Fragment implements PackagesInterface {


    public PackagesScreen() {

    }

    PackagesHandler handler;
    FragmentPackagesScreenBinding binding;
    View v;
    PackagesAdapter adapter;
    CustomDialog dialog;
    PackagesPresenter packagesPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_packages_screen, container, false);
        v = binding.getRoot();
        handler = new PackagesHandler(getActivity());
        binding.setHandler(handler);
        dialog = new CustomDialog(getActivity());
        packagesPresenter = new PackagesPresenter(this);
        packagesPresenter.getPackages(getActivity());
        packagesPresenter.getCurrent(getActivity());
        dialog.ShowDialog();
        return v;
    }

    @Override
    public void onPackages(PackagesArray array) {
        adapter = new PackagesAdapter(array, getActivity());
        binding.recPackages.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recPackages.setAdapter(adapter);
        dialog.DismissDialog();
    }

    @Override
    public void onCurrentPackage(GetResult<CurrentPackages> currentPackages) {
        if (currentPackages != null) {
            if (currentPackages.resultString != null) {
                Log.e(TAG, "onCurrentPackage: " + "Here");
                binding.cardCurrent.setVisibility(View.VISIBLE);
                binding.relExpired.setVisibility(View.VISIBLE);
                binding.linView.setVisibility(View.VISIBLE);


                if (LocaleManager.getLanguage(getActivity()).equals("en")) {
                    if (currentPackages.resultString.getRemainDays() > 1) {
                        binding.txtDays.setText(currentPackages.resultString.getRemainDays() + " Remaining Days");
                    } else if (currentPackages.resultString.getRemainDays() == 1) {
                        binding.txtDays.setText(currentPackages.resultString.getRemainDays() + " Remaining Day");
                    }

                    binding.txtDuration.setText(currentPackages.resultString.getPackageDuration());
                    binding.txtType.setText(currentPackages.resultString.getPackageDuration());
                    binding.txtSub.setText("You’ve subscribed to");
                    binding.txtUntil.setText(currentPackages.resultString.getStartDate() + " To " + currentPackages.resultString.ExpirationDate);
                    binding.txtPrice.setText(currentPackages.resultString.getPrice() + " SAR / Month ");
                } else {
                    if (currentPackages.resultString.getRemainDays() > 2) {
                        binding.txtDays.setText(currentPackages.resultString.getRemainDays() + " ايام متبقية");
                    } else if (currentPackages.resultString.getRemainDays() == 2 || currentPackages.resultString.getRemainDays() == 1) {
                        binding.txtDays.setText(currentPackages.resultString.getRemainDays() + " يوم متبقى");
                    }

                    binding.txtType.setText(currentPackages.resultString.getPackageDurationAr());
                    binding.txtSub.setText("أنت مشترك فى باقة :");
                    binding.txtDuration.setText(currentPackages.resultString.getPackageDurationAr());
                    binding.txtUntil.setText(currentPackages.resultString.getStartDate() + " إلى " + currentPackages.resultString.ExpirationDate);
                    binding.txtPrice.setText(currentPackages.resultString.getPrice() + " ريال / شهر ");
                }
                //binding.txtDuration.setText(currentPackages.resultString.getPackageDuration());
            }


        }
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

    public class PackagesHandler {
        Context context;

        public PackagesHandler(Context context) {
            this.context = context;
        }
    }
}