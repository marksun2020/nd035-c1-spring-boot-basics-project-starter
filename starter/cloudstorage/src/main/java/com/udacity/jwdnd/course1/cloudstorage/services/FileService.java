package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper mapper) {
        this.fileMapper = mapper;
    }

    public int addFile(MultipartFile file, int userId) throws IOException {
        UserFile userFile = new UserFile(file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), userId, file.getBytes());
        return this.fileMapper.insert(userFile);
    }

    public List<String> getFiles(Integer userId) {
        UserFile[] files = this.fileMapper.getFilesByUser(userId);
        return Arrays.stream(files).map(UserFile::getFileName).collect(Collectors.toList());
    }

    public UserFile getFile(String fileName) {
        UserFile file = this.fileMapper.getFileByName(fileName);
        return file;
    }

    public boolean deleteFile(String fileName) {
        return this.fileMapper.deleteFile(fileName);
    }
}
