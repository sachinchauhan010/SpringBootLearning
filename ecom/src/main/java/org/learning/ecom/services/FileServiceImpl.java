package org.learning.ecom.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    private String uploadImage(String path, MultipartFile file) throws IOException {

        //1. Get the name of original File
        String originalFilename = file.getOriginalFilename();

        //2. Generate unique file name
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

        //3. Check if directory exists or create it
        File folder = new File(path);  // Just the directory path
        if (!folder.exists()) {
            folder.mkdirs();
        }

        //4. Create the full file path
        String filePath = path + File.separator + fileName;

        //5. Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //6. Return the filename
        return fileName;
    }
}
