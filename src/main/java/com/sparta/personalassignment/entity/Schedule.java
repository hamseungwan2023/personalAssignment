package com.sparta.personalassignment.entity;


import com.sparta.personalassignment.dto.ScheduleReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "schedule")
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


    public Schedule(ScheduleReqDto reqDto, User user) {
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
        this.user = user;
    }
    public void update (ScheduleReqDto reqDto){
        this.title = reqDto.getTitle();
        this.detail = reqDto.getDetail();
    }
}
