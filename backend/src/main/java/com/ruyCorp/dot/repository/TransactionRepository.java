package com.ruyCorp.dot.repository;

import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

  Page<Transaction> findAllByUser(User user, Pageable pageable);

  @Query("SELECT t FROM Transaction t WHERE MONTH(t.created_at) = :mes AND YEAR(t.created_at) = :ano")
  List<Transaction> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);

}
