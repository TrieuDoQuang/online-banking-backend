package com.example.learningcourseapp.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.learningcourseapp.entities.ChapterEntity;
import java.util.Optional;
public interface ChapterRepository extends JpaRepository<ChapterEntity, Long>{
}
