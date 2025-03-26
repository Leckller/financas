package com.ruyCorp.dot.repository.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToMany(mappedBy = "tags")
  private List<Transaction> transactions;

  public Tag() {};

  public Integer getId() { return id; }

  public void setId(Integer id) { this.id = id; }

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public User getUser() { return user; }

  public void setUser(User user) { this.user = user; }

  public List<Transaction> getTransactions() { return transactions; }

  public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
}
