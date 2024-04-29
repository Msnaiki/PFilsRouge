import { Employee } from "./employee.model";
import { Manager } from "./manager.model";

export interface Task {
    taskId: number;
    title: string;
    description: string;
    assignedBy: Manager;
    assignedTo:Employee
    status:string;
  }