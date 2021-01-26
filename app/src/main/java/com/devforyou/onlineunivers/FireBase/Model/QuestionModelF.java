package com.devforyou.onlineunivers.FireBase.Model;
import java.io.Serializable;

public class QuestionModelF {

    private String id;
    private String test_id;
    private String question;
    private String opt_A;
    private String opt_B;
    private String opt_C;
    private String opt_D;
    private String answer;

    public QuestionModelF() {

    }
    //constructer for setting values


    public QuestionModelF(String id, String test_id, String question, String opt_A, String opt_B, String opt_C, String opt_D, String answer) {
        this.id = id;
        this.test_id = test_id;
        this.question = question;
        this.opt_A = opt_A;
        this.opt_B = opt_B;
        this.opt_C = opt_C;
        this.opt_D = opt_D;
        this.answer = answer;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOpt_A() {
        return opt_A;
    }

    public void setOpt_A(String opt_A) {
        this.opt_A = opt_A;
    }

    public String getOpt_B() {
        return opt_B;
    }

    public void setOpt_B(String opt_B) {
        this.opt_B = opt_B;
    }

    public String getOpt_C() {
        return opt_C;
    }

    public void setOpt_C(String opt_C) {
        this.opt_C = opt_C;
    }

    public String getOpt_D() {
        return opt_D;
    }

    public void setOpt_D(String opt_D) {
        this.opt_D = opt_D;
    }


    public String getAnswerText(){
        if(answer.equals("A"))
            return opt_A;
         else if(answer.equals("B"))
            return opt_B;
        else if(answer.equals("C"))
            return opt_C;
       else if(answer.equals("D"))

       return opt_D;
       else return "Произошла ошибка";
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}