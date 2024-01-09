package com.myfirstapp.mentdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView level = findViewById(R.id.level);
        TextView ageCategory = findViewById(R.id.age_id);
        Button btnRefer = findViewById(R.id.btn_refer);

        double mentalScore = getIntent().getDoubleExtra("score",0);
        String age = getIntent().getStringExtra("age");

        ageCategory.setText(age);

        if (age == "Child"){
            btnRefer.setText("Guardian Alerts");
        }



        if (mentalScore >70){
            level.setText("bad");
            level.setTextColor(Color.RED);
        }else if(mentalScore < 71 && mentalScore > 40){
            level.setText("Medium");
            level.setTextColor(Color.BLACK);
        }else{
            level.setText("GOOD");
            level.setTextColor(Color.GREEN);
        }

    }

    public void goToHome(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void ReferDoctor(View view) {
        Intent intent = new Intent(getApplicationContext(),DocAllActivity.class);
        startActivity(intent);
        finish();
    }
}