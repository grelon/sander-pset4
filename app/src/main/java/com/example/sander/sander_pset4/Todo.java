package com.example.sander.sander_pset4;

/**
 * Created by sander on 6-5-17.
 *
 * Object consists of text and an attribute indicating if it has been checked
 */

public class Todo {
    private String text;
    private int checked;
    private int _id;

    // constructor
    public Todo(String text, int checked) {
        this.text = text;
        this.checked = checked;
    }

    public Todo(String text, int checked, int _id) {
        this.text = text;
        this.checked = checked;
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
