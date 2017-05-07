package com.example.sander.sander_pset4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView lvMain;
    EditText etMain;
    Button bMain;
    DBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views
        lvMain = (ListView) findViewById(R.id.mainListView);
        etMain = (EditText) findViewById(R.id.mainEditText);
        bMain = (Button) findViewById(R.id.mainButton);

        // set lvMain
            // get info from database
            // set text of lvMain.items to db info


    }
}
