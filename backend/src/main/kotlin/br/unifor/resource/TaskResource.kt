package br.unifor.resource

import br.unifor.dto.TaskDTO
import br.unifor.dto.TaskDetailDTO
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
    fun getTask(@PathParam("hashTask") hashTask: String): TaskDetailDTO = taskService.getTask(
        hashTask = hashTask, //
        loggedUser = jwt.getLoggedUser(), //
    )

    @POST
    @Transactional
    fun createTask(@Valid form: CreateTaskForm): TaskDTO = taskService.createTask(
        form = form, //
        loggedUser = jwt.getLoggedUser(), //
    )

    @PUT
    @Path("/{hashTask}")
    fun editTask(@PathParam("hashTask") hashTask: String): TaskDetailDTO = taskService.editTask(
        hashTask = hashTask, //
        loggedUser = jwt.getLoggedUser(), //
    )

    @DELETE
    @Transactional
    @Path("/{hashTask}")
    fun disableTask(@PathParam("hashTask") hashTask: String): TaskDetailDTO = taskService.disableTask(
        hashTask = hashTask, //
        loggedUser = jwt.getLoggedUser(), //
    )
}

