package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Image;
import com.ftrainer.ftrainer.repositories.ImageRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

}
