import { CaretRight, Trash } from "phosphor-react";
import { Item, ListContainer, ListPage } from "./styles";
import { useContext } from "react";
import { TasksContext } from "../../context/TasksContext";
import * as Dialog from '@radix-ui/react-dialog';
import { CreateTask } from "../modal/registermodal";
import { useNavigate } from "react-router-dom";


export function List() {
    const { tasks, DeleteTask, CheckTask, fetchTasks, viewTask } = useContext(TasksContext);
    const navigate = useNavigate()

    return (
        <ListPage>
            <ListContainer>
                <div className="Cabeçalho">
                    <h2>Tarefas criadas {tasks.length}</h2>
                    <span>Concluídas {tasks.length}</span>
                </div>


                {tasks.map(task => {
                    //const checked = task.taskIsActive === true;

                    const VisibleTask = task.visibilite === 'public';
                    if (!VisibleTask) return null;

                    return (
                        <Item key={task.id}>
                            <input type="checkbox"
                                id={`task-${task.id}`}
                                checked={task.taskIsActive}
                                onChange={() => CheckTask(task.id)}
                                style={{ display: 'none' }} />
                            <label
                                htmlFor={`task-${task.id}`}
                                className={`checkbox ${task.taskIsActive ? 'checkbox-unchecked' : 'checkbox-checked'}`}
                            />
                            <p className="paragraph" style={{ textDecoration: task.taskIsActive ? 'none' : 'line-through', color: task.taskIsActive ? 'color: ${props =>props.theme["gray-300"}' : '${props =>props.theme["gray-100"}' }}>
                                {task.name}
                            </p>
                            <button onClick={async() => {viewTask(task.id); navigate(`/task/${task.id}`);} }>
                                <CaretRight size={20} color="#a9a9a9" />
                            </button>
                            <button onClick={() => DeleteTask(task.id)}>
                                <Trash size={20} color="#a9a9a9" />
                            </button>
                        </Item>
                    )
                })}
            </ListContainer>

            <Dialog.Root>
                <Dialog.Trigger asChild>
                    <button className="CreateTaskButton">
                        <h2>Criar Task</h2>
                    </button>
                </Dialog.Trigger>
                        <CreateTask
                            onSuccess={() => {
                                fetchTasks();
                            }}
                        />
            </Dialog.Root>
        </ListPage >
    )
}