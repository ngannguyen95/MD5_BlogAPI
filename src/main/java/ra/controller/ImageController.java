package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.model.service.ICatalogService;
import ra.model.service.IUserService;
import ra.model.serviceImpl.ImageServiceImpl;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final ImageServiceImpl imageService;

    @PostMapping("/create")
    public ResponseEntity<?> createImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadFiles = imageService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadFiles);
    }
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData = imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

}
