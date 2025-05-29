package br.unifor.service

import br.unifor.dto.TaskDTO
import br.unifor.dto.TaskDetailDTO
import br.unifor.entities.todo.Task
import br.unifor.entities.todo.User
import br.unifor.exception.TaskNotFoundException
import br.unifor.exception.TaskNotOwnedLoggedUserException
import br.unifor.form.CreateTaskForm
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class TaskService {
    fun listTasks(
        search: String?, //
        loggedUser: User, //
    ): List<TaskDTO> = (search?.replace(" ", "") //
        ?.split("") //
        ?.joinToString(separator = "%") //
        ?.uppercase() ?: "%%") //
        .let { filter ->
            Task.list("userOwner.id = ${loggedUser.id} AND isActive = true AND UPPER(nmTask) LIKE '$filter'") //
                .also { println() }.map { TaskDTO(it) } //
                .sortedWith(compareBy<TaskDTO> { it.wasDone } //
                    .thenBy { it.dtDeadline } //
                    .thenByDescending { it.dtDo } //
                    .thenBy { it.nmTask } //
                )
        }

    fun getTaskByHashTask(
        hashTask: String, //
        loggedUser: User, //
    ): TaskDetailDTO = (Task.findByHash(hashTask = hashTask) //
        ?: throw TaskNotFoundException(hashTask = hashTask)) //
        .also { task ->
            task.takeIf { it.isPrivateTask } //
                ?.let {
                    task.takeIf { it.userOwner == loggedUser } //
                        ?: throw TaskNotOwnedLoggedUserException(hashTask = hashTask)
                }
        }.let { TaskDetailDTO(task = it) }

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

    fun editTask(hashTask: String): Nothing = TODO()

    fun removeTask(hashTask: String): Nothing = TODO()
}