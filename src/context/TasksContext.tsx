 
import { createContext } from "react";

interface Task {
    id: string,
    user_id: string,
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
  viewTaskData: Task | null;
  fetchTasks: (query?: string) => void;
  DeleteTask: (id:string) => Promise<void>
  viewTask: (id:string) => Promise<void>
  CheckTask: (id:string) => Promise<void>
  CreateTask: (task:TaskInput) => Promise<void>
}

export const TasksContext = createContext<TasksContextType>({
  tasks: [],
  viewTaskData: null,
  fetchTasks: async () => {},
  DeleteTask: async () => {},
  viewTask: async () => {},
  CheckTask: async () => {},
  CreateTask: async () => {},
});

