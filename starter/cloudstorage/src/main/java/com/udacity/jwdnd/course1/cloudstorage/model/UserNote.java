package com.udacity.jwdnd.course1.cloudstorage.model;

public class UserNote {
    private Integer noteId;
    private Integer userId;
    private String title;
    private String Description;

    public UserNote(Integer userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        Description = description;
    }

    public String getNoteTitle() {
        return title;
    }

    public void setNoteTitle(String title) {
        this.title = title;
    }

    public String getNoteDescription() {
        return Description;
    }

    public void setNoteDescription(String description) {
        Description = description;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
