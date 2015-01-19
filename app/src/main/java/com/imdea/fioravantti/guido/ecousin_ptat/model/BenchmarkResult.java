package com.imdea.fioravantti.guido.ecousin_ptat.model;

public class BenchmarkResult {
    long timestamp;
    long rate;

    public BenchmarkResult(long timestamp, long rate) {
        setTimestamp(timestamp);
        setRate(rate);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }
}
