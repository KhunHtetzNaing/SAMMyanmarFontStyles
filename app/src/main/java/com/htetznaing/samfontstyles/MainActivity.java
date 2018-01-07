package com.htetznaing.samfontstyles;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String path = "/sdcard/Android/data/com.htetznaing.samfontstyles/";
    FuckYou fuck = new FuckYou();
    ListView listView;
    AdRequest adRequest;
    AdView banner;
    InterstitialAd interstitialAd;
    ProgressDialog progressBar,progressDialog;
    int images [] = {R.drawable.dancing,R.drawable.darcy,R.drawable.eainmat,R.drawable.ghost,R.drawable.heart,R.drawable.htinshu,R.drawable.izawgyi,R.drawable.jojar,R.drawable.kason,R.drawable.khninsi,R.drawable.love,R.drawable.matrix,R.drawable.nattaw,R.drawable.nayon,R.drawable.ngaye,R.drawable.notosans,R.drawable.notosansmix,R.drawable.ooredoo,R.drawable.padauk,R.drawable.paoh,R.drawable.pyatho,R.drawable.sagar,R.drawable.szg,R.drawable.tabaung,R.drawable.tabodwe,R.drawable.tabodwemix,R.drawable.tagu,R.drawable.tdg,R.drawable.tran,R.drawable.tsm,R.drawable.ttl,R.drawable.ubuntu,R.drawable.warso,R.drawable.wg,R.drawable.yoeyar,R.drawable.yuzana,R.drawable.zg2,R.drawable.zo,R.drawable.zy};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Please Wait!");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Extracting data....");
        progressBar.setCancelable(false);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Loading data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        new StartWork().execute();
        listView = (ListView) findViewById(R.id.listView);
        ListAdapter adapter = new ListAdapter(images,MainActivity.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showAD();
                goWork(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareText = "Samsung beautiful myanmar font styles.\n" +
                        "Support all samsung any model and version(Perfect above android 5.0 to 8.0)\n" +
                        "Download free at google play store : https://play.google.com/store/apps/details?id="+getPackageName();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareText);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent,"Samsung Myanmar Font Styles"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showAD();
            startActivity(new Intent(MainActivity.this,About.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        progressDialog.dismiss();
        super.onPause();
    }

    public void goWork(int position){
        boolean b = checkPermissions();
        if (b==true) {
            final String apk = fuck.getFontName().get(position);
            File f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }

            File n = new File(path + apk);
            if (n.exists()) {
                progressDialog.show();
                Intent intent = new Intent(MainActivity.this,Fucker.class);
                intent.putExtra("apk",apk);
                intent.putExtra("images",position);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please try again :)", Toast.LENGTH_SHORT).show();
                b = false;
                b = fuck.assets2SD(MainActivity.this, "icon.png", path, "fuck.zip");
                if (b == true) {
                    b = false;
                    fuck.unZip(path + "fuck.zip", "<@Fun4Mm/>", path);
                } else {
                    Toast.makeText(this, "Can't Copy Font File :(", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean checkPermissions() {
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        final List<String> listPermissionsNeeded = new ArrayList<>();
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Need to Allow Write Storage Permission!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(MainActivity.this, listPermissionsNeeded.toArray
                            (new String[listPermissionsNeeded.size()]), 5217);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            Log.d("TAG","Permission"+"\n"+String.valueOf(false));
            return false;
        }
        Log.d("Permission","Permission"+"\n"+String.valueOf(true));
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 5217: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    checkPermissions();
                    Toast.makeText(this, "You need to Allow Write Storage Permission!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    class StartWork extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            boolean b = checkPermissions();
            if (b==true) {
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdir();
                }

                for (int i = 0; i < fuck.getFontName().size(); i++) {
                    String name = path+fuck.getFontName().get(i);
                    File n = new File(name);
                    if (!n.exists()) {
                        if (b == true) {
                            b = false;
                            b = fuck.assets2SD(MainActivity.this, "icon.png", path, "fuck.zip");
                            if (b == true) {
                                b = false;
                                fuck.unZip(path + "fuck.zip", "<@Fun4Mm/>", path);
                                fuck.deleteFile(path+"fuck.zip");
                            } else {
                                Toast.makeText(MainActivity.this, "Can't Copy Font File :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    File nnnn = new File(name.replace(".apk",".ttf"));
                    if (!nnnn.exists()){
                        fuck.htetzUnzip(name,name.replace(".apk",""));
                        fuck.copy(new File(name.replace(".apk","")+"/assets/fonts/").listFiles()[0].toString(),name.replace(".apk","")+".ttf");
                        fuck.deleteDirectory(name.replace(".apk",""));
                    }

                    File nnn = new File(name.replace(".apk",".html"));
                    if (!nnn.exists()){
                        String lol = "<html>\n" +
                                "\t<head>\n" +
                                "\t<title>Preview</title>\n" +
                                "\t</head>\n" +
                                "<style type=\"text/css\">\n" +
                                "@font-face {\n" +
                                "    font-family: \"Myanmar\";\n" +
                                "    src: url('mm.ttf') format(\"truetype\");\n" +
                                "}\n" +
                                "* { \n" +
                                "    font-family: \"Myanmar\", Verdana, Tahoma;\n" +
                                "}\n" +
                                "</style>\n" +
                                "\t<body>\n" +
                                "\t<center>\n" +
                                "\t<p>သီဟိုဠ္မွ ဉာဏ္ႀကီးရွင္သည္<br />\n" +
                                "အာယုဝဍ္ဎနေဆးၫႊန္းစာကို<br />\n" +
                                "ဇလြန္ေဈးေဘးဗာဒံပင္ထက္<br />\n" +
                                "အဓိ႒ာန္လ်က္ ဂဃနဏဖတ္ခဲ့သည္။</p>\n" +
                                "\n" +
                                "<p>the quick brown fox<br />\n" +
                                "jumped over the lazy dog</p>\n" +
                                "\n" +
                                "<p>THE QUICK BROWN FOX<br />\n" +
                                "JUMPED OVER THE LAZY DOG</p>\n" +
                                "</center>\n" +
                                "\t</body>\n" +
                                "\t</html>";
                        lol = lol.replace("mm.ttf",name.replace(".apk",".ttf"));
                        fuck.writeTextFile(name.replace(".apk",".html"),lol);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.dismiss();
            Toast.makeText(MainActivity.this, "Done :)", Toast.LENGTH_SHORT).show();
            Log.d("HtetzWork","Done");
        }
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attention!");
        builder.setMessage("Do you want to exit ?");
        builder.setIcon(R.drawable.icon);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showAD();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showAD();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
