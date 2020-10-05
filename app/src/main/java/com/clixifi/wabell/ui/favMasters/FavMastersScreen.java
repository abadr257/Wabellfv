package com.clixifi.wabell.ui.favMasters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.favMasters.FavMastersStudent;
import com.clixifi.wabell.databinding.ActivityFavMastersScreenBinding;
import com.clixifi.wabell.ui.Adapters.FavMastersAdapter;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.ToastUtil;

public class FavMastersScreen extends AppCompatActivity implements FavMastersInterface {
    ActivityFavMastersScreenBinding binding ;
    FavHandler handler ;
    FavMastersPresenter presenter ;
    CustomDialog dialog ;
    FavMastersAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_fav_masters_screen);
        handler = new FavHandler(this);
        binding.setHandler(handler);
        dialog = new CustomDialog(this);
        presenter = new FavMastersPresenter(this);
        dialog.ShowDialog();
        presenter.getFav(this);
    }

    @Override
    public void onSuccess(FavMastersStudent fav) {
        dialog.DismissDialog();
        adapter = new FavMastersAdapter(FavMastersScreen.this ,fav);
        binding.recFavMasters.setLayoutManager(new LinearLayoutManager(this));
        binding.recFavMasters.setAdapter(adapter);
    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(FavMastersScreen.this ,R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(FavMastersScreen.this ,R.string.noInternet);
    }

    public class FavHandler{
        Context c ;

        public FavHandler(Context c) {
            this.c = c;
        }
        public void back(View view){
            onBackPressed();
        }
    }
}