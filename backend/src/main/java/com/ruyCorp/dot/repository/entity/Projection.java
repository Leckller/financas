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

  @OneToMany(mappedBy = "projection", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  @Builder.Default
  private List<ProjectedTransaction> projectedTransaction = new ArrayList<>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

}
