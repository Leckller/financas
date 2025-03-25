package com.ruyCorp.dot.repository.entity;

import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.types.TransactionType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // Despesa ou receita.
  String type;
  Double value;

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


  public Double getValue() { return value; }

  public void setValue(Double value) { this.value = value; }

  public String getType() { return type; }

  public User getUser() { return user; }

  public void setUser(User user) { this.user = user; }

  // Deve receber uma string de valor "despesa" ou "receita".
  public void setType(String type) { this.type = type; }

  public Transaction (TransactionDto transactionDto) {
    this.setType(transactionDto.type());
    this.setValue(transactionDto.value());
  }

}
