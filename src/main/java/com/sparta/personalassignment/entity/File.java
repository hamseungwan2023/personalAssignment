package com.sparta.personalassignment.entity;

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

    public File(String fileName, Schedule schedule, Long fileSize, String filePath, String fileType) {
        this.fileName = fileName;
        this.schedule = schedule;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.fileType = fileType;
    }

}
