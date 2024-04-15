package com.example.learningcourseapp.dtos;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private ChapterDTO chapter;

    @Column (name ="content_video")
    private String contentVideo;

    @Column (name = "content_text")
    private String contentText;
}
