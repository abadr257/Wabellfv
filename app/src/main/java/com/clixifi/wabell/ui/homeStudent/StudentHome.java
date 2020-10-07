package com.clixifi.wabell.ui.homeStudent;

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
import com.clixifi.wabell.databinding.FragmentStudentHomeBinding;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.utils.LocaleManager;


public class StudentHome extends Fragment {

    FragmentStudentHomeBinding binding ;
    View v;
    StudentHandler handler ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_home, container, false);
        v = binding.getRoot();
        handler = new StudentHandler(getActivity());
        binding.setHandler(handler);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
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