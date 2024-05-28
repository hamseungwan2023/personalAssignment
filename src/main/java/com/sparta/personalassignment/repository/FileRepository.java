package com.sparta.personalassignment.repository;

import com.sparta.personalassignment.entity.File;
import com.sparta.personalassignment.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findBySchedule(Schedule schedule);
}
