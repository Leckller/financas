package com.ruyCorp.dot.service;

import com.ruyCorp.dot.controller.dto.Projection.CreateProjectedTransactionDto;
import com.ruyCorp.dot.controller.dto.Projection.CreateProjectionDto;
import com.ruyCorp.dot.repository.ProjectedTransactionRepository;
import com.ruyCorp.dot.repository.ProjectionRepository;
import com.ruyCorp.dot.repository.entity.ProjectedTransaction;
import com.ruyCorp.dot.repository.entity.Projection;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.exception.NotFound.ProjectedTransactionNotFoundException;
import com.ruyCorp.dot.service.exception.NotFound.ProjectionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectionService {

  private final ProjectionRepository projectionRepository;
  private final ProjectedTransactionRepository projectedTransactionRepository;
  private final UserService userService;

  @Autowired
  public ProjectionService(ProjectionRepository projectionRepository, ProjectedTransactionRepository projectedTransactionRepository, UserService userService) {
    this.projectionRepository = projectionRepository;
    this.projectedTransactionRepository = projectedTransactionRepository;
    this.userService = userService;
  }

  public ProjectedTransaction findProjectedTransactionById(String username, Integer id) {
    User user = this.userService.findByUsername(username);
    return this.projectedTransactionRepository.findById(id).orElseThrow(ProjectedTransactionNotFoundException::new);
  }
  
  public Projection findProjectionById(String username, Integer id) {
    User user = this.userService.findByUsername(username);
    return this.projectionRepository.findById(id).orElseThrow(ProjectionNotFoundException::new);
  }

  public void createProjectedTransaction(String username, CreateProjectedTransactionDto dto) {}
  public void createProjection(String username, CreateProjectionDto dto) {}

  public void deleteProjectedTransaction(String username, Integer id) {}
  public void deleteProjection(String username, Integer id) {}

  public void editProjectedTransaction(String username, Integer id) {}
  public void editProjection(String username, Integer id) {}


}
