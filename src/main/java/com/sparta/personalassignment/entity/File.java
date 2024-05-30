package com.sparta.personalassignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "file")
public class File extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,name="file_name")
    private String fileName;

    @Column(nullable = false, name = "file_size")
    private Long fileSize;

    @Column(nullable = false, name = "file_type")
    private String fileType;

    @Column(nullable = false, name = "file_path")
    private String filePath;

    public void update(String fileName, Long fileSize, String fileType, String filePath) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.filePath = filePath+"/"+fileName;
    }

}
