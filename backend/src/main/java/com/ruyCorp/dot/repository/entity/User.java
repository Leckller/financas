package com.ruyCorp.dot.repository.entity;

import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // Orçamento
  private Double budget;
  // Saldo
  private Double balance;

  @Column(unique = true)
  private String email;
  private String name;
  private String role = "USER";

  @Column(unique = true)
  private String username;
  private String password;

  @CreationTimestamp
  private Date createdAt;
  @UpdateTimestamp
  private Date updatedAt;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  List<Transaction> transactions;

  public User() {}

  public User(UserCreationDto userCreationDto) {
    this.name = userCreationDto.name();
    this.email = userCreationDto.email();
    this.password = userCreationDto.password();
    this.username = userCreationDto.username();
  }

  public Double getBudget() {
    return budget;
  }

  public void setBudget(Double budget) {
    this.budget = budget;
  }

  public Double getBalance() {
    return balance;
  }

  public void incrementBalance(Double value) {
    this.balance += value;
  }

  public void decrementBalance(Double value) {
    this.balance -= value;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role));
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
