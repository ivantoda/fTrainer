package com.ftrainer.ftrainer.controllers;

import com.ftrainer.ftrainer.dto.ImagePayload;
import com.ftrainer.ftrainer.security.SecurityUtils;
import com.ftrainer.ftrainer.services.ImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/image/{userId}")
        public ResponseEntity<byte[]> getUserImage(@PathVariable int userId) throws IOException {
            byte[] imageData = imageService.getImageByUserId(userId);

            if (imageData == null) {
                imageData = imageService.getDefaultImage();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        }
    @GetMapping("/profilePicture")
    public ResponseEntity<byte[]> getCurrentUserProfilePicture() throws IOException {
        int userId = SecurityUtils.getCurrentUserId();
        byte[] imageData = imageService.getImageByUserId(userId);

        if (imageData == null) {
            imageData = imageService.getDefaultImage();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}