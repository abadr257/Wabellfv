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
import com.clixifi.wabell.data.Response.ImageUrl;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.TutorProfileData.TutorProfileForStudent;
import com.clixifi.wabell.databinding.FragmentTutorAboutBinding;
import com.clixifi.wabell.ui.Adapters.CertificatesAdapter;
import com.clixifi.wabell.ui.Adapters.SubjectsAdapterInAbout;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileInterface;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfilePresenter;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileView;
import com.clixifi.wabell.utils.LocaleManager;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.clixifi.wabell.utils.StaticMethods.tutorId;


public class TutorAboutFragment extends Fragment implements TutorProfileInterface {
    FragmentTutorAboutBinding binding;
    View v;
    MyHandler handler;
    TutorProfilePresenter presenter;
    CertificatesAdapter adapter ;
    ArrayList<ImageUrl> listOfImages ;
    SubjectsAdapterInAbout adapterInAbout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
        this.listOfImages = tutor.getFiles();

        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        if(LocaleManager.getLanguage(getActivity()).equals("ar")){
            adapterInAbout = new SubjectsAdapterInAbout(tutor.getArTopics() ,getActivity() );
            binding.recSubjects.setLayoutManager(layoutManager1);
            binding.recSubjects.setAdapter(adapterInAbout);
        }else {
            adapterInAbout = new SubjectsAdapterInAbout(tutor.getEngTopics() ,getActivity() );
            binding.recSubjects.setLayoutManager(layoutManager1);
            binding.recSubjects.setAdapter(adapterInAbout);
        }
        binding.edBiography.setText(tutor.getBiography());
        binding.edEdu.setText(tutor.getEducation());
        binding.edExp.setText(tutor.getExperience());

        if (LocaleManager.getLanguage(getActivity()).equals("ar")) {
            binding.edWorkDetails.setText(tutor.getHourPrice() + " ريال / للساعة " +"\n" +tutor.getAvailableTimesText() +"\n" + tutor.getAvailableDaysText());
        } else {
            binding.edWorkDetails.setText(tutor.getHourPrice() + " SAR / Hr " +"\n" +tutor.getAvailableTimesText() +"\n" + tutor.getAvailableDaysText());
        }
        if(tutor.getFiles().size() == 0){
         binding.txtToMedia.setVisibility(View.GONE);
        }
        for (int i=0 ; i < tutor.getFiles().size() ; i ++){
            Log.e(TAG, "onFiles: "+tutor.getFiles().get(i).getFilePath() );
        }
        if (tutor.getFiles() != null) {
            if (tutor.getFiles().size() == 0) {
                binding.txtNoCer.setVisibility(View.VISIBLE);
            }else {
                adapter = new CertificatesAdapter(getActivity() , tutor.getFiles() ,null);
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                binding.recCertificate.setLayoutManager(layoutManager);
                binding.recCertificate.setAdapter(adapter);
            }
        }


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

    @Override
    public void onRequest(ResultBoolean resultBoolean) {

    }

    @Override
    public void onSendMessage(ResultBoolean resultBoolean) {

    }

    @Override
    public void onCall(ResultBoolean resultBoolean) {

    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void ViewAll(View v) {
            if (listOfImages.size() > 0){
                ((TutorProfileView) getActivity()).onViewAll(listOfImages);
            }
        }

    }
}