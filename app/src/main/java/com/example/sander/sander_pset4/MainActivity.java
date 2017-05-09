package com.example.sander.sander_pset4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvMain;
    EditText etMain;
    Button bMain;
    DBManager db;

    ArrayList<Todo> todoList;
    ArrayList<String> todoStrList;

    TodoAdapter adapter;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views
        lvMain = (ListView) findViewById(R.id.mainListView);
        etMain = (EditText) findViewById(R.id.mainEditText);
        bMain = (Button) findViewById(R.id.mainButton);

        // create dbmanager
        db = new DBManager(this);
        db.open();

        // make the todolist
        makeList();
    }

    private void makeList() {
        Log.d("log", "Main.onCreate.makeList");

        // fill array with todo items from database
        todoList = db.read();

        // fill string array list
        todoStrList = new ArrayList<>();
        for (int i = 0; i < todoList.size(); i++) {
            todoStrList.add(i, todoList.get(i).getText());
            Log.d("log", "todoList.get(" + i + ").getText():" + todoList.get(i).getText());
        }

        // initialize adapter
        adapter = new TodoAdapter(this, todoList);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, todoList);

        lvMain.setAdapter(arrayAdapter);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

//        // set listeners for listitems
//        lvMain.setOnItemClickListener(new simpleListener());

        // testing: print content of todolist
        for (Todo todo : db.read()) {
            Log.d("log", "ID: " + todo.get_id());
            Log.d("log", "Text: " + todo.getText());
            Log.d("log", "Checked: " + todo.getChecked());
        }

        Log.d("log", "Main.onCreate.makeList: success");
    }

    private void reverseCheckState(Todo todo, TextView tvTodo) {
        if (todo.getChecked() == 0) {
            // check
            todo.setChecked(1);
        }
        else {
            // uncheck
            todo.setChecked(0);
        }

        // update db
        db.update(todo);
    }

    private class simpleListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("log", "Main.simpleListener: start");

            // get textview of todo
            TextView tvTodo = (TextView) view.findViewById(R.id.listCheckedTV);

            // reverse checkstate of item in database
            reverseCheckState(todoList.get(position), tvTodo);

            // update view
            lvMain.setItemChecked(position, (todoList.get(position).getChecked() != 0));

            Log.d("log", "Main.simpleListener: success");
        }
    }
}

