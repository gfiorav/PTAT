package com.imdea.fioravantti.guido.ecousin_ptat.util;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.imdea.fioravantti.guido.ecousin_ptat.Constants;

public class Animator {

    Context context;

    public Animator (Context context) {
        setContext(context);
    }

    public void changeBackgroundColor (int from, int to, final View v) {
        Integer colorFrom = getContext().getResources().getColor(from);
        Integer colorTo = getContext().getResources().getColor(to);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                v.setBackgroundColor((Integer)animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        colorAnimation.setDuration(Constants.backTransDur);
    }

    public void changeTextColor (int from, int to, final View v) {
        final TextView tv = (TextView) v;

        Integer colorFrom = getContext().getResources().getColor(from);
        Integer colorTo = getContext().getResources().getColor(to);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                tv.setTextColor((Integer) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        colorAnimation.setDuration(Constants.backTransDur);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
