package com.example.madlibs;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;

public class UserInputActivity extends AppCompatActivity {

    Story story;
    InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent selectedStory = getIntent();
        String retrievedSelected = selectedStory.getStringExtra("selected");

        // if orientation is changed, is already made

        if (retrievedSelected.equals("Clothes")){
            is = getResources().openRawResource(R.raw.clothes);
        }
        else if (retrievedSelected.equals("Dance")){
            is = getResources().openRawResource(R.raw.dance);
        }
        else if (retrievedSelected.equals("Simple")){
            is = getResources().openRawResource(R.raw.simple);
        }
        else if (retrievedSelected.equals("University")){
            is = getResources().openRawResource(R.raw.university);
        }
        else if (retrievedSelected.equals("Tarzan")) {
            is = getResources().openRawResource(R.raw.tarzan);
        }
        // default, just in case
        else {
            is = getResources().openRawResource(R.raw.simple);
        }

        story = new Story(is);

        // initiate all view items used in user input process
        TextView wordsLeft = findViewById(R.id.wordsLeft);
        TextView wordType = findViewById(R.id.wordType);
        TextInputEditText wordInput = findViewById(R.id.wordInput);
        Button makeText = findViewById(R.id.okButton);

        // set beginning values
        int left = story.getPlaceholderRemainingCount();
        wordsLeft.setText(left + " word(s) left");
        wordType.setText("please fill in a " + story.getNextPlaceholder());


    }


    public void okClicked(View v){

        // get string from user input
        TextInputEditText wordInput = findViewById(R.id.wordInput);
        String inputText = wordInput.getText().toString();
        Log.d("word input", inputText);

        // check for invalid user input
        if (inputText.equals("")){
            // give feedback to user
            wordInput.setHint("Please fill in a word");
            int red = Color.parseColor("#B71C1C");
            wordInput.setHintTextColor(red);
        }
        else {
            // parse user input to story object, add bold html tags
            story.fillInPlaceholder("<b>" + inputText + "</b>");

            // give user information about the process
            TextView wordsLeft = findViewById(R.id.wordsLeft);
            int left = story.getPlaceholderRemainingCount();
            wordsLeft.setText(left + " word(s) left");
            TextView wordType = findViewById(R.id.wordType);
            wordType.setText("please fill in a " + story.getNextPlaceholder());
            wordInput.setText("");

            // check if all words have been filled in
            if (story.isFilledIn()) {
                // if so, we are ready to show the full text in the next activity
                Intent intent = new Intent(UserInputActivity.this, TextInputActivity.class);
                // Story outputStory = story;
                intent.putExtra("story", story);
                startActivity(intent);
            }
        }
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("story", story);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        // restore story object
        story = (Story) savedInstanceState.getSerializable("story");

        // show story creation process to user
        TextView wordsLeft = findViewById(R.id.wordsLeft);
        int left = story.getPlaceholderRemainingCount();
        wordsLeft.setText(left + " word(s) left");
        TextView wordType = findViewById(R.id.wordType);
        String type = story.getNextPlaceholder();
        wordType.setText("please fill in a " + type);
    }
}