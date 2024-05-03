package com.example.onlinebankingapp.repositories;
import com.example.onlinebankingapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<UserEntity, Long>{
}
