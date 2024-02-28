package com.myfirstapp.mentdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuesAnsActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView question,numQues;
    ImageView backButton;
    Button topButton,middleButton,bottomButton,nextButton;
    int questionPosition = 0;
    List<QuesModelClass> questionBank = new ArrayList<>();

    String selectedByUser = "";

    String ageCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_ans);
        mAuth = FirebaseAuth.getInstance();

        backButton = findViewById(R.id.back_id);

        numQues = findViewById(R.id.numQues);
        question = findViewById(R.id.ques_id);
        topButton = findViewById(R.id.topOption);
        middleButton = findViewById(R.id.middleOption);
        bottomButton = findViewById(R.id.bottomOption);
        nextButton = findViewById(R.id.next_Option);

        ageCategory = getIntent().getStringExtra("category");



        //getting ques data from database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mentdoc-da69c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionBank.clear();
                for (DataSnapshot dataSnapshot : snapshot.child(ageCategory).getChildren()){

                    String getQues =  dataSnapshot.child("question").getValue(String.class);
                    String getOptionA =  dataSnapshot.child("optionA").getValue(String.class);
                    String getOptionB =  dataSnapshot.child("optionB").getValue(String.class);
                    String getOptionC =  dataSnapshot.child("optionC").getValue(String.class);
                    int getPointA =  dataSnapshot.child("pointA").getValue(Integer.class);
                    int getPointB =  dataSnapshot.child("pointB").getValue(Integer.class);
                    int getPointC =  dataSnapshot.child("pointC").getValue(Integer.class);

                    QuesModelClass quesModel = new QuesModelClass(getQues,getOptionA,getOptionB,getOptionC,getPointA,getPointB,getPointC);
                    questionBank.add(quesModel);
                }
                numQues.setText((questionPosition+1)+"/"+questionBank.size());
                question.setText(questionBank.get(questionPosition).getQues());
                topButton.setText(questionBank.get(questionPosition).getOptionA());
                middleButton.setText(questionBank.get(questionPosition).getOptionB());
                bottomButton.setText(questionBank.get(questionPosition).getOptionC());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedByUser.isEmpty()){
                    selectedByUser = "A";
                    topButton.setTextColor(Color.GRAY);
                    questionBank.get(questionPosition).setSelectedByUser("A");
                }

            }
        });

        middleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedByUser.isEmpty()){
                    selectedByUser = "B";
                    middleButton.setTextColor(Color.GRAY);
                    questionBank.get(questionPosition).setSelectedByUser("B");
                }

            }
        });

        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedByUser.isEmpty()){
                    selectedByUser = "C";
                    bottomButton.setTextColor(Color.GRAY);
                    questionBank.get(questionPosition).setSelectedByUser("C");
                }

            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedByUser.isEmpty()){
                    Toast.makeText(QuesAnsActivity.this, "Select an Answer", Toast.LENGTH_SHORT).show();
                }else{
                    changeNextQues();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void changeNextQues(){
        questionPosition++;
        if ((questionPosition+1) == questionBank.size()){
            nextButton.setText("Submit");
            selectedByUser = "";
            topButton.setTextColor(Color.BLACK);
            middleButton.setTextColor(Color.BLACK);
            bottomButton.setTextColor(Color.BLACK);

            numQues.setText((questionPosition+1)+"/"+questionBank.size());
            question.setText(questionBank.get(questionPosition).getQues());
            topButton.setText(questionBank.get(questionPosition).getOptionA());
            middleButton.setText(questionBank.get(questionPosition).getOptionB());
            bottomButton.setText(questionBank.get(questionPosition).getOptionC());
        }else if (questionPosition < questionBank.size()){
            selectedByUser = "";
            topButton.setTextColor(Color.BLACK);
            middleButton.setTextColor(Color.BLACK);
            bottomButton.setTextColor(Color.BLACK);

            numQues.setText((questionPosition+1)+"/"+questionBank.size());
            question.setText(questionBank.get(questionPosition).getQues());
            topButton.setText(questionBank.get(questionPosition).getOptionA());
            middleButton.setText(questionBank.get(questionPosition).getOptionB());
            bottomButton.setText(questionBank.get(questionPosition).getOptionC());

        }else{
            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            intent.putExtra("score",getMentalScore());

            if (ageCategory.equals("QuesUnder18")){
                intent.putExtra("age","Teenager");
            }else if(ageCategory.equals("QuesAdult")){
                intent.putExtra("age","Adult");
            }else {
                intent.putExtra("age","Child");
            }

            startActivity(intent);
            finish();
        }
    }

    private double getMentalScore(){
        int score = 0;
        for (int i = 0;i<questionBank.size();i++){
            if (questionBank.get(i).getSelectedByUser().equals("A")){
                score+=questionBank.get(i).getPointA();
            } else if (questionBank.get(i).getSelectedByUser().equals("B")) {
                score+=questionBank.get(i).getPointB();
            }else{
                score+=questionBank.get(i).getPointC();
            }
        }
        return (double) (score * 100) /(3*questionBank.size());
    }

}