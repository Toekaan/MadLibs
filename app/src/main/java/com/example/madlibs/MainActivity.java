package com.example.madlibs;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.Spinner;
        import android.widget.Toast;
        import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openscreen);
        intent = new Intent(MainActivity.this, UserInputActivity.class);

        // drop down menu lets user select different stories
        Spinner storySpinner = (Spinner) findViewById(R.id.storySpinner);
        ArrayAdapter storyAdapter = ArrayAdapter.createFromResource(this, R.array.story_array, android.R.layout.simple_spinner_item);
        storyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storySpinner.setAdapter(storyAdapter);
        storySpinner.setOnItemSelectedListener(new SpinnerSelect());
    }

    public class SpinnerSelect extends Activity implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selected = parent.getItemAtPosition(position).toString();
            Log.d("selected: ", selected);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            selected = null;
        }
    }

    /** gives string of chosen story within intent to next activity */
    public void checkStart(View v){
        // probably won't be used since spinner already selects "simple" automatically
        if (selected == null){
            Toast.makeText(this, "Please select one of the stories!", Toast.LENGTH_SHORT).show();
        }

        // check user input and choose according story
        else {
            intent.putExtra("selected", selected);
            startActivity(intent);
        }
    }
}
