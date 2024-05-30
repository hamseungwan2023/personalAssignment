package com.sparta.personalassignment.entity;


import com.sparta.personalassignment.dto.ScheduleReqDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule")
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "detail", nullable = false, length = 500)
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    public Schedule update (ScheduleReqDto reqDto){
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
        return this;
    }
    public Schedule updateWithFile(ScheduleReqDto reqDto, File file) {
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
        this.file = file;
        return this;
    }

}
