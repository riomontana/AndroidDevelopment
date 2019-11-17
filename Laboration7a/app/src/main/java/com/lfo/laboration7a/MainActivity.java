package com.lfo.laboration7a;



import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InnerClass().execute();
            }
        });
    }


    private class InnerClass extends AsyncTask <Integer,Void,Void> {
        private int[] colors = {Color.BLUE,Color.CYAN,Color.DKGRAY, Color.GRAY,
                Color.GREEN, Color.MAGENTA, Color.RED,Color.YELLOW, Color.rgb(123,45,200)};
        int counter = 0;

        @Override
        protected Void doInBackground(Integer... params) {
            for(int i = 0 ; i < colors.length; i++) {
                try {
                    Log.d("thread", "running");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter++;
                publishProgress();
            }
            counter = 0;
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... aVoid) {
            tv1.setBackgroundColor(colors[counter]);
            tv2.setBackgroundColor(colors[counter]);
        }

    }

}
