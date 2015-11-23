package com.imdea.fioravantti.guido.ecousin_ptat.model;

import android.content.Context;
import android.util.Log;

import com.imdea.fioravantti.guido.ecousin_ptat.Constants;
import com.imdea.fioravantti.guido.ecousin_ptat.util.AppSettings;
import com.imdea.fioravantti.guido.ecousin_ptat.util.TimeTools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by guido on 12/27/14.
 */
public class TCPDumpAPI {

    Context context;

    public TCPDumpAPI (Context context) {
        setContext(context);
    }

    public boolean isInstalled () {
        File tcpDump = new File(Constants.tcpDumpPath + "tcpdump");

        if(tcpDump.exists()) return true;

        return false;
    }

    public void ensureDirectory () {
        File dumpPath = new File(Constants.dumpPath);

        if (!dumpPath.exists()) dumpPath.mkdirs();
    }

    public String getDumpFilename () {
        String filename = new TimeTools().getBeautifulDate() + ".dump";

        return filename;
    }

    public boolean start () {
        AppSettings as = new AppSettings(getContext());
        int bytesPerPack = as.getBytesPerPack();
        int MBPerCap = as.getMBPerCap();

        final String command =
                        Constants.tcpDumpPath +
                        "tcpdump -w '" +
                        Constants.dumpPath + getDumpFilename() +
                        "' -s " +
                        bytesPerPack +
                        " -C " +
                        MBPerCap;

        Log.d(Constants.control, "Dump command is " + command);

        try {
            Process su = Runtime.getRuntime().exec("su");

            DataOutputStream dos = new DataOutputStream(su.getOutputStream());

            ensureDirectory();
            enterCommand(command, dos);

            dos.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean stop () {
        final String command = "ps tcpdump";

        Process su = null;
        try {
            su = Runtime.getRuntime().exec("su");
            DataOutputStream dos = new DataOutputStream(su.getOutputStream());

            enterCommand(command, dos);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    su.getInputStream()
            ));

            dos.close();

            ArrayList <String> pids = getRunningTCPDumpPIDs(br);

            su = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(su.getOutputStream());

            for (String pid : pids) enterCommand("kill " + pid, dos);

            dos.close();


            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    void enterCommand(String command, DataOutputStream dos) throws IOException {
        dos.writeBytes(command + "\n");
        dos.flush();
    }

    ArrayList<String> getRunningTCPDumpPIDs (BufferedReader br) throws IOException {
        ArrayList<String> pids = new ArrayList<String>();

        br.readLine();

        String line;
        while((line = br.readLine()) != null) {
            String [] parts = line.split("\\s+");
            pids.add(parts[1]);
        }

        return pids;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
