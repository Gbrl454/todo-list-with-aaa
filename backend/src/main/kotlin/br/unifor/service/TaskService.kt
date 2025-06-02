package br.unifor.service

import br.unifor.dto.TaskDTO
import br.unifor.dto.TaskDetailDTO
import br.unifor.entities.todo.Task
import br.unifor.entities.todo.TaskEvent
import br.unifor.entities.todo.User
import br.unifor.exception.*
import br.unifor.extensions.testDeadlineDateLessCurrentDate
import br.unifor.extensions.updateIfChanged
import br.unifor.form.CreateTaskForm
import br.unifor.form.EditTaskForm
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.time.LocalDateTime

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

    private fun getTaskByHashTask(
        hashTask: String, //
        loggedUser: User, //
    ): Task = (Task.findByHash(hashTask = hashTask) //
        ?: throw TaskNotFoundException(hashTask = hashTask)) //
        .also { task ->
            task.takeIf { it.isPrivateTask } //
                ?.let {
                    task.takeIf { it.userOwner == loggedUser } //
                        ?: throw TaskNotOwnedLoggedUserException(hashTask = hashTask)
                }
        }

    fun getTask(
        hashTask: String, //
        loggedUser: User, //
    ): TaskDetailDTO = getTaskByHashTask(
        hashTask = hashTask, //
        loggedUser = loggedUser, //
    ).let { TaskDetailDTO(task = it) }

    fun createTask(
        form: CreateTaskForm, //
        loggedUser: User, //
    ): Task = Task().apply {
        this.nmTask = form.nmTask
        this.dsTask = form.dsTask
        this.isPrivateTask = form.isPrivateTask
        this.dtDeadline = form.dtDeadline
        this.generateHashAndPersist(userOwner = loggedUser)

        TaskEvent.create(
            task = this, //
            userOperator = loggedUser, //
        ).persist()
    }

    private fun recreateTask(
        task: Task, //
        form: EditTaskForm, //
        loggedUser: User, //
    ): Task {
        disableTask(
            hashTask = task.hashTask, //
            loggedUser = loggedUser, //
        )

        return createTask(
            form = CreateTaskForm(
                nmTask = form.nmTask ?: task.nmTask, //
                dsTask = form.dsTask ?: task.dsTask, //
                isPrivateTask = form.isPrivateTask ?: task.isPrivateTask, //
                dtDeadline = form.dtDeadline ?: task.dtDeadline, //
            ), //
            loggedUser = loggedUser, //
        )
    }

    fun editTask(
        hashTask: String, //
        form: EditTaskForm, //
        loggedUser: User, //
    ): TaskDetailDTO = getTaskByHashTask(
        hashTask = hashTask, //
        loggedUser = loggedUser, //
    ).let { task ->
        task.dtDo?.let { throw TaskAlreadyCompletedException(hashTask = hashTask, dtDo = it) }

        TaskDetailDTO(
            task = if (form.nmTask != null || form.dsTask != null) {
            recreateTask(
                task = task, //
                form = form, //
                loggedUser = loggedUser, //
            )
        } else {
            var edited = false

            edited = updateIfChanged(
                current = { task.isPrivateTask },
                update = { task.isPrivateTask = it },
                newValue = form.isPrivateTask,
                onChange = {
                    form.isPrivateTask?.let { isPrivate ->
                        val event = if (isPrivate) TaskEvent.turnPrivate(task, loggedUser)
                        else TaskEvent.turnPublic(task, loggedUser)
                        event.persist()
                    }
                }) || edited

            edited = form.dtDeadline?.let { newDeadline ->
                task.dtDeadline.testDeadlineDateLessCurrentDate(date = newDeadline)
                updateIfChanged(
                    current = { task.dtDeadline }, //
                    update = { task.dtDeadline = it }, //
                    newValue = newDeadline, //
                )
            } == true || edited

            if (!edited) throw TaskInformationHasChangedException(hashTask = hashTask)
            task.also { it.persist() }
        })
    }

    @Transactional
    fun completeTask(
        hashTask: String, //
        loggedUser: User, //
    ): TaskDetailDTO = getTaskByHashTask(
        hashTask = hashTask, //
        loggedUser = loggedUser, //
    ).let { task ->

        task.dtDo?.let {
            throw TaskAlreadyCompletedException(
                hashTask = hashTask, //
                dtDo = it, //
            )
        }

        task.apply {
            this.dtDo = LocalDateTime.now()
            this.persist()
        }

        TaskEvent.complete(
            task = task, //
            userOperator = loggedUser, //
        ).persist()

        TaskDetailDTO(task = task)
    }

    @Transactional
    fun disableTask(
        hashTask: String, //
        loggedUser: User, //
    ): TaskDetailDTO = getTaskByHashTask(
        hashTask = hashTask, //
        loggedUser = loggedUser, //
    ).let { task ->
        task.takeIf { it.isActive }?.let {
            task.apply {
                this.isActive = false

                TaskEvent.disable(
                    task = task, //
                    userOperator = loggedUser, //
                ).persist()

                this.persist()
            }
        } ?: throw TaskAlreadyDisabledException(hashTask = hashTask)
        TaskDetailDTO(task = task)
    }
}