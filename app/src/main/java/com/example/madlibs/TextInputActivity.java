package com.example.madlibs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class TextInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textinput);

        // get user's created story and show it
        Intent intent = getIntent();
        Story retrievedStory = (Story) intent.getSerializableExtra("story");
        TextView outputText = findViewById(R.id.outputText);
        // make user inputted text appear bold
        // (sources used: https://stackoverflow.com/questions/46598639/multiple-lines-of-text-with-bold-headings-in-android)
        outputText.setText(Html.fromHtml(retrievedStory.toString()));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void returnClicked(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
