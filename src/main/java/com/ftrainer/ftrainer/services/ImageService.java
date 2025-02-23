package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Image;
import com.ftrainer.ftrainer.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void saveImageFile(MultipartFile file, Integer userId) throws IOException;

    byte[] getImageByUserId(int userId) throws IOException;

    byte[] getDefaultImage();
}
