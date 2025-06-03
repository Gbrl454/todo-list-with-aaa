import { createContext } from "react";

interface Task {
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

interface TasksContextType {
  tasks: Task[];
  viewTaskData: Task | null;
  fetchTasks: (query?: string) => void;
  DeleteTask: (id: string) => Promise<void>;
  viewTask: (id: string) => Promise<void>;
  CheckTask: (id: string) => Promise<void>;
  CreateTask: (task: TaskInput) => Promise<void>;
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

