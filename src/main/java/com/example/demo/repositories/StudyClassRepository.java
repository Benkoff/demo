package com.example.demo.repositories;

import com.example.demo.models.StudyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyClassRepository extends JpaRepository<StudyClass, Long> {

}
