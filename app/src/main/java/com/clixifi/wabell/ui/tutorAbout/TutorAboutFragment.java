package com.clixifi.wabell.ui.tutorAbout;

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
import com.clixifi.wabell.data.Response.AddFav.AddFavorite;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.TutorProfileData.TutorProfileForStudent;
import com.clixifi.wabell.databinding.FragmentTutorAboutBinding;
import com.clixifi.wabell.ui.Adapters.CertificatesAdapter;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileInterface;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfilePresenter;
import com.clixifi.wabell.utils.LocaleManager;

import static android.content.ContentValues.TAG;
import static com.clixifi.wabell.utils.StaticMethods.tutorId;


public class TutorAboutFragment extends Fragment implements TutorProfileInterface {
    FragmentTutorAboutBinding binding;
    View v;
    MyHandler handler;
    TutorProfilePresenter presenter;
    CertificatesAdapter adapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutor_about, container, false);
        v = binding.getRoot();
        handler = new MyHandler(getActivity());
        binding.setHandler(handler);
        presenter = new TutorProfilePresenter(this);
        presenter.getTutorData(getActivity(), tutorId , false);
        return v;
    }

    @Override
    public void onSuccess(TutorProfileForStudent tutor) {

        if(LocaleManager.getLanguage(getActivity()).equals("en")){
            if(tutor.getEngTopics() != null){

                int size = tutor.getEngTopics().size() ;

                if(size == 0 ){
                    binding.linSub1.setVisibility(View.GONE);
                    binding.linSub2.setVisibility(View.GONE);
                    binding.linSub3.setVisibility(View.GONE);
                }
                if(size >= 3){

                    binding.txtSub1.setText(tutor.getEngTopics().get(0));
                    binding.txtSub2.setText(tutor.getEngTopics().get(1));
                    binding.txtSubMany.setText("+"+(size - 2));
                }else {
                    if(size == 2 ){

                        binding.txtSub1.setText(tutor.getEngTopics().get(0));
                        binding.txtSub2.setText(tutor.getEngTopics().get(1));
                        binding.txtSubMany.setVisibility(View.GONE);
                    }else if(size == 1){

                        binding.txtSub1.setText(tutor.getEngTopics().get(0));
                        binding.linSub2.setVisibility(View.GONE);
                        binding.linSub3.setVisibility(View.GONE);
                    }
                }
            }else {
                binding.linSub1.setVisibility(View.GONE);
                binding.linSub2.setVisibility(View.GONE);
                binding.linSub3.setVisibility(View.GONE);
            }
        }else {
            if(tutor.getArTopics() != null){
                int size = tutor.getArTopics().size() ;
                if(size == 0 ){
                    binding.linSub1.setVisibility(View.GONE);
                    binding.linSub2.setVisibility(View.GONE);
                    binding.linSub3.setVisibility(View.GONE);
                }
                if(size >= 3){
                    binding.txtSub1.setText(tutor.getArTopics().get(0));
                    binding.txtSub2.setText(tutor.getArTopics().get(1));
                    binding.txtSubMany.setText("+"+(size - 2));
                }else {
                    if(size == 2 ){
                        binding.txtSub1.setText(tutor.getArTopics().get(0));
                        binding.txtSub2.setText(tutor.getArTopics().get(1));
                        binding.txtSubMany.setVisibility(View.GONE);
                    }else if(size == 1){
                        binding.txtSub1.setText(tutor.getArTopics().get(0));
                        binding.txtSub2.setVisibility(View.GONE);
                        binding.txtSubMany.setVisibility(View.GONE);
                    }
                }
            }else {
                binding.linSub1.setVisibility(View.GONE);
                binding.linSub2.setVisibility(View.GONE);
                binding.linSub3.setVisibility(View.GONE);
            }
        }

        binding.edBiography.setText(tutor.getBiography());
        binding.edEdu.setText(tutor.getEducation());
        binding.edExp.setText(tutor.getExperience());

        if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
            binding.edWorkDetails.setText(tutor.getHourPrice() + " ريال/للساعة " +"\n" +tutor.getAvailableTimesText() +"\n" + tutor.getAvailableDaysText());
        } else {
            binding.edWorkDetails.setText(tutor.getHourPrice() + " SAR/hr " +"\n" +tutor.getAvailableTimesText() +"\n" + tutor.getAvailableDaysText());
        }
        adapter = new CertificatesAdapter(getActivity() , tutor.getFiles() ,null);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.recCertificate.setLayoutManager(layoutManager);
        binding.recCertificate.setAdapter(adapter);
    }

    @Override
    public void onFail(boolean fail) {

    }

    @Override
    public void onConnection(boolean isConnected) {

    }

    @Override
    public void OnAddedToFavorite(AddFavorite addFavorite) {

    }

    @Override
    public void onDeleteFav(ResultBoolean result) {

    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void ViewAll(View v) {

        }

    }
}