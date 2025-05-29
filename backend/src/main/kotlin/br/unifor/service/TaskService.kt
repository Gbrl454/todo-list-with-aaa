package br.unifor.service

import br.unifor.dto.TaskDTO
import br.unifor.entities.todo.Task
import br.unifor.entities.todo.User
import br.unifor.form.CreateTaskForm
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TaskService {
    fun listTasks(): Nothing = TODO()

    fun getTaskByHashTask(hashTask: String): Nothing = TODO()

    fun createTask(
        form: CreateTaskForm, //
        loggedUser: User, //
    ): TaskDTO = Task().apply {
        this.nmTask = form.nmTask
        this.dsTask = form.dsTask
        this.isPrivateTask = form.isPrivateTask
        this.dtDeadline = form.dtDeadline
        this.generateHashAndPersist(userOwner = loggedUser)
    }.let { TaskDTO(it) }

    fun editTask(): Nothing = TODO()

    fun editTask(hashTask: String): Nothing = TODO()

    fun removeTask(hashTask: String): Nothing = TODO()
}