package com.htetznaing.samfontstyles;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.ArrayList;

import mm.technomation.mmtext.mmtext;

public class Fucker extends AppCompatActivity {
    String apk = "tagu.apk";
    String path = "/sdcard/Android/data/com.htetznaing.samfontstyles/";
    WebView webView;
    FuckYou pff = new FuckYou();
    TextView textView;
    AdRequest adRequest;
    AdView banner;
    InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fucker);

        adRequest = new AdRequest.Builder().build();
        banner = (AdView) findViewById(R.id.adView);
        banner.loadAd(adRequest);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-1325188641119577/2922074837");
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAD();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                loadAD();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                loadAD();
            }
        });

        textView = (TextView) findViewById(R.id.textView);
        mmtext.prepareView(Fucker.this,textView,mmtext.TEXT_UNICODE,true,true);

        apk = getIntent().getStringExtra("apk");
        setTitle(getTitle2());
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:////sdcard/Android/data/com.htetznaing.samfontstyles/"+apk.replace(".apk",".html"));
    }

    public void Install(){
        boolean check = appInstalled("com.monotype.android.font.samsungsans");

        if(check==true) {
            showAD();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(path + apk)), "application/vnd.android.package-archive");
            startActivity(intent);
        }else{
            pff.assets2SD(Fucker.this,"cover.png",path,"sans.apk");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Attention!");
            builder.setMessage("You need to Install Samsung Sans!");
            builder.setPositiveButton("Install", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    showAD();
                    File fi = new File(path+"sans.apk");
                    if (fi.exists()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(path + "sans.apk")), "application/vnd.android.package-archive");
                        startActivity(intent);
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    showAD();
                }
            });
            AlertDialog d = builder.create();
            d.show();
        }
    }

    private boolean appInstalled(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    public void changeFont(View view) {
        int currentapiVersion = Build.VERSION.SDK_INT;

        if (currentapiVersion >= Build.VERSION_CODES.M)
        {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.FontPreviewTablet");
                intent.setComponent(componentName);
                startActivity(intent);
            }catch (Exception e){
                showAD();
                startActivityForResult(new Intent(Settings.ACTION_DISPLAY_SETTINGS),0);
            }
        }
        else
        {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.flipfont.FontListProgressActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }catch (Exception e){
                showAD();
                startActivityForResult(new Intent(Settings.ACTION_DISPLAY_SETTINGS),0);
            }
        }
    }

    public void installFont(View view) {
        Install();
    }

    public String getTitle2(){
        String lo2 = "Myanmar";
        FuckYou fuckYou = new FuckYou();
        ArrayList<String> ll = new ArrayList<>();
        ArrayList<String> lll = new ArrayList<>();
        ll = fuckYou.getFontName();
        lll = fuckYou.getName();

        for (int i =0;i<ll.size();i++){
            if (apk.equals(ll.get(i))){
                lo2 = lll.get(i);
            }
        }

        return lo2;
    }

    public void loadAD(){
        if (!interstitialAd.isLoaded()){
            interstitialAd.loadAd(adRequest);
        }
    }

    public void showAD(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }else{
            interstitialAd.loadAd(adRequest);
        }
    }
}
