package com.example.sander.sander_pset4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sander on 8-5-17.
 */

public class TodoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Todo> todolist;
    private LayoutInflater inflater;

    public TodoAdapter(Context context, ArrayList<Todo> todolist) {
        this.context = context;
        this.todolist = todolist;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return todolist.size();
    }

    @Override
    public Object getItem(int position) {
        return todolist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get view for list item
        View view = inflater.inflate(R.layout.list_item, parent, false);

        // get view within list item
        TextView tvTodo = (TextView) view.findViewById(R.id.listCheckedTV);

        // set view
        Boolean b = (todolist.get(position).getChecked() != 0);
        tvTodo.setText(todolist.get(position).getText());

        return view;
    }
}
