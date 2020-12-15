package com.clixifi.wabell.ui.messages;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.FragmentMessageAndCallScreenBinding;
import com.clixifi.wabell.ui.Adapters.MessagePagerAdapter;
import com.clixifi.wabell.ui.Adapters.PagerAdapter;
import com.clixifi.wabell.ui.register.RegisterScreen;
import com.clixifi.wabell.utils.LocaleManager;


public class MessageAndCallScreen extends Fragment {
    FragmentMessageAndCallScreenBinding binding;
    View v;
    MessageHandler handler;
    MessagePagerAdapter adapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_and_call_screen, container, false);
        v = binding.getRoot();
        handler = new MessageHandler(getActivity());
        binding.setHandler(handler);
        configTabs();
        return v;
    }

    private void configTabs() {
        adapter = new MessagePagerAdapter(getChildFragmentManager(), getActivity());
        binding.tabLayout.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(adapter);
    }

    public class MessageHandler {
        Context context;

        public MessageHandler(Context context) {
            this.context = context;
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(LocaleManager.onAttach(context));
    }
}