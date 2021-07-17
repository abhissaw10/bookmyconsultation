package com.bmc.userservice.service;

import com.bmc.userservice.exception.InvalidInputException;
import com.bmc.userservice.exception.ResourceUnAvailableException;
import com.bmc.userservice.model.User;
import com.bmc.userservice.repository.S3Repository;
import com.bmc.userservice.repository.UserRepository;
import com.bmc.userservice.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final S3Repository s3Repository;

    public User register(User user) throws InvalidInputException {
        ValidationUtils.validate(user);
        user.setId(UUID.randomUUID().toString());
        user.setCreatedDate(LocalDate.now().toString());
        userRepository.save(user);
        notify(user);
        return user;
    }

    public User getUser(String id) {
        return Optional.ofNullable(userRepository.findById(id))
            .get()
            .orElseThrow(ResourceUnAvailableException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    private void notify(User user){
        notificationService.notifyUser(user);
    }

    public void uploadDocuments(String userId, MultipartFile file) {
        try {
            s3Repository.uploadFileToS3(userId,file);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public ByteArrayOutputStream downloadDocuments(String id, String documentId) throws IOException {
        return s3Repository.downloadFileFromS3(id,documentId);
    }

    public List<String> downloadDocumentMetadata(String userId) {
        return s3Repository.getDocumentsMetadata(userId);
    }
}
