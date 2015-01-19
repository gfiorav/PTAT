package com.imdea.fioravantti.guido.ecousin_ptat.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.imdea.fioravantti.guido.ecousin_ptat.Constants;
import com.imdea.fioravantti.guido.ecousin_ptat.R;

/**
 * Created by guido on 12/28/14.
 */
public class Notifier {
    Context context;
    NotificationManager nm;

    Class goToClass;

    public Notifier (Context context, Class clss) {
        setContext(context);
        setGoToClass(clss);

        nm = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void showRunning () {
        Notification.Builder b = new Notification.Builder(getContext());

        Resources rsrc = getContext().getResources();

        b.setContentTitle(rsrc.getString(R.string.txtNotTitle));
        b.setContentText(rsrc.getString(R.string.txtNotBody));
        b.setTicker(rsrc.getString(R.string.txtNotTicker));
        b.setSmallIcon(R.drawable.ic_launcher);
        b.setOngoing(true);

        Intent goToIntent = new Intent(context, getGoToClass());
        goToIntent.setAction(Intent.ACTION_MAIN);
        goToIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent pi =
                PendingIntent.getActivity(
                        context,
                        0,
                        goToIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        b.setContentIntent(pi);

        nm.notify(Constants.runningNotID, b.build());


    }

    public void hideRunning () {
        nm.cancel(Constants.runningNotID);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Class getGoToClass() {
        return goToClass;
    }

    public void setGoToClass(Class goToClass) {
        this.goToClass = goToClass;
    }
}
