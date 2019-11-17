package com.lfo.laboration7b;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MainActivity activity = this;
    private TextView tv1;
    private TextView tv2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onResume() {
        super.onResume();
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InnerClass().run();
            }
            class InnerClass extends Thread {
                private int[] colors = {Color.BLUE,Color.CYAN,Color.DKGRAY, Color.GRAY,
                        Color.GREEN, Color.MAGENTA, Color.RED,Color.YELLOW, Color.rgb(123,45,200)};
                public void run() {
//                    Log.d("thread", (activity==null)?"null":"not null");
                    for(int color : colors) {
                        try {
                            activity.runOnUiThread(new SetColor(color));
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                class SetColor implements Runnable {
                    private int color;
                    public SetColor(int color) {
                        this.color = color;
                    }
                    @Override
                    public void run() {
                        tv1.setBackgroundColor(color);
                        tv2.setBackgroundColor(color);
                    }
                }
            }
        });
    }


}