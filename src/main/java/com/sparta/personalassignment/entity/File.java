package com.sparta.personalassignment.entity;

import com.sparta.personalassignment.dto.FileReqDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file")
public class File extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(nullable = false ,name="file_name")
    private String fileName;

    @Column(nullable = false, name = "file_size")
    private Long fileSize;

    @Column(nullable = false, name = "file_type")
    private String fileType;

    @Column(nullable = false, name = "file_path")
    private String filePath;

    public File(FileReqDto reqDto) {
        this.fileName = reqDto.getFileName();
        this.schedule = reqDto.getSchedule();
        this.fileSize = reqDto.getFileSize();
        this.filePath = reqDto.getFilePath();
        this.fileType = reqDto.getFileType();
    }

}
