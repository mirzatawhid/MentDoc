package com.myfirstapp.mentdoc;

public class QuesModelClass {
    String ques;
    String optionA;
    String optionB;
    String optionC;
    int pointA,pointB,pointC;

    String selectedByUser;
    public QuesModelClass(String ques, String optionA, String optionB, String optionC, int pointA, int pointB, int pointC) {
        this.ques = ques;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public int getPointA() {
        return pointA;
    }

    public void setPointA(int pointA) {
        this.pointA = pointA;
    }

    public int getPointB() {
        return pointB;
    }

    public void setPointB(int pointB) {
        this.pointB = pointB;
    }

    public int getPointC() {
        return pointC;
    }

    public void setPointC(int pointC) {
        this.pointC = pointC;
    }

    public String getSelectedByUser() {
        return selectedByUser;
    }

    public void setSelectedByUser(String selectedByUser) {
        this.selectedByUser = selectedByUser;
    }
}
