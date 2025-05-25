package br.unifor.service

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
    ): String {
        TODO()
    }

    fun editTask(): Nothing = TODO()

    fun editTask(hashTask: String): Nothing = TODO()

    fun removeTask(hashTask: String): Nothing = TODO()
}
