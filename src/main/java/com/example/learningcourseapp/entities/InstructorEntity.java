package com.example.learningcourseapp.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name ="instructors")

public class InstructorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "name")
    private String name;
    @Column (name = "date_of_birth")
    private Date dateOfBirth;
    @Column (name = "balance")
    private long balance;
    @Column (name = "email")
    private String email;
    @Column (name = "phone_number")
    private Long phoneNumber;

    @OneToMany (targetEntity = CourseEntity.class)
    private List<CourseEntity> courses;
}
