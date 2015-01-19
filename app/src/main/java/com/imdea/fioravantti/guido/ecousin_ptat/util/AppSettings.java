package com.imdea.fioravantti.guido.ecousin_ptat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.imdea.fioravantti.guido.ecousin_ptat.Constants;

public class AppSettings {

    private Context context;

    /* Keys */
    private static final String KEY_MODE            = "mode";
    private static final String KEY_THRESHOLD       = "threshold";
    private static final String KEY_TCPDUMPENABLED  = "TCPDumpEnabled";
    private static final String KEY_BYTESPERPACK    = "bytesPerPack";
    private static final String KEY_MBPERCAP        = "MBPerPack";
    private static final String KEY_TIMEGUARD       = "timeGuard";
    private static final String KEY_SSD             = "SSD";
    private static final String KEY_SYSENABLED      = "sysEnabled";
    private static final String KEY_SYSGRAN         = "sysGran";
    private static final String KEY_BINNUM         = "binNum";

    public AppSettings (Context context) {
        setContext(context);
    }

    public void saveMode (int mode) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(KEY_MODE, mode);
        editor.commit();
    }

    public int getMode () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        int mode = pref.getInt(KEY_MODE, -1);

        return mode;
    }

    public void setThreshold (int threshold) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(KEY_THRESHOLD, threshold);
        editor.commit();
    }

    public int getThreshold () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        int threshold = pref.getInt(KEY_THRESHOLD, Constants.defaultThresholdKb);

        return threshold;
    }

    public void setTCPDumpEnabled (boolean TCPDumpEnabled) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(KEY_TCPDUMPENABLED, TCPDumpEnabled);
        editor.commit();
    }


    public boolean getTCPDumpEnabled () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        boolean TCPDumpEnabled = pref.getBoolean(
                KEY_TCPDUMPENABLED,
                Constants.defaultTCPDumpEnabled
        );

        return TCPDumpEnabled;
    }

    public void setBytesPerPack (int bytesPerPack) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(KEY_BYTESPERPACK, bytesPerPack);
        editor.commit();
    }

    public int getBytesPerPack () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        int bytesPerPack = pref.getInt(KEY_BYTESPERPACK, Constants.defaultBytesPerPack);

        return bytesPerPack;
    }

    public void setMBPerCap (int MBPerCap) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(KEY_MBPERCAP, MBPerCap);
        editor.commit();
    }

    public int getMBPerCap () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        int bytesPerPack = pref.getInt(KEY_MBPERCAP, Constants.defaultMBPerCap);

        return bytesPerPack;
    }

    public void setTimeGuard (int timeGuard) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(KEY_TIMEGUARD, timeGuard);
        editor.commit();
    }

    public int getSSD () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        int SSD = pref.getInt(KEY_SSD, Constants.defaultSSD);

        return SSD;
    }

    public void setBinNum (int binNum) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(KEY_BINNUM, binNum);
        editor.commit();
    }

    public int getBinNum () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        int binNum = pref.getInt(KEY_BINNUM, Constants.defaultBinNum);

        return binNum;
    }

    public void setSSD (int SSD) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(KEY_SSD, SSD);
        editor.commit();
    }

    public int getTimeGuard () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        int timeGuard = pref.getInt(KEY_TIMEGUARD, Constants.defaultTimeGuard);

        return timeGuard;
    }

    public void setSysEnabled (boolean sysEnabled) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(KEY_SYSENABLED, sysEnabled);
        editor.commit();
    }


    public boolean getSysEnabled () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        boolean sysEnabled = pref.getBoolean(
                KEY_SYSENABLED,
                Constants.defaultSysEnabled
        );

        return sysEnabled;
    }

    public void setSysGran (int sysGran) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(KEY_SYSGRAN, sysGran);
        editor.commit();
    }

    public int getSysGran () {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        int sysGran = pref.getInt(KEY_SYSGRAN, Constants.defaultSysGran);

        return sysGran;
    }


    void setContext(Context context) {
        this.context = context;
    }


}
