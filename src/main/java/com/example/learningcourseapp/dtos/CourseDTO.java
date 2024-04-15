package com.example.learningcourseapp.dtos;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {



    private int discount;

    private String description;

    private String title;

    private double cost;

    private String category;

//    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
//    private List<CommentDTO> comments;
//
//    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
//    private List<RatingDTO> ratings;

}
