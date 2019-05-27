package com.nbehary.heartbeat.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todo")
public class TodoItem {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "date_time")
    private Date dateTime;  //TODO: Replace with OffsetDateTime

    @ColumnInfo(name = "done")
    private boolean isDone;

    public TodoItem(String text, Date dateTime, boolean isDone) {
        this.text = text;
        this.dateTime = dateTime;
        this.isDone = isDone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


}
