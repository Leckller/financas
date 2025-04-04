package com.ruyCorp.dot.repository.entity;

import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // Despesa ou receita.
  String type;
  Double amount;

  @CreationTimestamp
  LocalDate created_at;

  @UpdateTimestamp
  LocalDate updated_at;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  User user;

  @ManyToMany
  @JoinTable(
      name = "tags_transactions",
      joinColumns = @JoinColumn(name = "transaction_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private List<Tag> tags;

  public Transaction (TransactionDto transactionDto) {
    this.setType(transactionDto.type());
    this.setAmount(transactionDto.amount());
  }

}
