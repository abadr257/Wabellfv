package com.clixifi.wabell.ui.homeStudent;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.data.Response.featuredTutors.FeaturedArray;
import com.clixifi.wabell.databinding.FragmentStudentHomeBinding;
import com.clixifi.wabell.ui.Adapters.FeaturedAdapter;
import com.clixifi.wabell.ui.Adapters.TutorListAdapter;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;


public class StudentHome extends Fragment implements StudentHomeInterface {

    FragmentStudentHomeBinding binding ;
    View v;
    StudentHandler handler ;
    FeaturedAdapter adapter ;
    StudentHomePresenter presenter ;
    CustomDialog dialog ;
    TutorListAdapter listAdapter ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_home, container, false);
        v = binding.getRoot();
        handler = new StudentHandler(getActivity());
        binding.setHandler(handler);
        presenter = new StudentHomePresenter(this);
        dialog = new CustomDialog(getActivity());
        dialog.ShowDialog();
        presenter.getFeatured(getActivity());
        presenter.getTutorList(getActivity());
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
    }

    @Override
    public void onFeaturedTutors(FeaturedArray featuredArray) {
        dialog.DismissDialog();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.recFeatured.setLayoutManager(layoutManager);
        adapter = new FeaturedAdapter(getActivity() , featuredArray);
        binding.recFeatured.setAdapter(adapter);
    }

    @Override
    public void onFailFeatured(boolean failFeatured) {
        dialog.DismissDialog();
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
    }

    @Override
    public void onLogs(TutorListArray array) {
        dialog.DismissDialog();
        listAdapter = new TutorListAdapter(getActivity() , array );
        binding.recTutors.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recTutors.setAdapter(listAdapter);
    }

    @Override
    public void onFailLogs(boolean failLogs) {
        dialog.DismissDialog();
    }

    public class StudentHandler{
        Context context ;

        public StudentHandler(Context context) {
            this.context = context;
        }
        public void onFilter(View v){
            ((MainScreen)getActivity()).goToFilter();
        }
    }
}