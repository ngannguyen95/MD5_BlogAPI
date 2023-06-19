package ra.model.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.utils.ImageUtils;
import ra.model.entity.Image;
import ra.model.repository.ImageRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ImageServiceImpl  {
   private final ImageRepository imageRepository;


    public String uploadFile(MultipartFile file) throws IOException {
        Image imageName = imageRepository.save(Image.builder()
                .image(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageName != null) {
            return "Upload thành công file:       " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName) {
        Optional<Image> img = imageRepository.findByImage(fileName);
        byte[] image = ImageUtils.decompressImage(img.get().getImageData());
        return image;
    }
    public Optional<Image> findImageById(Long id){
        Optional<Image> image = imageRepository.findImageById(id);
        return image;
    }

}
