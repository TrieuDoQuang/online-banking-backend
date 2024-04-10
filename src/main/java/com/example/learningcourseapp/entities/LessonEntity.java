package com.example.learningcourseapp.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="lessons")

public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private ChapterEntity chapter;

    @Column (name ="content_video")
    private String contentVideo;

    @Column (name = "content_text")
    private String contentText;
}
