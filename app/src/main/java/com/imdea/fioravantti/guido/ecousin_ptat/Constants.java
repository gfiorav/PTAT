package com.imdea.fioravantti.guido.ecousin_ptat;

/**
 * Created by guido on 12/27/14.
 */
public class Constants {

    /* String TAGs */
    public static final String control  = "[ CONTROL ]";
    public static final String error    = "[ ERROR ]";
    public static final String appName  = "eCousin PTAT";
    public static final String dialogueConfirm = "CONFIRM";

    /* TCPDump related */
    public static final String tcpDumpPath  = "/system/xbin/";
    public static final String dumpPath     = "/sdcard/PTAT/";

    /* Benchmark related */
    public static final String dlPrefix = ".bnchmrk-";
    public static final int recvBuffer  = 1024;
    public static final int bitsInByte  = 8;
    public static final int recvEnd     = -1;
    public static final String bechmarkURL = "" +
            "http://speedtest.mad.adamo.es/speedtest/random4000x4000.jpg";

    /* Preferences related */
    public static final int defaultThresholdKb          = 512;
    public static final boolean defaultTCPDumpEnabled   = true;
    public static final int defaultBytesPerPack         = 80;
    public static final int defaultMBPerCap             = 20;
    public static final boolean defaultSysEnabled       = false;
    public static final int defaultTimeGuard            = 5;
    public static final int defaultSSD                  = 2;
    public static final int defaultBinNum               = 10;
    public static final int defaultSysGran              = 20*60000;

    /* Database related */
    public static final String databaseName             = "Benchmarks.db";
    public static final int databaseVersion             = 1;
    public static final String tableName                = "benchmarks";

    /* Modes */
    public static final int notInstalled    = 0;
    public static final int stopped         = 1;
    public static final int running         = 2;

    /* Threading fields */
    public static final long counterGranularity = 1000;
    public static final long now                = 0;

    /* Systematic Download related */
    public static final String downloadLink     = "http://upload.wikimedia.org/wikipedia/commons/2/2d/Snake_River_%285mb%29.jpg";

    /* Notification related */
    public static final int runningNotID = 001;

    /* Animation related */
    public static final long backTransDur = 200;
}
