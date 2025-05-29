 
import { createContext } from "react";

interface Task {
    id: number,
    user_id: number,
    name: string,
    description: string,
    visibilite: string,
    limitDate: Date,
    completionDate: Date | null,
    create_at: Date | null,
    taskIsActive: boolean,
}

interface TaskInput {
  name: string;
  description: string;
  visibilite: string;
  limitDate: string;
}

interface TasksContextType {
  tasks: Task[];
  fetchTasks: (query?: string) => void;
  DeleteTask: (id:number) => Promise<void>
  viewTask: (id:number) => Promise<void>
  CheckTask: (id:number) => Promise<void>
  CreateTask: (task:TaskInput) => Promise<void>
}

export const TasksContext = createContext<TasksContextType>({
  tasks: [],
  fetchTasks: async () => {},
  DeleteTask: async () => {},
  viewTask: async () => {},
  CheckTask: async () => {},
  CreateTask: async () => {},
});

