package com.evgenyvyaz.secondyandexchallenge.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.evgenyvyaz.secondyandexchallenge.App;
import com.evgenyvyaz.secondyandexchallenge.util.ConverterUtil;
import com.evgenyvyaz.secondyandexchallenge.R;
import com.evgenyvyaz.secondyandexchallenge.util.Util;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity {


    public static final String RADIO_LINK = "RADIO_LINK";
    private Button startBTN;
    private Button pauseBTN;
    private TextView progressTV;
    private String proxyUrl;
    private String radioUrl ;
    private Button cacheClearBTN;
    private HttpProxyCacheServer proxy;
    private MediaPlayer mediaPlayer;


    com.afollestad.materialdialogs.MaterialDialog dialog;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        startBTN = (Button) findViewById(R.id.startBTN);
        pauseBTN = (Button) findViewById(R.id.pauseBTN);
        cacheClearBTN = (Button) findViewById(R.id.cacheClearBTN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableHomeUpBack();
        radioUrl = getIntent().getStringExtra(RADIO_LINK);

        View content = getLayoutInflater().inflate(R.layout.layout_date_dialog, null);
        progressTV = (TextView) content.findViewById(R.id.progressTV);
        com.afollestad.materialdialogs.MaterialDialog.Builder builder = new com.afollestad.materialdialogs.MaterialDialog.Builder(PlayActivity.this)
                .canceledOnTouchOutside(false)
                .customView(content, false);
        progressTV.setText("Загрузка...");

        dialog = builder.build();
        updateProxy();

        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        pauseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();

            }
        });
        cacheClearBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                proxy.shutdown();
                cleanChache();
                progressTV.setText("Удаляется кэш, пожалуйста, подождите...");
                updateProxy();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanChache();
        mediaPlayer.pause();
    }

    public void updateProxy() {
        dialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                proxy = App.getProxy(PlayActivity.this);
                proxyUrl = proxy.getProxyUrl(radioUrl);
                try {

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(proxyUrl);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            dialog.dismiss();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    protected void enableHomeUpBack(){
        ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.show();
        ImageView icon = (ImageView)findViewById(android.R.id.home);
        if(icon != null){
            icon.setPadding(Math.round(ConverterUtil.convertDpToPixel(8, getApplicationContext())), 0, 0, 0);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                proxy.shutdown();
                cleanChache();
                mediaPlayer.pause();
                finish();
                break;
        }

        return true;
    }
    private void cleanChache(){
        try {
            Util.cleanDirectory(getCacheDir());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

  }
