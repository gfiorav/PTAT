package com.imdea.fioravantti.guido.ecousin_ptat.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.imdea.fioravantti.guido.ecousin_ptat.Constants;
import com.imdea.fioravantti.guido.ecousin_ptat.R;
import com.imdea.fioravantti.guido.ecousin_ptat.util.Animator;
import com.imdea.fioravantti.guido.ecousin_ptat.util.AppSettings;

public class PreferencesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PreferencesFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return true;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PreferencesFragment extends Fragment {

        Switch swEnableTCPDump;
        EditText edBytesPerPack;
        EditText edMBPerCap;
        EditText edThreshold;
        EditText edSafeguard;
        EditText edSSD;
        EditText edBinNum;
        Switch swSysEnabled;
        EditText edSysGran;

        Button btnDefaults;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_preferences, container, false);

            swEnableTCPDump     = (Switch)      rootView.findViewById(R.id.swEnableTCPDump);
            edBytesPerPack      = (EditText)    rootView.findViewById(R.id.edBytesPerPack);
            edMBPerCap          = (EditText)    rootView.findViewById(R.id.edMBPerCap);
            edThreshold         = (EditText)    rootView.findViewById(R.id.edThreshold);
            edSafeguard         = (EditText)    rootView.findViewById(R.id.edSafeguard);
            edSSD               = (EditText)    rootView.findViewById(R.id.edSSD);
            edBinNum            = (EditText)    rootView.findViewById(R.id.edBD);
            swSysEnabled        = (Switch)      rootView.findViewById(R.id.swEnableSys);
            edSysGran           = (EditText)    rootView.findViewById(R.id.edSysGran);

            btnDefaults         = (Button)      rootView.findViewById(R.id.btnDefaults);

            loadSettings();

            registerEventListeners();

            return rootView;
        }

        void loadSettings () {
            AppSettings as = new AppSettings(getActivity().getApplicationContext());

            swEnableTCPDump.setChecked(as.getTCPDumpEnabled());
            edBytesPerPack.setText(as.getBytesPerPack() + "");
            edMBPerCap.setText(as.getMBPerCap() + "");
            edThreshold.setText(as.getThreshold() + "");
            edSafeguard.setText(as.getTimeGuard() + "");
            edSSD.setText(as.getSSD() + "");
            edBinNum.setText(as.getBinNum() + "");
            swSysEnabled.setChecked(as.getSysEnabled());
            edSysGran.setText((as.getSysGran() / 60000) + "");
        }

        void registerEventListeners () {

            swEnableTCPDump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    new AppSettings(
                            getActivity().getApplicationContext()
                    ).setTCPDumpEnabled(isChecked);
                }
            });

            edBytesPerPack.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed = (EditText) v;

                    if (hasFocus) {
                        ed.setSelection(ed.length());
                    } else {
                        int toStore = Constants.defaultBytesPerPack;

                        try {
                            toStore = Integer.parseInt(ed.getText().toString());
                        } catch (NumberFormatException e) {
                            ed.setText(toStore + "");
                        }
                        new AppSettings(
                                getActivity().getApplicationContext()
                        ).setBytesPerPack(toStore);
                    }
                }
            });

            edMBPerCap.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed = (EditText) v;

                    if (hasFocus) {
                        ed.setSelection(ed.length());
                    } else {
                        int toStore = Constants.defaultMBPerCap;

                        try {
                            toStore = Integer.parseInt(ed.getText().toString());
                        } catch (NumberFormatException e) {
                            ed.setText(toStore + "");
                        }
                        new AppSettings(
                                getActivity().getApplicationContext()
                        ).setMBPerCap(toStore);
                    }
                }
            });

            edThreshold.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed = (EditText) v;

                    if(hasFocus) {
                        ed.setSelection(ed.length());
                    }
                    else {
                        int toStore = Constants.defaultThresholdKb;

                        try {
                            toStore = Integer.parseInt(ed.getText().toString());
                        } catch (NumberFormatException e) {
                            ed.setText(toStore + "");
                        }
                        new AppSettings(
                                getActivity().getApplicationContext()
                        ).setThreshold(toStore);
                    }
                }
            });

            edSafeguard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed = (EditText) v;

                    if(hasFocus) {
                        ed.setSelection(ed.length());
                    }
                    else {
                        int toStore = Constants.defaultTimeGuard;

                        try {
                            toStore = Integer.parseInt(ed.getText().toString());
                        } catch (NumberFormatException e) {
                            ed.setText(toStore + "");
                        }
                        new AppSettings(
                                getActivity().getApplicationContext()
                        ).setTimeGuard(toStore);
                    }
                }
            });

            edSSD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed = (EditText) v;

                    if(hasFocus) {
                        ed.setSelection(ed.length());
                    }
                    else {
                        int toStore = Constants.defaultSSD;

                        try {
                            toStore = Integer.parseInt(ed.getText().toString());
                        } catch (NumberFormatException e) {
                            ed.setText(toStore + "");
                        }
                        new AppSettings(
                                getActivity().getApplicationContext()
                        ).setSSD(toStore);
                    }
                }
            });

            edBinNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed = (EditText) v;

                    if(hasFocus) {
                        ed.setSelection(ed.length());
                    }
                    else {
                        int toStore = Constants.defaultBinNum;

                        try {
                            toStore = Integer.parseInt(ed.getText().toString());
                        } catch (NumberFormatException e) {
                            ed.setText(toStore + "");
                        }
                        new AppSettings(
                                getActivity().getApplicationContext()
                        ).setBinNum(toStore);
                    }
                }
            });

            swSysEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    new AppSettings(
                            getActivity().getApplicationContext()
                    ).setSysEnabled(isChecked);
                }
            });

            edSysGran.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed = (EditText) v;

                    if(hasFocus) {
                        ed.setSelection(ed.length());
                    }
                    else {
                        int toStore = Constants.defaultSysGran;

                        try {
                            toStore = Integer.parseInt(ed.getText().toString());
                        } catch (NumberFormatException e) {
                            ed.setText(toStore + "");
                        }
                        new AppSettings(
                                getActivity().getApplicationContext()
                        ).setSysGran(toStore * 60000);
                    }
                }
            });

            btnDefaults.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;

                    if(b.getText() == Constants.dialogueConfirm) {
                        resetDefaults();

                        b.setText(getResources().getText(R.string.defaults));

                        new Animator(getActivity()).changeTextColor(
                                android.R.color.holo_red_dark,
                                android.R.color.white,
                                b
                        );
                    }
                    else {
                        b.setText(Constants.dialogueConfirm);

                        new Animator(getActivity()).changeTextColor(
                                android.R.color.white,
                                android.R.color.holo_red_dark,
                                b
                        );
                    }
                }
            });
        }

        void resetDefaults () {
            AppSettings as = new AppSettings(getActivity().getApplicationContext());

            as.setTCPDumpEnabled(Constants.defaultTCPDumpEnabled);
            as.setBytesPerPack(Constants.defaultBytesPerPack);
            as.setMBPerCap(Constants.defaultMBPerCap);
            as.setThreshold(Constants.defaultThresholdKb);
            as.setTimeGuard(Constants.defaultTimeGuard);
            as.setSSD(Constants.defaultSSD);
            as.setBinNum(Constants.defaultBinNum);
            as.setSysEnabled(Constants.defaultSysEnabled);
            as.setSysGran(Constants.defaultSysGran);

            loadSettings();
        }
    }
}
