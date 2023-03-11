package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNote;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    public FileController(FileService fileService, UserService userService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping
    public String addFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        int userId = this.userService.getUser(authentication.getName()).getUserId();

        if (!file.getOriginalFilename().isEmpty()) {
            final List<String> files = this.fileService.getFiles(userId);
            String originalFilename = file.getOriginalFilename();
            if (files.contains(originalFilename)) {
                redirectAttributes.addFlashAttribute("message",
                        String.format("A file with the name '%s' has been already loaded before.", originalFilename));
            }
            else {
                this.fileService.addFile(file, userId);
                redirectAttributes.addFlashAttribute("message", String.format("Successfully uploaded file '%s'", originalFilename));
            }
        }
        return "redirect:/home";
    }

    @GetMapping("/view/{fileName:.+}")
    @ResponseBody public ResponseEntity<InputStreamResource> viewFile(@PathVariable String fileName) {
        UserFile file = this.fileService.getFile(fileName);
        MediaType contentType = MediaType.parseMediaType(file.getContentType());
        InputStream in = new ByteArrayInputStream(file.getFileData());

        return ResponseEntity.ok().contentType(contentType).body(new InputStreamResource(in));
    }

    @GetMapping("/delete/{fileName:.+}")
    public String removeFile(@PathVariable String fileName) {
        UserFile file = this.fileService.getFile(fileName);
        this.fileService.deleteFile(file.getFileName());

        return "redirect:/home";
    }

}
