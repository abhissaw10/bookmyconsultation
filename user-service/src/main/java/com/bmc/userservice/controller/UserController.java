package com.bmc.userservice.controller;

import com.bmc.userservice.exception.InvalidInputException;
import com.bmc.userservice.model.User;
import com.bmc.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/users")
    public ResponseEntity<User> register(@RequestBody User user) throws InvalidInputException {
       return ResponseEntity.ok(userService.register(user));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/users/{id}/documents")
    public ResponseEntity<String> uploadDocuments(@RequestParam("files") MultipartFile[] files, @PathVariable("id") String doctorId) throws IOException {
        int index=0;
        for(MultipartFile file: files){
            String name = file.getName();
            userService.uploadDocuments(doctorId,file);
        }
        return ResponseEntity.ok("File(s) uploaded Successfully");
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/documents/{documentId}")
    public ResponseEntity<byte[]> downloadDocuments(@PathVariable String userId, @PathVariable("documentId") String documentId) throws IOException {
        String contentType = "application/octet-stream";
        ByteArrayOutputStream downloadStream = userService.downloadDocuments(userId,documentId);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userId + "\"")
            .body(downloadStream.toByteArray());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/documents/metadata")
    public ResponseEntity<List<String>> downloadDocumentMetadata(@PathVariable String userId) throws IOException {
        return ResponseEntity.ok(userService.downloadDocumentMetadata(userId));
    }
}
