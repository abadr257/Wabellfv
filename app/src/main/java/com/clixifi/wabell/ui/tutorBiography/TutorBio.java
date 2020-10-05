package com.clixifi.wabell.ui.tutorBiography;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.FragmentTutorBioBinding;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.ToastUtil;


public class TutorBio extends Fragment implements BioInterface {

    FragmentTutorBioBinding bioBinding ;
    View v ;
    MyHandlers handlers ;
    BioPresenter presenter ;
    CustomDialog dialog ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bioBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutor_bio, container, false);
        v = bioBinding.getRoot();
        handlers = new MyHandlers(getActivity());
        bioBinding.setHandler(handlers);
        presenter = new BioPresenter(this);
        dialog = new CustomDialog(getActivity());
        return v;
    }
    public class MyHandlers{
        Context context ;

        public MyHandlers(Context context) {
            this.context = context;
        }
        public void nextStep(View v){
            dialog.ShowDialog();
            String tag = bioBinding.edTag.getText().toString();
            String biography = bioBinding.edBiography.getText().toString();
            if (tag.isEmpty() && biography.isEmpty()){
                dialog.DismissDialog();
                ((TutorSteps)getActivity()).step4();
            }else {
                presenter.uploadData(getActivity() , tag , biography);
            }
        }
    }
    @Override
    public void onAddedSuccess(boolean success) {
        dialog.DismissDialog();
        ((TutorSteps)getActivity()).step4();
    }

    @Override
    public void onAddedFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.error);
    }

    @Override
    public void onNoConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.noInternet);
    }

    @Override
    public void onEmptyFields(boolean empty) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(getActivity() , R.string.empty);
    }
}