package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Image;
import com.ftrainer.ftrainer.repositories.ImageRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public ImageServiceImpl(ImageRepository imageRepository,
                            UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void saveImageFile(MultipartFile file, Integer userId) throws IOException {
        Image image = new Image();
        image.setImage(file.getBytes());
        image.setUser(userRepository.findById(userId).get());
        imageRepository.save(image);
    }

    public byte[] getImageByUserId(int userId) {
        Optional<Image> image = imageRepository.findByUserId(userId);
        return image.map(Image::getImage).orElse(null);
    }

    public byte[] getDefaultImage() {
        try {
            ClassPathResource resource = new ClassPathResource("static/images/default_user_picture.jpg");
            try (InputStream inputStream = resource.getInputStream()) {
                return inputStream.readAllBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
