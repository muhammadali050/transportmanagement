package com.climesoftt.transportmanagement.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by AtoZ on 3/19/2018.
 */

public class PDialog {
    public Context cot;
    private ProgressDialog pd;

    public PDialog(Context c)
    {
        this.cot = c;
        pd = new ProgressDialog(cot);
    }
    public PDialog message(String msg){
        pd.setMessage(msg);
        return this;
    }
    public PDialog show(){
        pd.show();
        return this;
    }

    public void hide(){
        pd.dismiss();
    }
}
