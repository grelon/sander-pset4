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

        // set choicemode of list to multiple
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // set up list
        updateList();

        // set listeners for listitems
        lvMain.setOnItemClickListener(new simpleListener());
        lvMain.setOnItemLongClickListener(new longListener());

        // testing: print content of todolist
        for (Todo todo : db.read()) {
            Log.d("log", "ID: " + todo.get_id());
            Log.d("log", "Text: " + todo.getText());
            Log.d("log", "Checked: " + todo.getChecked());
        }

        Log.d("log", "Main.onCreate.makeList: success");
    }


    private void updateTodoStrList() {
        // fill string array list with text of todo's
        todoStrList = new ArrayList<>();
        for (int i = 0; i < todoList.size(); i++) {
            todoStrList.add(i, todoList.get(i).getText());
        }
    }

    private void reverseCheckState(Todo todo) {
        // reverse check state in todo
        if (todo.getChecked() == 0) {todo.setChecked(1);}
        else {todo.setChecked(0);}

        // update db
        db.update(todo);
    }

    public void createTodo(View view) {
        EditText editText = (EditText) findViewById(R.id.mainEditText);
        db.create(new Todo(editText.getText().toString(), 0));

        // clear textfield
        editText.getText().clear();

        // update list
        updateList();
    }

    private void updateList() {
        // get fresh data from db
        todoList = db.read();
        if (todoList.isEmpty()) {
            init();
        }
        updateTodoStrList();

        // update listview
        arrayAdapter = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_multiple_choice, todoStrList);
        lvMain.setAdapter(arrayAdapter);

        // set check state of todo items
        for (int i = 0; i < todoList.size(); i++) {
            lvMain.setItemChecked(i, (todoList.get(i).getChecked() != 0));
        }
    }

    private void init() {
        // will only run when databse is empty
        db.create(new Todo(getString(R.string.init1), 0));
        db.create(new Todo(getString(R.string.init2), 0));
        db.create(new Todo(getString(R.string.init3), 0));
    }

    private class simpleListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("log", "Main.simpleListener: start");

            // reverse checkstate of item in database
            reverseCheckState(todoList.get(position));

            // update view
            lvMain.setItemChecked(position, (todoList.get(position).getChecked() != 0));

            Log.d("log", "Main.simpleListener: success");
        }
    }

    private class longListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("log", "Main.longListener: start");

            // delete item from database
            db.delete(todoList.get(position));

            // update list
            updateList();

            Log.d("log", "Main.longListener: success");
            return true;
        }
    }
}

