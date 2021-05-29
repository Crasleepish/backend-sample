package com.crasleepish.springbootbackend.bean;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private User author;
    private int bonus;

//    @JsonSerialize(converter = com.crasleepish.springbootbackend.util.DateToLongConverter.class)
//    @JsonDeserialize(converter = com.crasleepish.springbootbackend.util.LongToDateConverter.class)
    private Date commitTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }
}
