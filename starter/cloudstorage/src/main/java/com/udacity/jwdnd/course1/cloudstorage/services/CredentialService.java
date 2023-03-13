package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCredential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service
public class CredentialService {

    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper, HashService hashService) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public int addCredential(UserCredential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = getEncrypted(credential.getPassword(), encodedKey);
        return credentialMapper.insert(new UserCredential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserId()));
    }

    public UserCredential[] getCredentials(Integer userId) {
        UserCredential[] dbCredentials = credentialMapper.getCredentials(userId);
        return Arrays
                .stream(dbCredentials)
                .map(dbCred -> new UserCredential(
                        dbCred.getCredentialId(),
                        dbCred.getUrl(),
                        dbCred.getUsername(),
                        dbCred.getCredentialKey(),
                        dbCred.getPassword(),
                        dbCred.getUserId())).toArray(UserCredential[]::new);
    }

    public UserCredential getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public void updateCredential(UserCredential credential) {
        String encryptedPassword = getEncrypted(credential.getPassword(), credential.getCredentialKey());
        UserCredential credentialToStore = new UserCredential(
                credential.getCredentialId(),
                credential.getUrl(),
                credential.getUsername(),
                credential.getCredentialKey(),
                encryptedPassword,
                credential.getUserId());
        this.credentialMapper.updateCredential(credentialToStore);
    }

    public void deleteCredential(Integer credentialId) {
        this.credentialMapper.deleteCredentialById(credentialId);
    }

    private String getEncrypted(String value, String encodedKey) {
        return encryptionService.encryptValue(value, encodedKey);
    }

    private String getDecrypted(String value, String encodedKey) {
        return encryptionService.decryptValue(value, encodedKey);
    }
}
