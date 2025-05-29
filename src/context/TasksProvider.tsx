import { useEffect, useState, type ReactNode } from "react";
import { api } from "../lib/axios";
import { TasksContext } from "./TasksContext";


interface Tasks {
    id: number,
    user_id: number,
    name: string,
    description: string,
    visibilite: string,
    limitDate: Date,
    completionDate: Date,
    create_at: Date,
    taskIsActive: boolean,
}

interface TaskInput {
    name: string;
    description: string;
    visibilite: string;
    limitDate: string;
}

interface TasksProviderProps {
    children: ReactNode
}

export function TasksProvider({ children }: TasksProviderProps) {
    const [tasks, setTasks] = useState<Tasks[]>([])

    async function fetchTasks(query: string = "") {
        try {
            const response = await api.get("/task", { params: { q: query } })

            console.log("Dados recebidos da API:", response.data);

            if (Array.isArray(response.data)) {
                setTasks(response.data)
            } else if (Array.isArray(response.data.tasks)) {
                setTasks(response.data.tasks)
            } else {
                console.error("Formato inesperado dos dados:", response.data);
                setTasks([])  // limpa para não quebrar o app
            }
        } catch (error) {
            console.error("Erro ao buscar tasks:", error);
            setTasks([])
        }
    }
    const CreateTask = async ({ name, description, visibilite, limitDate }: TaskInput): Promise<void> => {
        try {
            const response = await api.post("/task", { name, description, visibilite, limitDate })

            console.log("task Criada", response.data)


        } catch (error) {
            console.error(error);
        }
    }

    const DeleteTask = async (id: number) => {
        if (window.confirm('tem certeza que deseja deletar a Task')) {
            try {
                await api.delete(`/task/${id}`)
                setTasks(prev => prev.filter(task => task.id !== id))
            } catch (error) {
                console.error(error);
            }
        }
    }

    const viewTask = async (id: number) => {
        try {
            await api.get(`/tas/${id}`)
        } catch (error) {
            console.error(error)
        }
    }

    useEffect(() => {
        fetchTasks();
    }, [])

    const CheckTask = async (id: number): Promise<void> => {

        try {
            const currentTask = tasks.find(task => task.id === id);
            if (!currentTask) return;

            if (currentTask.taskIsActive === false) return;

            const updateTask = !currentTask.taskIsActive;

            const response = await api.patch(`/task/${id}`, {
                taskIsActive: updateTask
            });

            setTasks(prev =>
                prev.map(task =>
                    task.id === id ? { ...task, taskIsActive: updateTask } : task)
            )

            console.log(response.data)
        } catch (error) {
            console.error(error)
        }
    }

    return (
        <TasksContext.Provider value={{ tasks, fetchTasks, DeleteTask, viewTask, CheckTask, CreateTask }}>
            {children}
        </TasksContext.Provider>
    )
}