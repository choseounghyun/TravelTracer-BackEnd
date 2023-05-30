package com.project.travelTracer.global.file.service;

import com.project.travelTracer.global.file.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String save(MultipartFile multipartFile) throws FileException;

    void delete(String filePath);
}
