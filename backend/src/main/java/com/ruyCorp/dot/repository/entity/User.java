package com.ruyCorp.dot.repository.entity;

import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // Orçamento
  @Getter
  private Double budget;
  // Saldo
  @Getter
  private Double balance;

  @Getter
  @Column(unique = true)
  private String email;
  @Getter
  private String name;
  @Getter
  private String role = "USER";

  @Column(unique = true)
  private String username;
  @Getter
  private String password;

  @CreationTimestamp
  private Date createdAt;
  @UpdateTimestamp
  private Date updatedAt;

  @Getter
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  List<Transaction> transactions;

  public User() {}

  public User(UserCreationDto userCreationDto) {
    this.name = userCreationDto.name();
    this.email = userCreationDto.email();
    this.password = userCreationDto.password();
    this.username = userCreationDto.username();
  }

  public void incrementBalance(Double value) {
    this.balance += value;
  }

  public void decrementBalance(Double value) {
    this.balance -= value;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role));
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

}
