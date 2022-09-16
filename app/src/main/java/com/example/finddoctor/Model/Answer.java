package com.example.finddoctor.Model;

public class Answer {
    private String id,question,questionType,questionImage,imageUrl,username;
    private String doctorImage,doctorName,doctorType,answer;

    public Answer() {
    }

    public Answer(String id, String question, String questionType, String questionImage, String imageUrl, String username, String doctorImage, String doctorName, String doctorType,String answer) {
        this.id = id;
        this.question = question;
        this.questionType = questionType;
        this.questionImage = questionImage;
        this.imageUrl = imageUrl;
        this.username = username;
        this.doctorImage = doctorImage;
        this.doctorName = doctorName;
        this.doctorType = doctorType;
        this.answer=answer;
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

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
