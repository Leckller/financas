package com.ruyCorp.dot.service.exception.NotFound;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException () {
        super("Tarefa não encontrada!");
    }
}
