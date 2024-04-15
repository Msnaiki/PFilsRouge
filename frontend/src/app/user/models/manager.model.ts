import { Task } from "../../task/models/task.models";
import { Employee } from "./employee.model";
import { User } from "./user.model";
export interface Manager extends User {
    managerId: number;
    tasksCreated: Task[]; 
    employees: Employee[]; 
  }