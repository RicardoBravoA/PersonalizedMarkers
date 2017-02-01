package com.rba.markersdemo.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rba.markersdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ricardo Bravo on 31/01/17.
 */

public class MyInfoWindow extends Fragment {

    private MapView mapView;
    @BindView(R.id.lblMessage) AppCompatTextView lblMessagelblMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_window, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

    }

    @OnClick(R.id.imgCar)
    public void onClickCar(){
        mapView.onMarkerClick();
    }

    public void setTitle(String title){
        lblMessagelblMessage.setText(title);
    }

    public void setView(MapView mapView){
        this.mapView = mapView;
    }

}
