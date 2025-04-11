package com.ruyCorp.dot.repository.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
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

  private String type;

  private Double amount;

  @CreationTimestamp
  private LocalDate created_at;

  @UpdateTimestamp
  private LocalDate updated_at;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToMany
  @JoinTable(
      name = "tags_transactions",
      joinColumns = @JoinColumn(name = "transaction_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  @Builder.Default
  @JsonManagedReference
  private List<Tag> tags = new ArrayList<>();

  public Transaction(TransactionDto transactionDto) {
    this.type = transactionDto.type();
    this.amount = transactionDto.amount();
    // user e tags devem ser atribuídos em outro ponto do código!
  }
}
