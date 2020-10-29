package com.clixifi.wabell.ui.tutorReviews;

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
import com.clixifi.wabell.data.Response.ReviewsArray;
import com.clixifi.wabell.databinding.FragmentTutorReviewsBinding;
import com.clixifi.wabell.ui.Adapters.ReviewsAdapter;
import com.clixifi.wabell.utils.ToastUtil;

import static android.content.ContentValues.TAG;
import static com.clixifi.wabell.utils.StaticMethods.tutorId;


public class TutorReviewsFragment extends Fragment implements TutorReviewsInterface {
    FragmentTutorReviewsBinding binding ;
    View v ;
    ReviewsAdapter adapter ;
    MyHandler handler ;
    TutorReviewsPresenter presenter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutor_reviews, container, false);
        v = binding.getRoot();
        handler = new MyHandler(getActivity());
        binding.setHandler(handler);
        presenter = new TutorReviewsPresenter(this);
        Log.e(TAG, "onCreateView: "+tutorId );
        presenter.getReviews(getActivity() , tutorId);

        return v;
    }

    @Override
    public void onSuccess(ReviewsArray array) {
        if(array.getReviewsData().size() == 0 ){
            binding.noReviews.setVisibility(View.VISIBLE);
        }else {
            binding.noReviews.setVisibility(View.GONE);
            adapter = new ReviewsAdapter(getActivity() , array);
            Log.e(TAG, "onSuccess: Reviews "+array.getReviewsData().size() );

            binding.recReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recReviews.setAdapter(adapter);
        }

    }

    @Override
    public void onFail(boolean fail) {
        ToastUtil.showErrorToast(getActivity() , R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {
        ToastUtil.showErrorToast(getActivity() , R.string.noInternet);
    }

    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }

    }
}