package com.tundecodex.shopping_cart.service.image;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import javax.sql.rowset.serial.SerialBlob;

import com.tundecodex.shopping_cart.dto.ImageDto;
import com.tundecodex.shopping_cart.exceptions.CategoryNotFoundException;
import com.tundecodex.shopping_cart.exceptions.ResourceNotFoundException;
import com.tundecodex.shopping_cart.model.Image;
import com.tundecodex.shopping_cart.model.Product;
import com.tundecodex.shopping_cart.repository.ImageRepository;
import com.tundecodex.shopping_cart.service.product.IProductService;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {

        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find image with id " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("Image not found!");
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, long id) {
        Product product = productService.getProductById(id);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {

                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String url = "/api/v1/images/image/download/";
                String downloadUrl = url + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(url + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImageById(MultipartFile file, Long id) {

        Image image = getImageById(id);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());

            image.setImage(new SerialBlob(file.getBytes()));

            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
