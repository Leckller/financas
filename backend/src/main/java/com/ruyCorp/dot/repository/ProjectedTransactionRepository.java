package com.ruyCorp.dot.repository;

import com.ruyCorp.dot.repository.entity.ProjectedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectedTransactionRepository extends JpaRepository<ProjectedTransaction, Integer> {
}
