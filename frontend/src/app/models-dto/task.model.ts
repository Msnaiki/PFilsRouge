import { EmployeeDto } from "./employee.model";
import { ManagerDto } from "./manager.model";

export interface TaskDTO {
    taskId: number;
    title: string; 
    description:string;
    assignedBy:ManagerDto;
    assignedTo:EmployeeDto;
    status:string;
  }