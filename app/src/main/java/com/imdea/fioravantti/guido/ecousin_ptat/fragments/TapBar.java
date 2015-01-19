package com.imdea.fioravantti.guido.ecousin_ptat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.imdea.fioravantti.guido.ecousin_ptat.R;
import com.imdea.fioravantti.guido.ecousin_ptat.activities.AboutActivity;
import com.imdea.fioravantti.guido.ecousin_ptat.activities.PreferencesActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TapBar extends Fragment {
    public TapBar() {
    }

    ImageButton btnPreferences;
    ImageButton btnAbout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tap_bar, container, false);

        btnPreferences = (ImageButton) rootView.findViewById(R.id.btnPreferences);
        btnAbout = (ImageButton) rootView.findViewById(R.id.btnAbout);

        registerEventListners();

        return rootView;
    }

    void registerEventListners() {
        btnPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(
                                getActivity().getApplicationContext(),
                                PreferencesActivity.class)
                );
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(
                                getActivity().getApplicationContext(),
                                AboutActivity.class)
                );
            }
        });
    }
}
