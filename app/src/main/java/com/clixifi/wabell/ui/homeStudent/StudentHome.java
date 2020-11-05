package com.clixifi.wabell.ui.homeStudent;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
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
import com.clixifi.wabell.utils.StaticMethods;

import static android.content.ContentValues.TAG;


public class StudentHome extends Fragment implements StudentHomeInterface {

    FragmentStudentHomeBinding binding;
    View v;
    StudentHandler handler;
    FeaturedAdapter adapter;
    StudentHomePresenter presenter;
    CustomDialog dialog;
    TutorListAdapter listAdapter;


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
//        Log.e(TAG, "Token: "+ StaticMethods.userData.getToken());
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
    }

    @Override
    public void onFeaturedTutors(FeaturedArray featuredArray) {
        if(featuredArray.getItems().size() ==0){
            binding.dots.setVisibility(View.GONE);
        }
        binding.recFeatured.setHasFixedSize(true);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(5);
        binding.recFeatured.setLayoutManager(layoutManager);
        adapter = new FeaturedAdapter(getActivity(), featuredArray);

        binding.recFeatured.setAdapter(adapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.recFeatured);

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
        listAdapter = new TutorListAdapter(getActivity(), array);
        binding.recTutors.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recTutors.setAdapter(listAdapter);
    }

    @Override
    public void onFailLogs(boolean failLogs) {
        dialog.DismissDialog();
    }

    @Override
    public void onFilter(TutorListArray array) {

    }

    public class StudentHandler {
        Context context;

        public StudentHandler(Context context) {
            this.context = context;
        }

        public void onFilter(View v) {
            ((MainScreen) getActivity()).goToFilter();
        }
        public void onSearch(View v) {
            ((MainScreen) getActivity()).goToSearch();

        }
    }
}