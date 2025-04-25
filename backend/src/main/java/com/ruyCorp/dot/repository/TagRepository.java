package com.ruyCorp.dot.repository;

import com.ruyCorp.dot.repository.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

  Optional<Tag> findByName(String name);
  Page<Tag> listWithPages(Pageable pageable);
}
