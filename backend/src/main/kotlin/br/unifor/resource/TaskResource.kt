package br.unifor.resource

import br.unifor.dto.TaskDTO
import br.unifor.form.CreateTaskForm
import br.unifor.service.TaskService
import br.unifor.utils.getLoggedUser
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.*
import org.eclipse.microprofile.jwt.JsonWebToken

@Path("/task")
class TaskResource(
    @Inject var jwt: JsonWebToken, //
    @Inject var taskService: TaskService, //
) {
    @GET
    fun listTasks(@QueryParam("search") search: String? = null): List<TaskDTO> = taskService.listTasks(
        search = search, //
        loggedUser = jwt.getLoggedUser(), //
    )

    @GET
    @Path("/{hashTask}")
    fun getTaskByHashTask(@PathParam("hashTask") hashTask: String): Nothing =
        taskService.getTaskByHashTask(hashTask = hashTask)

    @POST
    @Transactional
    fun createTask(@Valid form: CreateTaskForm): TaskDTO = taskService.createTask(
        form = form, //
        loggedUser = jwt.getLoggedUser(), //
    )

    @PUT
    @Path("/{hashTask}")
    fun editTask(@PathParam("hashTask") hashTask: String): Nothing = taskService.editTask(hashTask = hashTask)

    @DELETE
    @Path("/{hashTask}")
    fun removeTask(@PathParam("hashTask") hashTask: String): Nothing = taskService.removeTask(hashTask = hashTask)
}

