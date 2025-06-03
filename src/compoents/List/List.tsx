import { CaretRight, Trash } from "phosphor-react";
import { Item, ListContainer, ListPage } from "./styles";
import { useContext, useEffect } from "react";
import { TasksContext } from "../../context/TasksContext";
import * as Dialog from '@radix-ui/react-dialog';
import { CreateTask } from "../modal/registermodal";
import { useNavigate } from "react-router-dom";

export function List() {
    const { tasks, DeleteTask, CheckTask, fetchTasks, viewTask } = useContext(TasksContext);
    const navigate = useNavigate();

    useEffect(() => {
        fetchTasks();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    return (
        <ListPage>
            <ListContainer>
                <div className="Cabeçalho">
                    <h2>Tarefas criadas {tasks.length}</h2>
                    <span>Concluídas {tasks.filter(t => !t.is_active).length}</span>
                </div>

                {tasks.map(task => {

                    return (
                        <Item key={task.hashTask}>
                            <input
                                type="checkbox"
                                id={`task-${task.hashTask}`}
                                checked={task.is_active}
                                onChange={() => CheckTask(task.hashTask)}
                                style={{ display: 'none' }}
                            />
                            <label
                                htmlFor={`task-${task.hashTask}`}
                                className={`checkbox ${task.is_active ? 'checkbox-unchecked' : 'checkbox-checked'}`}
                            />
                            <p
                                className="paragraph"
                                style={{
                                    textDecoration: task.is_active ? 'none' : 'line-through',
                                    color: task.is_active ? '#a9a9a9' : '#6b6b6b', // cores fixas, pois a interpolação estava errada
                                }}
                            >
                                {task.name}
                            </p>
                            <button
                                onClick={async () => {
                                    await viewTask(task.hashTask);
                                    navigate(`/task/${task.hashTask}`);
                                }}
                                aria-label={`Ver detalhes da tarefa ${task.name}`}
                            >
                                <CaretRight size={20} color="#a9a9a9" />
                            </button>
                            <button
                                onClick={() => DeleteTask(task.hashTask)}
                                aria-label={`Deletar tarefa ${task.name}`}
                            >
                                <Trash size={20} color="#a9a9a9" />
                            </button>
                        </Item>
                    );
                })}
            </ListContainer>

            <Dialog.Root>
                <Dialog.Trigger asChild>
                    <button className="CreateTaskButton">
                        <h2>Criar Task</h2>
                    </button>
                </Dialog.Trigger>
                <CreateTask onSuccess={fetchTasks} />
            </Dialog.Root>
        </ListPage>
    );
}
