package com.fengpanhome.weatherapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.fengpanhome.weatherapp.R;

public class MainActivity extends Activity
{

    private ProgressBar progressBar;
    private int progressStatus = 0;
    final private Handler handler = new Handler();

    private void initializeView()
    {
        progressBar = (ProgressBar)findViewById(R.id.progress_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
        progressStatus = 0;
        new Thread(new Runnable() {
            public void run()
            {
                while (progressStatus < 100)
                {
                    progressStatus += 1;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);

                            try
                            {
                            }
                            catch (Exception e)
                            {
                               e.printStackTrace();
                            }

                        }
                    });
                }
            }
        }).start();
    }
}
