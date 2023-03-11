package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNote;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper mapper) {
        this.noteMapper = mapper;
    }

    public int addNote(UserNote note) throws IOException {
        return this.noteMapper.insert(note);
    }

    public void updateNote(UserNote note) {
        this.noteMapper.updateNote(note);
    }

    public List<UserNote> getNotes(Integer userId) {
        UserNote[] notes = this.noteMapper.getNotesByUser(userId);
        return Arrays.stream(notes).collect(Collectors.toList());
    }

    public UserNote getNote(Integer noteId) {
        UserNote note = this.noteMapper.getNoteById(noteId);
        return note;
    }

    public boolean deleteNote(Integer noteId) {
        return this.noteMapper.deleteNoteById(noteId);
    }
}
