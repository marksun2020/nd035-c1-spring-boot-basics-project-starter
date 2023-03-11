package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    UserFile getFile(int fileId);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    UserFile getFileByName(String fileName);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(UserFile file);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    UserFile[] getFilesByUser(Integer userId);

    @Delete("DELETE FILES WHERE fileName = #{fileName}")
    boolean deleteFile(String fileName);
}
