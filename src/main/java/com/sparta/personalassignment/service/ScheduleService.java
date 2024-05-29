package com.sparta.personalassignment.service;

import com.sparta.personalassignment.dto.FileReqDto;
import com.sparta.personalassignment.entity.File;
import com.sparta.personalassignment.entity.Schedule;
import com.sparta.personalassignment.repository.FileRepository;
import com.sparta.personalassignment.dto.ScheduleReqDto;
import com.sparta.personalassignment.dto.ScheduleResDto;
import com.sparta.personalassignment.entity.User;
import com.sparta.personalassignment.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private final ScheduleRepository scheduleRepository;
    private final FileRepository fileRepository;




    //일정 생성
    public ScheduleResDto save(ScheduleReqDto reqDto,
                               User user,
                               MultipartFile multipartFile,
                               String filepath) {

        Schedule schedule = new Schedule(reqDto, user);

        if (multipartFile != null) {
            String fileName = multipartFile.getOriginalFilename();
            Long fileSize = multipartFile.getSize();
            String fileExt = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

            FileReqDto fileReqDto = new FileReqDto(fileName,filepath,fileExt,fileSize,schedule);
            File entityFile = new File(fileReqDto);


            if(fileExt.equals("jpg") || fileExt.equals("jpeg") || fileExt.equals("png")){
                fileRepository.save(entityFile);
            }else{
                throw new IllegalArgumentException("jpg,jpeg,png인 파일만 넣어주세요.");
            }
            Path dirPath = Paths.get(filepath);
            
            try{
                if(!Files.exists(dirPath)){
                    Files.createDirectory(dirPath);
                }
                Path filePath = dirPath.resolve(fileName);
                multipartFile.transferTo(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return new ScheduleResDto(scheduleRepository.save(schedule));
    }

    //일정 조회
    public ScheduleResDto findById(Long id) {
        return new ScheduleResDto(Objects.requireNonNull(scheduleRepository.findById(id).orElse(null)));
    }

    //일정 목록 조회
    public List<ScheduleResDto> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResDto> scheduleResDtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleResDtos.add(new ScheduleResDto(schedule));
        }
        return scheduleResDtos;
    }

    //일정 수정
    @Transactional
    public ScheduleResDto updateSchedule(Long id,
                                         String password,
                                         ScheduleReqDto reqDto,
                                         User user) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디 값은 존재하지 않습니다."));

        if (password.equals(user.getPassword())) {
            schedule.update(reqDto);
            return new ScheduleResDto(schedule);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
    }

    //일정 삭제
    public void deleteSchedule(Long id,
                               String password,
                               @AuthenticationPrincipal User user) {
        scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디 값은 존재하지 않습니다.")
        );
        if (password.equals(user.getPassword())) {
            scheduleRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("비밀;번호가 일치하지 않습니다.");
        }
    }

}
