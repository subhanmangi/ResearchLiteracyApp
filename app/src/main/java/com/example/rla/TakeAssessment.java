package com.example.rla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TakeAssessment extends AppCompatActivity {

    List<QuestionModel> listOfQuestions;
    QuestionModel q;

    DatabaseReference databaseReference;


    TextView question, optiona, optionb, optionc, optiond;
    CardView cardqa, cardqb, cardqc, cardqd;

    LinearLayout nextButton, learnButton;

    int correctCount = 0;
    int wrongCount = 0;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_assessment);

        question = findViewById(R.id.card_question);
        optiona = findViewById(R.id.card_optiona);
        optionb = findViewById(R.id.card_optionb);
        optionc = findViewById(R.id.card_optionc);
        optiond = findViewById(R.id.card_optiond);

        cardqa = findViewById(R.id.cardoa);
        cardqb = findViewById(R.id.cardob);
        cardqc = findViewById(R.id.cardoc);
        cardqd = findViewById(R.id.cardod);

        nextButton = findViewById(R.id.nextButton);
        learnButton = findViewById(R.id.learnButton);



        listOfQuestions = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Questions");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot  : snapshot.getChildren()){
                    QuestionModel questionModel = dataSnapshot.getValue(QuestionModel.class);
                    listOfQuestions.add(questionModel);
                }
                index = 0;
                Collections.shuffle(listOfQuestions);
                   q= listOfQuestions.get(index);
                setAllData();

                nextButton.setClickable(false);
                //Toast.makeText(TakeAssessment.this, ""+listOfQuestions.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }

    public void setAllData(){
        question.setText(q.getQuestion());
        optiona.setText(q.getoA());
        optionb.setText(q.getoB());
        optionc.setText(q.getoC());
        optiond.setText(q.getoD());




    }

    public void correct(CardView card){

        card.setCardBackgroundColor(getResources().getColor(R.color.green));


        nextButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                enableButtons();
                correctCount++;
                index++;
                q = listOfQuestions.get(index);
                setAllData();
                resetColors();
            }
        });
    }

    public void wrong(CardView card){

        card.setCardBackgroundColor(getResources().getColor(R.color.red));

        nextButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                enableButtons();
                learnButton.setVisibility(View.INVISIBLE);
                wrongCount++;
                if(index<listOfQuestions.size()-1){
                    index++;
                    q = listOfQuestions.get(index);
                    setAllData();
                    resetColors();

                }
                else{
                    gameWon();
                }
            }
        });

        learnButton.setVisibility(View.VISIBLE);

        learnButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW",
                        Uri.parse(q.getLink()));
                startActivity(intent);
            }
        });



    }

    private void gameWon() {

        Intent intent = new Intent(TakeAssessment.this, ResultActivity.class);
        intent.putExtra("correct", correctCount);
        intent.putExtra("wrong", wrongCount);
        startActivity(intent);
    }

    public void enableButtons(){
        cardqa.setClickable(true);
        cardqb.setClickable(true);
        cardqc.setClickable(true);
        cardqd.setClickable(true);
    }

    public void disableButtons(){
        cardqa.setClickable(false);
        cardqb.setClickable(false);
        cardqc.setClickable(false);
        cardqd.setClickable(false);
    }

    public void resetColors(){
        cardqa.setCardBackgroundColor(getResources().getColor(R.color.cardColor));
        cardqb.setCardBackgroundColor(getResources().getColor(R.color.cardColor));
        cardqc.setCardBackgroundColor(getResources().getColor(R.color.cardColor));
        cardqd.setCardBackgroundColor(getResources().getColor(R.color.cardColor));

    }

    public void optionaClicked(View v){
        disableButtons();
        nextButton.setClickable(true);
        if(q.getoA().equals(q.getAns())){
            cardqa.setCardBackgroundColor(getResources().getColor(R.color.green));

            if(index<listOfQuestions.size()-1){
                correct(cardqa);
            }
            else{
                gameWon();
            }
        }
        else{
            wrong(cardqa);
        }
    }

    public void optionbClicked(View v){
        disableButtons();
        nextButton.setClickable(true);
        if(q.getoB().equals(q.getAns())){
            cardqb.setCardBackgroundColor(getResources().getColor(R.color.green));

            if(index<listOfQuestions.size()-1){
                correct(cardqb);
            }
            else{
                gameWon();
            }
        }
        else{
            wrong(cardqb);
        }
    }

    public void optioncClicked(View v){
        disableButtons();
        nextButton.setClickable(true);
        if(q.getoC().equals(q.getAns())){
            cardqc.setCardBackgroundColor(getResources().getColor(R.color.green));

            if(index<listOfQuestions.size()-1){
                correct(cardqc);
            }
            else{
                gameWon();
            }
        }
        else{
            wrong(cardqc);
        }
    }

    public void optiondClicked(View v){
        disableButtons();
        nextButton.setClickable(true);
        if(q.getoD().equals(q.getAns())){
            cardqd.setCardBackgroundColor(getResources().getColor(R.color.green));

            if(index<listOfQuestions.size()-1){
                correct(cardqd);
            }
            else{
                gameWon();
            }
        }
        else{
            wrong(cardqd);
        }
    }


}