package com.zam.springsecurityjwt.service.impl;

import com.zam.springsecurityjwt.configuration.FileUploadProperties;
import com.zam.springsecurityjwt.exeptions.ApiRequestException;
import com.zam.springsecurityjwt.service.FileStorageService;
import jakarta.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageImpl implements FileStorageService {

    private final Path dirLocation;

    public Path getDirLocation() {
        return dirLocation;
    }

    @Autowired
    public FileStorageImpl(FileUploadProperties fileUploadProperties){
        this.dirLocation = Paths.get(fileUploadProperties.getLocation()).toAbsolutePath().normalize();
        System.out.println(this.dirLocation);
    }
    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.dirLocation);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new ApiRequestException("could not create upload dir");
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            String hashName = DigestUtils.md5DigestAsHex(file.getBytes());
            String finalFileName = hashName+"."+extension;
            Path dFile = this.dirLocation.resolve(finalFileName);
            Files.copy(file.getInputStream() , dFile , StandardCopyOption.REPLACE_EXISTING);
            return finalFileName;
        }catch (Exception e){
            throw new ApiRequestException("could not upload file");
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        Path file = this.dirLocation.resolve(fileName).normalize();
        Resource resource = UrlResource.from(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        else {
            throw new ApiRequestException("Could not find file");
        }
    }
}
