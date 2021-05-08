package com.example.office_task_mangement.model;

public class model
{
private String title;
private String note;
private  String date;
private String id;
public model()
{

}

    public model(String title, String note, String date, String id) {
        this.title = title;
        this.note = note;
        this.date = date;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getData() {
        return date;
    }

    public void setData(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

