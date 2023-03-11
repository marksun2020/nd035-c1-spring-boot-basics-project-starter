package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UserNote;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(UserNote note);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription=#{noteDescription} WHERE noteId = #{noteId}")
    void updateNote(UserNote note);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    UserNote[] getNotesByUser(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteId=#{noteId}")
    UserNote getNoteById(Integer noteId);

    @Delete("DELETE NOTES WHERE noteId = #{noteId}")
    boolean deleteNoteById(Integer noteId);
}
