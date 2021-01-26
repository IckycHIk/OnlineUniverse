package com.devforyou.onlineunivers.FireBase.Model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

;
import java.util.Date;

public class ResultModelF {

    private String id;
    private String user_id;
    private String test_id;
    private long score;
    private long corect_answer;
    private long count_answer;
    private String time;
    private Timestamp test_date;



    public ResultModelF() {
    }

    public ResultModelF(String id, String user_id, String test_id, long score, long corect_answer, long count_answer, String time, Timestamp test_date) {
        this.id = id;
        this.user_id = user_id;
        this.test_id = test_id;
        this.score = score;
        this.corect_answer = corect_answer;
        this.count_answer = count_answer;
        this.time = time;
        this.test_date = test_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getCorect_answer() {
        return corect_answer;
    }

    public void setCorect_answer(long corect_answer) {
        this.corect_answer = corect_answer;
    }

    public long getCount_answer() {
        return count_answer;
    }

    public void setCount_answer(long count_answer) {
        this.count_answer = count_answer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Timestamp getTest_date() {
        return test_date;
    }

    public void setTest_date(Timestamp test_date) {
        this.test_date = test_date;
    }
}
