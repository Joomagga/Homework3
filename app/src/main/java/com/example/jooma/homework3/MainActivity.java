package com.example.jooma.homework3;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* Instance Local variables */
    private WebView browser;
    private String num = "";
    private String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /* Initializing Methods */
    private void init()
    {
        // initialize browser
        browser = (WebView)findViewById(R.id.webview);
        browser.setWebViewClient(new WebViewClient());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new jsi(this), "Android");
        browser.loadUrl("file:///android_asset/WebCalculator.html");
    }

    /* Customized Javascript Interface */
    private class jsi
    {
        /* Instance Local Variables */
        private Context context;

        /* Constructor Methods */
        public jsi(Context c)
        {
            context = c;
        }

        /* Javascript Methods */
        @JavascriptInterface
        public void showToast(String msg)
        {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void buttonPressed(int a)
        {
            num = num.concat(Integer.toString(a));
        }

        @JavascriptInterface
        public void sendMsg(String newMsg)
        {
            msg = newMsg;
            sendSMS(num, msg);
        }

        @JavascriptInterface
        public String getNum()
        {
            return num;
        }

        @JavascriptInterface
        public String getMsg()
        {
            return msg;
        }
    }

    private void sendSMS(String phoneNumber, String message)
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }
}
