package com.mmall.service.impl;

import com.mmall.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileServiceImpl implements IFileService {

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        // get extension
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString()+fileExtension;
        logger.info("file upload begin, fileName:{}, uploadPath:{}, newFileName: {}", fileName, path, newFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, newFileName);

        try {
            file.transferTo(targetFile);
            // file upload succeed
            // todo upload targetFile to FTP
            // todo after upload, remove the file in upload folder
        } catch (IOException e) {
            logger.error("file upload exception", e);
        }

        return targetFile.getName();
    }

    public static void main(String[] args) {
        String fileName = "abc.jpg";
        System.out.println(fileName.substring(fileName.lastIndexOf(".")));
    }
}
