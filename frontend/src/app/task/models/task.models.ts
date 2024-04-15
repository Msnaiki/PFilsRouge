import { Manager } from "../../user/models/manager.model";
import { Employee } from "../../user/models/employee.model";


export interface Task {
  taskId: number;
  title: string;
  description: string;
  status: string;
  assignedBy: Manager; 
  assignedTo: Employee; 
}