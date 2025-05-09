package com.ruyCorp.dot.repository;

import com.ruyCorp.dot.repository.entity.Transaction;
import com.ruyCorp.dot.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

  Page<Transaction> findAllByUser(User user, Pageable pageable);

  @Query("SELECT t FROM Transaction t WHERE MONTH(t.createdAt) = :mes AND YEAR(t.createdAt) = :ano")
  List<Transaction> findByMesEAno(@Param("mes") int mes, @Param("ano") int ano);

  @Query("""
    SELECT t FROM Transaction t
    WHERE t.user = :user
    AND (:dataInicio IS NULL OR t.createdAt >= :dataInicio)
    AND (:dataFim IS NULL OR t.createdAt <= :dataFim)
    """)
  Page<Transaction> findByUserAndOptionalData(
      @Param("user") User user,
      @Param("dataInicio") LocalDateTime dataInicio,
      @Param("dataFim") LocalDateTime dataFim,
      Pageable pageable
  );
}
