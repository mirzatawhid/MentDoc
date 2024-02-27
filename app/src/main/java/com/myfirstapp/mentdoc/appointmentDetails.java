package com.myfirstapp.mentdoc;

public class appointmentDetails {
        String date;
        String doc_id;
        String slot;

        String docName;
        String slot_time;

        String docPost;

        String appointment_id;

    public appointmentDetails(String date, String doc_id, String slot) {
        this.date = date;
        this.doc_id = doc_id;
        this.slot = slot;
    }

    public appointmentDetails(String date, String doc_id, String slot, String docName, String slot_time) {
        this.date = date;
        this.doc_id = doc_id;
        this.slot = slot;
        this.docName = docName;
        this.slot_time = slot_time;
    }

    public appointmentDetails(String date, String doc_id, String slot, String docName, String slot_time, String docPost, String appointment_id) {
        this.date = date;
        this.doc_id = doc_id;
        this.slot = slot;
        this.docName = docName;
        this.slot_time = slot_time;
        this.docPost = docPost;
        this.appointment_id = appointment_id;
    }

    public String getDocPost() {
        return docPost;
    }

    public void setDocPost(String docPost) {
        this.docPost = docPost;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getSlot_time() {
        return slot_time;
    }

    public void setSlot_time(String slot_time) {
        this.slot_time = slot_time;
    }
}
