package com.tundecodex.shopping_cart.service.image;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import com.tundecodex.shopping_cart.dto.ImageDto;
import com.tundecodex.shopping_cart.model.Image;

public interface IImageService  {
    Image getImageById(Long id);
    void deleteImageById(Long id);
   List  <ImageDto> saveImages(List <MultipartFile> files, long id);
    void updateImageById(MultipartFile file, Long id);
    
}
