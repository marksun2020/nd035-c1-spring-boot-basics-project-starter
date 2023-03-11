package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UserCredential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    UserCredential[] getCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, credentialKey, password, userId) VALUES(#{url}, #{username}, #{credentialKey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(UserCredential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    UserCredential getCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialId = #{credentialId}")
    void updateCredential(UserCredential credential);

    @Delete("DELETE CREDENTIALS WHERE credentialId = #{credentialId}")
    boolean deleteCredentialById(Integer credentialId);
}
