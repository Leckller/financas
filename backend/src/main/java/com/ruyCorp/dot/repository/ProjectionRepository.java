package com.ruyCorp.dot.repository;

import com.ruyCorp.dot.repository.entity.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Integer> {
  Optional<Projection> findByName(String name);
}
