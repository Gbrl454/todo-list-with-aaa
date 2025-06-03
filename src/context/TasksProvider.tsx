import {
    useEffect,
    useState,
    type ReactNode,
    useCallback,
} from "react";
import { api } from "../lib/axios";
import { TasksContext } from "./TasksContext";
import Cookies from "js-cookie";

interface Tasks {
    id: string;
    user_id: string;
    name: string;
    description: string;
    visibilite: string; // Mantido como está, caso a API use esse nome
    limitDate: Date | null;
    completionDate: Date | null;
    create_at: string;
    taskIsActive: boolean;
}

interface TaskInput {
    nmTask: string;
    dsTask: string;
    isPrivateTask: boolean;
    dtDeadline: string;
}

interface TasksProviderProps {
    children: ReactNode;
}

function formatTaskDates(task: any): Tasks {
    return {
        ...task,
        limitDate: task.limitDate ? new Date(task.limitDate) : null,
        completionDate: task.completionDate ? new Date(task.completionDate) : null,
    };
}

export function TasksProvider({ children }: TasksProviderProps) {
    const [tasks, setTasks] = useState<Tasks[]>([]);
    const [viewTaskData, setViewTaskData] = useState<Tasks | null>(null);

    const fetchTasks = useCallback(async (query: string = "") => {
        try {
            const response = await api.get("/task", { params: { q: query } });

            console.log("Dados recebidos da API:", response.data);

            const data = Array.isArray(response.data)
                ? response.data
                : Array.isArray(response.data?.tasks)
                    ? response.data.tasks
                    : [];

            const tasksFormatted = data.map(formatTaskDates);
            setTasks(tasksFormatted);
        } catch (error) {
            const token = Cookies.get("X-TOKEN-TODO");
            if (!token) {
                console.warn("Tentativa de buscar tarefas sem autenticação.");
                return;
            }
            console.error("Erro ao buscar tasks:", error);
            setTasks([]);
        }
    }, []);

    const CreateTask = useCallback(async (task: TaskInput): Promise<void> => {
    const { nmTask, dsTask, isPrivateTask, dtDeadline } = task;

    if (!nmTask.trim()) {
        alert("O nome da tarefa é obrigatório.");
        return;
    }

    try {
        // Supondo que dtDeadline seja uma string "yyyy-MM-dd"
        // Transformar para "yyyy-MM-ddT00:00:00"
        let formattedDeadline = dtDeadline;
        if (dtDeadline && dtDeadline.length === 10) { // formato esperado "yyyy-MM-dd"
            formattedDeadline = dtDeadline + "T00:00:00";
        }

        const payload = { nmTask, dsTask, isPrivateTask, dtDeadline: formattedDeadline };
        console.log('Dados enviados para criar tarefa:', payload);

        const response = await api.post("/task", payload, {
            headers: {
                "Content-Type": "application/json",
            },
        });

        console.log("Resposta da API:", response.data);

        if (!response.data?.id) {
            throw new Error("Resposta inesperada da API ao criar tarefa.");
        }

        const newTask = formatTaskDates(response.data);
        setTasks((prev) => [...prev, newTask]);

        console.log("Tarefa criada com sucesso:", newTask);
    } catch (error: any) {
        console.error("Erro ao criar tarefa:", error.response?.data || error.message || error);
    }
}, [setTasks, formatTaskDates]);

    const DeleteTask = useCallback(async (id: string) => {
        if (!window.confirm("Tem certeza que deseja deletar a Task?")) return;

        try {
            await api.delete(`/task/${id}`);
            setTasks((prev) => prev.filter((task) => task.id !== id));
        } catch (error) {
            console.error("Erro ao deletar tarefa:", error);
        }
    }, []);

    const viewTask = useCallback(async (id: string) => {
        try {
            const response = await api.get(`/task/${id}`);
            const taskFormatted = formatTaskDates(response.data);
            setViewTaskData(taskFormatted);
            console.log(taskFormatted);
        } catch (error) {
            console.error("Erro ao visualizar tarefa:", error);
        }
    }, []);

    const CheckTask = useCallback(async (id: string): Promise<void> => {
        try {
            const currentTask = tasks.find((task) => task.id === id);
            if (!currentTask) return;

            const updateTask = !currentTask.taskIsActive;

            const response = await api.put(`/task/${id}`, {
                taskIsActive: updateTask,
            });

            setTasks((prev) =>
                prev.map((task) =>
                    task.id === id ? { ...task, taskIsActive: updateTask } : task
                )
            );

            console.log("Status da tarefa atualizado:", response.data);
        } catch (error) {
            console.error("Erro ao atualizar status da tarefa:", error);
        }
    }, [tasks]);

    useEffect(() => {
        fetchTasks();
    }, [fetchTasks]);

    return (
        <TasksContext.Provider
            value={{
                tasks,
                fetchTasks,
                DeleteTask,
                viewTask,
                CheckTask,
                CreateTask,
                viewTaskData,
            }}
        >
            {children}
        </TasksContext.Provider>
    );
}
