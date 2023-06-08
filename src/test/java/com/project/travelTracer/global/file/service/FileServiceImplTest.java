package com.project.travelTracer.global.file.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FileServiceImplTest {

    @Autowired
    FileService fileService;

    private MockMultipartFile getMockUploadFile() throws IOException{
        return new MockMultipartFile("file", "file.jpg", "image/jpg", new FileInputStream("/Users/yechan/Desktop/diary.jpg"));
    }

    @Test
    public void file_save_success() throws Exception {
        String filePath = fileService.save(getMockUploadFile());
        log.info(filePath);
        File file = new File(filePath);
        assertThat(file.exists()).isTrue();

        fileService.delete(filePath);
    }
}