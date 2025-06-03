/* eslint-disable @typescript-eslint/no-explicit-any */
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
    hashTask: string;
    userId: string;
    name: string;
    description: string;
    visibilite: string;
    limitDate: Date | null;
    completionDate: Date | null;
    create_at: string;
    is_active: boolean;
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

// Função que converte a resposta da API para o formato esperado no frontend
function formatTask(task: any): Tasks {
    return {
        hashTask: task.hashTask,
        userId: task.userId || "",
        name: task.nmTask,
        description: task.dsTask || "",
        visibilite: task.isPrivateTask ? "private" : "public",
        limitDate: task.dtDeadline ? new Date(task.dtDeadline) : null,
        completionDate: task.dtDo ? new Date(task.dtDo) : null,
        create_at: task.create_at || "",
        is_active: !task.wasDone,
    };
}

function getAccessToken(): string | null {
    const tokenJson = Cookies.get("X-TOKEN-TODO");
    const tokenObj = tokenJson ? JSON.parse(tokenJson) : null;
    return tokenObj?.accessToken ?? null;
}

export function TasksProvider({ children }: TasksProviderProps) {
    const [tasks, setTasks] = useState<Tasks[]>([]);
    const [viewTaskData, setViewTaskData] = useState<Tasks | null>(null);

    const fetchTasks = useCallback(async (query: string = "") => {
        try {
            const tokenJson = Cookies.get("X-TOKEN-TODO");
            const tokenObj = tokenJson ? JSON.parse(tokenJson) : null;
            const accessToken = tokenObj?.accessToken;

            if (!accessToken) {
                console.warn("Tentativa de buscar tarefas sem autenticação.");
                setTasks([]);
                return;
            }

            const response = await api.get("/task", {
                params: { q: query },
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            });

            console.log("Dados recebidos da API:", response.data);

            const data = Array.isArray(response.data)
                ? response.data
                : Array.isArray(response.data?.tasks)
                    ? response.data.tasks
                    : [];

            const tasksFormatted = data.map(formatTask);
            setTasks(tasksFormatted);
        } catch (error) {
            console.error("Erro ao buscar tasks:", error);
            setTasks([]);
        }
    }, []);

    const CreateTask = useCallback(async (task: TaskInput): Promise<void> => {
        const { nmTask, dsTask, isPrivateTask = true, dtDeadline } = task;

        if (!nmTask.trim()) {
            alert("O nome da tarefa é obrigatório.");
            return;
        }

        if (!dsTask.trim()) {
            alert("A descrição da tarefa é obrigatória.");
            return;
        }

        try {
            let formattedDeadline = dtDeadline;

            if (dtDeadline) {
                if (dtDeadline.length === 10) {
                    formattedDeadline = dtDeadline + "T23:59:59";
                } else if (dtDeadline.length === 16) {
                    formattedDeadline = dtDeadline + ":00";
                }
            }

            const payload = {
                nmTask,
                dsTask,
                isPrivateTask,
                dtDeadline: formattedDeadline,
            };

            console.log("Dados enviados para criar tarefa:", payload);

            // Pega o token do cookie
            const tokenJson = Cookies.get("X-TOKEN-TODO");
            const tokenObj = tokenJson ? JSON.parse(tokenJson) : null;
            const accessToken = tokenObj?.accessToken;

            if (!accessToken) {
                alert("Você precisa estar logado para criar uma tarefa.");
                return;
            }

            const response = await api.post("/task", payload, {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${accessToken}`,
                },
            });

            console.log("Resposta da API:", response.data);

            if (!response.data?.id) {
                throw new Error("Resposta inesperada da API ao criar tarefa.");
            }

            const newTask = formatTask(response.data);
            setTasks((prev) => [...prev, newTask]);

            console.log("Tarefa criada com sucesso:", newTask);
        } catch (error: any) {
            console.error("Erro ao criar tarefa:", {
                message: error.message,
                status: error.response?.status,
                data: error.response?.data,
                headers: error.response?.headers,
            });
        }
    }, [setTasks]);

    const DeleteTask = useCallback(async (hashTask: string) => {
        if (!window.confirm("Tem certeza que deseja deletar a Task?")) return;

        try {
            const tokenJson = Cookies.get("X-TOKEN-TODO");
            const tokenObj = tokenJson ? JSON.parse(tokenJson) : null;
            const accessToken = tokenObj?.accessToken;

            if (!accessToken) {
                console.warn("Tentativa de visualizar tarefa sem autenticação.");
                return;
            }

            const encodedHashTask = encodeURIComponent(hashTask);
            const response = await api.delete(`/task/${encodedHashTask}`, {
                withCredentials: true,
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            });
            setTasks((prev) => prev.filter((task) => task.hashTask !== hashTask));
            console.log(response)
        } catch (error) {
            console.error("Erro ao deletar tarefa:", error);
        }
    }, []);

    const viewTask = useCallback(
        async (hashTask: string) => {
            try {
                // Pega o token dentro do callback
                const tokenJson = Cookies.get("X-TOKEN-TODO");
                const tokenObj = tokenJson ? JSON.parse(tokenJson) : null;
                const accessToken = tokenObj?.accessToken;
                
                if (!accessToken) {
                    console.warn("Tentativa de visualizar tarefa sem autenticação.");
                    return;
                }

                const encodedHashTask = encodeURIComponent(hashTask);
                const response = await api.get(`/task/${encodedHashTask}`, {
                    withCredentials: true,
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                });

                const taskFormatted = formatTask(response.data);
                setViewTaskData(taskFormatted);
                console.log(taskFormatted);
            } catch (error) {
                console.error("Erro ao visualizar tarefa:", error);
            }
        },
        [setViewTaskData] // só depende da função de setar estado
    );

    const CheckTask = useCallback(async (hashTask: string): Promise<void> => {
        try {
            const accessToken = getAccessToken();
            if (!accessToken) {
                console.warn("Tentativa de atualizar tarefa sem autenticação.");
                return;
            }


            const currentTask = tasks.find((task) => task.hashTask === hashTask);
            if (!currentTask) return;

            const updateTask = !currentTask.is_active;
            const encodedHashTask = encodeURIComponent(hashTask);
            const response = await api.put(
                `/task/${encodedHashTask}/do`,
                { is_active: updateTask },
                {
                    withCredentials: true,
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                    },
                }
            );

            if (response.status === 200) {
                setTasks((prev) =>
                    prev.map((task) =>
                        task.hashTask === hashTask
                            ? { ...task, is_active: updateTask }
                            : task
                    )
                );
                console.log("Status da tarefa atualizado:", response.data);
            }
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
