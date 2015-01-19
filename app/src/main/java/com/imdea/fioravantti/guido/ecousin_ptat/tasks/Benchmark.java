package com.imdea.fioravantti.guido.ecousin_ptat.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.imdea.fioravantti.guido.ecousin_ptat.Constants;
import com.imdea.fioravantti.guido.ecousin_ptat.model.BenchmarkResult;
import com.imdea.fioravantti.guido.ecousin_ptat.model.DatabaseAPI;
import com.imdea.fioravantti.guido.ecousin_ptat.util.AppSettings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class Benchmark extends AsyncTask<String, Integer, BenchmarkResult> {
    Context context;

    long bins [];
    int binNum;

    long timestamp;

    BenchmarkResult result;

    int SSD;
    int total;

    String link;
    int id;

    boolean isAchieved;

    public Benchmark (Context context) {
        setContext(context);

        AppSettings as = new AppSettings(getContext());

        setTotal(0);
        setAchieved(false);
        setBinNum(as.getBinNum());
        setBins(new long [getBinNum()]);
        setSSD(as.getSSD());
        setId((int) (System.currentTimeMillis() / 1000));
    }

    @Override
    protected BenchmarkResult doInBackground(String... urls) {
        setLink(urls[0]);

        InputStream is;
        OutputStream os;
        HttpURLConnection conn;

        try {
            URL url = new URL(getLink());

            conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            int responseCode;

            responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                Log.wtf(
                        Constants.control,
                        "HTTP conection in thread " + getId() + " was rejected"
                );
            }

            is = conn.getInputStream();
            os = new FileOutputStream(Constants.dumpPath + Constants.dlPrefix + getId());

            int count = 0;
            int slowStartPtr = 0;

            byte [] recv = new byte[Constants.recvBuffer];



            setTimestamp(System.currentTimeMillis() / 1000);

            new Timer().scheduleAtFixedRate(
                    new BinCounter(),
                    Constants.now,
                    Constants.counterGranularity
            );

            while ((count = is.read(recv)) != Constants.recvEnd) {
                if(isAchieved()) {
                    conn.disconnect();
                    Log.d(Constants.control, "Benchmark achieved.");
                }
                else {
                    os.write(recv, 0, count);

                    total += (count * Constants.bitsInByte);

                }
            }


        } catch (MalformedURLException e) {
            Log.wtf(Constants.control, "URL in thread " + getId() + " is malformed.");
            Log.wtf(Constants.control, "URL was " + getLink());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getResult();
    }

    public class BinCounter extends TimerTask {
        int lastTotal = 0;
        int binPtr = 0;

        @Override
        public void run() {
            if(binPtr < getBins().length) {
                int tot = getTotal();

                getBins()[binPtr] = tot - lastTotal;

                lastTotal = tot;

                binPtr++;
            }
            else {
                long cumulative = 0;

                int b;
                for(b = getSSD(); b < getBinNum(); b++) {
                    cumulative += getBins()[b];
                }

                cumulative /= getBinNum();

                setResult(new BenchmarkResult(getTimestamp(), cumulative));

                Log.d(Constants.control, "Rate achieved is: " + (getResult().getRate() / 8192));

                setAchieved(true);

                new DatabaseAPI(getContext()).add(getResult());

                this.cancel();
            }
        }
    }

    public boolean isAchieved() {
        return isAchieved;
    }

    public void setAchieved(boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

    public int getBinNum() {
        return binNum;
    }

    public void setBinNum(int binNum) {
        this.binNum = binNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long[] getBins() {
        return bins;
    }

    public void setBins(long[] bins) {
        this.bins = bins;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getSSD() {
        return SSD;
    }

    public void setSSD(int SSD) {
        this.SSD = SSD;
    }

    public BenchmarkResult getResult() {
        return this.result;
    }

    public void setResult(BenchmarkResult result) {
        this.result = result;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}