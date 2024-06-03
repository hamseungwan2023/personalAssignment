package com.sparta.personalassignment.exception;

import com.sparta.personalassignment.entity.File;
import com.sparta.personalassignment.repository.FileRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandlerException {
    private final FileRepository fileRepository;

    public FileHandlerException(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File FileHandler(MultipartFile multipartFile, String filepath, boolean isSaveFile, Long fileId) {
        String fileName = multipartFile.getOriginalFilename();
        Long fileSize = multipartFile.getSize();
        String fileExt = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

        File entityFile = null;

        if (fileExt.equals("jpg") || fileExt.equals("jpeg") || fileExt.equals("png")) {
            if (isSaveFile) {
                entityFile = File.builder()
                        .fileName(fileName)
                        .fileSize(fileSize)
                        .filePath(fileName + "/" + fileExt)
                        .fileType(fileExt)
                        .build();
                fileRepository.save(entityFile);
            } else {
                entityFile = fileRepository.findById(fileId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));
                entityFile.update(fileName, fileSize, fileExt, filepath + "/" + fileName);
            }
        } else {
            throw new IllegalArgumentException("jpg,jpeg,png인 파일만 넣어주세요.");
        }

        Path dirPath = Paths.get(filepath);

        try {
            if (!Files.exists(dirPath)) {
                Files.createDirectory(dirPath);
            }
            Path filePath = dirPath.resolve(fileName);
            multipartFile.transferTo(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return entityFile;
    }
}
