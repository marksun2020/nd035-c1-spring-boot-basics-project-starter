package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserCredential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/add")
    public String addOrUpdateCredential(@ModelAttribute UserCredential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (credential.getCredentialId() != null) {
            UserCredential dbCredential = this.credentialService.getCredential(credential.getCredentialId());
            credential.setUserId(dbCredential.getUserId());
            credential.setCredentialKey(dbCredential.getCredentialKey());
            this.credentialService.updateCredential(credential);
            redirectAttributes.addFlashAttribute("message", String.format("The credential is successfully updated"));
        }
        else {
            int userId = this.userService.getUser(authentication.getName()).getUserId();
            credential.setUserId(userId);
            this.credentialService.addCredential(credential);
            redirectAttributes.addFlashAttribute("message", String.format("The credential is successfully added"));
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, RedirectAttributes redirectAttributes) {
        this.credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("message", String.format("The credential is successfully removed"));

        return "redirect:/home";
    }

}
