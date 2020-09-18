package com.example.adminapp;

import android.util.Log;

public class replacer {

    static String _email;

    public static String fireproof_string(String input)
    {
        _email=input;
        _email=_email.replace(".","");
        _email=_email.replace("#","");
        _email=_email.replace("$","");
        _email=_email.replace("[","");
        _email=_email.replace("}","");


        Log.i("btag",_email);
        return  _email;
    }



}
