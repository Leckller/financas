package com.ruyCorp.dot.repository.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "projections")
public class Projection {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private Double meta;

  @ManyToMany
  @JoinTable(
      name = "projection_projected_transactions",
      joinColumns = @JoinColumn(name = "projection_id"),
      inverseJoinColumns = @JoinColumn(name = "projected_transaction_id")
  )
  @Builder.Default
  @JsonManagedReference
  private List<ProjectedTransaction> projectedTransactions = new ArrayList<>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

}
