package com.example.learningcourseapp.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.learningcourseapp.entities.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface LessonRepository extends JpaRepository<LessonEntity, Long>{
}
