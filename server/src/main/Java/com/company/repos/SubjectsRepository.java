package com.company.repos;

import com.company.entity.SubjectsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectsRepository extends JpaRepository<SubjectsEntity, Integer> {
}