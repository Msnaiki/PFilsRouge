import{Task} from './task.model';
import{Employee} from './employee.model';
export interface Manager {
    managerId?: number;
    username: string;
    name:string;
    password:string;
    tasksCreated?: Task[];
    employees?: Employee[];
  }