package com.ruyCorp.dot.service;

import com.ruyCorp.dot.controller.dto.Projection.CreateProjectedTransactionDto;
import com.ruyCorp.dot.controller.dto.Projection.CreateProjectionDto;
import com.ruyCorp.dot.controller.dto.Projection.EditProjectedTransactionDto;
import com.ruyCorp.dot.controller.dto.Projection.EditProjectionDto;
import com.ruyCorp.dot.repository.ProjectedTransactionRepository;
import com.ruyCorp.dot.repository.ProjectionRepository;
import com.ruyCorp.dot.repository.entity.ProjectedTransaction;
import com.ruyCorp.dot.repository.entity.Projection;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.exception.AlreadyExists.AlreadyExistsException;
import com.ruyCorp.dot.service.exception.NoPermissionException;
import com.ruyCorp.dot.service.exception.NotFound.ProjectedTransactionNotFoundException;
import com.ruyCorp.dot.service.exception.NotFound.ProjectionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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

  public ProjectedTransaction findProjectedTransactionById(String username, Integer id) throws NoPermissionException {
    User user = this.userService.findByUsername(username);
    ProjectedTransaction projectedTransaction = this.projectedTransactionRepository.findById(id).orElseThrow(ProjectedTransactionNotFoundException::new);
    this.projectedTransactionBelongsUser(user, projectedTransaction);
    return projectedTransaction;
  }

  public void projectedTransactionBelongsUser(User user, ProjectedTransaction projectedTransaction) throws NoPermissionException {
    if (!Objects.equals(user.getName(), projectedTransaction.getUser().getName())) {
      throw new NoPermissionException("Você não tem permissão para ver Transações projetadas de outras pessoas.");
    }
  }

  public void projectionBelongsUser(User user, Projection projection) throws NoPermissionException {
    if (!Objects.equals(user.getName(), projection.getUser().getUsername())) {
      throw new NoPermissionException("Você não tem permissão para ver projeções de outras pessoas.");
    }
  }

  public void projectionExists(String name, String username) throws AlreadyExistsException {
    User user = this.userService.findByUsername(username);
    Optional<ProjectedTransaction> projectedTransaction = this.projectedTransactionRepository.findByName(name);
    if(projectedTransaction.isPresent() && Objects.equals(projectedTransaction.get().getId(), user.getId())) {
      throw new AlreadyExistsException("Você já criou uma projeção com esse nome.");
    }
  }
  public void projectedTransactionExists(String name, String username) throws AlreadyExistsException {
    User user = this.userService.findByUsername(username);
    Optional<Projection> projection = this.projectionRepository.findByName(name);
    if(projection.isPresent() && Objects.equals(projection.get().getId(), user.getId())) {
      throw new AlreadyExistsException("Você já criou uma transação projetada com esse nome.");
    }
  }

  public Projection findProjectionById(String username, Integer id) throws NoPermissionException {
    User user = this.userService.findByUsername(username);
    Projection projection = this.projectionRepository.findById(id).orElseThrow(ProjectionNotFoundException::new);
    this.projectionBelongsUser(user, projection);
    return projection;
  }

  public Projection syncProjectedTransactionById(Integer projectionId, Integer projectedTransactionId, String username) throws NoPermissionException {
    Projection projection = this.findProjectionById(username, projectionId);
    ProjectedTransaction transaction = this.findProjectedTransactionById(username, projectedTransactionId);
    if(projection.getProjectedTransactions().contains(transaction)) {
      projection.getProjectedTransactions().remove(transaction);
    } else {
      projection.getProjectedTransactions().add(transaction);
    }
    return this.projectionRepository.save(projection);
  }

  public ProjectedTransaction createProjectedTransaction(String username, CreateProjectedTransactionDto dto) throws AlreadyExistsException {
    this.projectedTransactionExists(dto.name(), username);
    ProjectedTransaction projectedTransaction = new ProjectedTransaction();
    projectedTransaction.setName(dto.name());
    return this.projectedTransactionRepository.save(projectedTransaction);
  }

  public Projection createProjection(String username, CreateProjectionDto dto) throws AlreadyExistsException {
    this.projectionExists(dto.name(), username);
    Projection projection = new Projection();
    projection.setName(dto.name());
    return this.projectionRepository.save(projection);
  }

  public void deleteProjectedTransaction(String username, Integer id) throws NoPermissionException {
    ProjectedTransaction projectedTransaction = this.findProjectedTransactionById(username, id);
    this.projectedTransactionRepository.delete(projectedTransaction);
  }
  public void deleteProjection(String username, Integer id) throws NoPermissionException {
    Projection projection = this.findProjectionById(username, id);
    this.projectionRepository.delete(projection);
  }

  public ProjectedTransaction editProjectedTransaction(EditProjectedTransactionDto dto, String username, Integer id) throws NoPermissionException {
    ProjectedTransaction projectedTransaction = this.findProjectedTransactionById(username, id);
    if (dto.name() != null) {
      projectedTransaction.setName(dto.name());
    }
    return projectedTransactionRepository.save(projectedTransaction);
  }
  public Projection editProjection(EditProjectionDto dto, String username, Integer id) throws NoPermissionException {
    Projection projection = this.findProjectionById(username, id);
    if (dto.name() != null) {
      projection.setName(dto.name());
    }
    return projectionRepository.save(projection);
  }


}
