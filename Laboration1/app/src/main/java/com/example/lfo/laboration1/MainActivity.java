package com.example.lfo.laboration1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnPrevious;
    private Button btnNext;
    private TextView tvWhatToDo;
    private TextView tvContent;
    private String whatToDo;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        registerListeners();
        controller = new Controller(this);
    }

    private void registerListeners() {
        btnNext.setOnClickListener(new NextListener());
    }

    private void initializeComponents() {
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnNext = (Button) findViewById(R.id.btnNext);
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvWhatToDo = (TextView) findViewById(R.id.tvWhatToDo);
    }


    public void previousInstruction(View view) {
        controller.previousClick();
    }

    public void setWhatToDo(String whatToDo) {
        tvWhatToDo.setText(whatToDo);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    private class NextListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
          controller.nextClick();
        }
    }
}
