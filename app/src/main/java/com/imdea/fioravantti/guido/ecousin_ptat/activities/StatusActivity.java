package com.imdea.fioravantti.guido.ecousin_ptat.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imdea.fioravantti.guido.ecousin_ptat.Constants;
import com.imdea.fioravantti.guido.ecousin_ptat.R;
import com.imdea.fioravantti.guido.ecousin_ptat.model.TCPDumpAPI;
import com.imdea.fioravantti.guido.ecousin_ptat.tasks.Benchmark;
import com.imdea.fioravantti.guido.ecousin_ptat.tasks.DownloadFile;
import com.imdea.fioravantti.guido.ecousin_ptat.util.Animator;
import com.imdea.fioravantti.guido.ecousin_ptat.util.AppSettings;
import com.imdea.fioravantti.guido.ecousin_ptat.util.Notifier;
import com.imdea.fioravantti.guido.ecousin_ptat.util.TimeTools;

import java.util.Timer;
import java.util.TimerTask;


public class StatusActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

    }

    @Override
    protected void onDestroy() {
        super.onPause();

        new Notifier(getApplicationContext(), StatusActivity.class).hideRunning();
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);

        Log.d(Constants.control, "Working in the background...");
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class StatusFragment extends Fragment {
        Context context;

        NotificationManager nm;

        private int mode;

        Button btnStart;
        TextView txtStatus;

        LinearLayout frmInfo;
        TextView txtUpTime;
        TextView txtBytes;
        TextView txtBenchs;

        Timer tmrCounter;
        Timer tmrSysDown;
        ByteCounter tskCounter;
        SystematicDownloads tskSysDown;
        Handler handler;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_status, container, false);

            setContext(getActivity().getApplicationContext());

            txtStatus   = (TextView)        rootView.findViewById(R.id.txtStatus);
            btnStart    = (Button)          rootView.findViewById(R.id.btnStart);

            frmInfo     = (LinearLayout)    rootView.findViewById(R.id.frmInfo);
            txtUpTime   = (TextView)        rootView.findViewById(R.id.txtUpTime);
            txtBytes    = (TextView)        rootView.findViewById(R.id.txtPacketCounter);
            txtBenchs   = (TextView)        rootView.findViewById(R.id.txtBenchmarkCounter);

            handler     = new Handler();

            setMode(Constants.notInstalled);

            registerEventListeners(rootView);

            if(new TCPDumpAPI(getContext()).isInstalled()) changeMode(rootView, Constants.stopped);

            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();

            AppSettings as = new AppSettings(getContext());
            as.saveMode(getMode());

        }

        void registerEventListeners (View rootView) {
            final View view = rootView;

            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (getMode()) {
                        case Constants.stopped:
                            if(new TCPDumpAPI(getContext()).start()) {
                                changeMode(view, Constants.running);

                                tmrCounter = new Timer();
                                tskCounter = new ByteCounter(getContext());
                                tmrCounter.scheduleAtFixedRate(
                                        tskCounter,
                                        Constants.now,
                                        Constants.counterGranularity
                                );

                                if(new AppSettings(getContext()).getSysEnabled()) {
                                    tmrSysDown = new Timer();
                                    tskSysDown = new SystematicDownloads();
                                    tmrSysDown.scheduleAtFixedRate(
                                            tskSysDown,
                                            Constants.now,
                                            new AppSettings(getContext()).getSysGran()
                                    );
                                }

                            }

                            break;

                        case Constants.running:
                            if(new TCPDumpAPI(getContext()).stop()) {
                                changeMode(view, Constants.stopped);

                                while(!tskCounter.cancel());
                                tmrCounter.cancel();
                                tmrCounter.purge();


                                if(tskSysDown != null && tmrSysDown != null) {
                                    while(!tskSysDown.cancel());
                                    tmrSysDown.cancel();
                                    tmrSysDown.purge();

                                    tskSysDown = null;
                                    tmrSysDown = null;

                                }

                            }

                            break;

                        default:
                            break;
                    }
                }
            });
        }

        void changeMode (final View rootView, int mode) {
            switch (mode) {
                case Constants.notInstalled:
                    rootView.setBackgroundColor(
                            rootView.getResources().getColor(android.R.color.holo_orange_light)
                    );
                    txtStatus.setText(R.string.txtTCPDumpMissing);

                    btnStart.setVisibility(View.GONE);

                    setMode(Constants.notInstalled);
                    break;

                case Constants.stopped:
                    new Notifier(
                            getActivity().getApplicationContext(),
                            StatusActivity.class).hideRunning();

                    new Animator(getContext()).changeBackgroundColor(
                            android.R.color.holo_green_light,
                            android.R.color.darker_gray,
                            rootView
                    );

                    txtStatus.setText(R.string.stopped);

                    btnStart.setText(R.string.start);
                    btnStart.setVisibility(View.VISIBLE);

                    frmInfo.setVisibility(View.GONE);

                    setMode(Constants.stopped);
                    break;

                case Constants.running:
                    new Notifier(
                            getContext(),
                            StatusActivity.class).showRunning();

                    new Animator(getContext()).changeBackgroundColor(
                            android.R.color.darker_gray,
                            android.R.color.holo_green_light,
                            rootView
                    );

                    txtStatus.setText(R.string.running);

                    btnStart.setText(R.string.stop);
                    btnStart.setVisibility(View.VISIBLE);

                    frmInfo.setVisibility(View.VISIBLE);

                    setMode(Constants.running);
                    break;

                default:
                    Log.wtf(Constants.error, "Invalid mode");

                    System.exit(-1);
                    break;

            }
        }

        public void updateInfo () {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    long upTime = (System.currentTimeMillis() - tskCounter.getTimestamp()) / 1000;
                    long downBytes = tskCounter.getCurRx();
                    long upBytes = tskCounter.getCurTx();
                    long bytes = downBytes + upBytes;
                    int benchs = tskCounter.getBenchmarksMade();

                    txtUpTime.setText(
                            "Been running for " + new TimeTools().getBeautifulTimestamp(upTime)
                    );
                    txtBytes.setText(String.format("%d Kbytes being processed (%d ul/ %d dl)",
                            bytes / 1024,
                            upBytes / 1024,
                            downBytes /1024
                    ));
                    txtBenchs.setText(String.format("%d benchmarks made", benchs));

                }
            });
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public class SystematicDownloads extends  TimerTask {

            @Override
            public void run() {
                Log.d(Constants.control, "Systematic Download happening. Next one in " +
                    new AppSettings(getContext()).getSysGran()/1000 + " seconds...");
                new DownloadFile().execute(Constants.downloadLink);
            }
        }

        public class ByteCounter extends TimerTask {

            long timestamp;

            long totalRx;
            long totalTx;

            long curRx;
            long curTx;

            int threshold;
            int benchmarksMade;

            boolean pendingBenchmark;
            int timeGuard;

            AsyncTask benchmark;

            TrafficStats ts;

            Context context;

            public ByteCounter(Context context) {
                setContext(context);

                timestamp = System.currentTimeMillis();

                ts = new TrafficStats();

                totalRx = ts.getMobileRxBytes();
                totalTx = ts.getMobileTxBytes();

                curRx = 0;
                curTx = 0;

                threshold = new AppSettings(context).getThreshold() * 1024;
                benchmarksMade = 0;

                pendingBenchmark = false;
                timeGuard = 0;

                benchmark = new Benchmark(getContext());
            }

            @Override
            public void run() {
                curRx = ts.getMobileRxBytes() - totalRx;
                curTx = ts.getMobileTxBytes() - totalTx;

                if (!pendingBenchmark) {
                    if (curRx >= threshold || curTx >= threshold) {
                        Log.d(Constants.control, "Benchmark requested. Waiting for stream to end...");

                        pendingBenchmark = true;
                    }
                }
                else {
                    if (curRx < threshold || curTx < threshold) {
                        if (timeGuard < new AppSettings(getContext()).getTimeGuard()) {
                            timeGuard++;
                        }
                        else {
                            if(benchmark.getStatus() == AsyncTask.Status.PENDING) {
                                benchmark = new Benchmark(getContext());

                                Log.d(Constants.control, "Starting benchmark...");
                                benchmark.execute(new String[]{Constants.bechmarkURL});

                            }
                            else if (benchmark.getStatus() == AsyncTask.Status.FINISHED) {
                                benchmarksMade++;
                                benchmark = new Benchmark(getContext());

                                pendingBenchmark = false;
                                timeGuard = 0;
                            }
                        }
                    }
                    else {
                        timeGuard = 0;
                    }
                }

                totalRx += curRx;
                totalTx += curTx;

                updateInfo();
            }

            public void setContext(Context context) {
                this.context = context;
            }

            public long getTotalRx() {
                return totalRx;
            }

            public void setTotalRx(long totalRx) {
                this.totalRx = totalRx;
            }

            public long getTotalTx() {
                return totalTx;
            }

            public void setTotalTx(long totalTx) {
                this.totalTx = totalTx;
            }

            public long getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }

            public int getBenchmarksMade() {
                return benchmarksMade;
            }

            public void setBenchmarksMade(int benchmarksMade) {
                this.benchmarksMade = benchmarksMade;
            }

            public long getCurRx() {
                return curRx;
            }

            public long getCurTx() {
                return curTx;
            }
        }

    }
}
