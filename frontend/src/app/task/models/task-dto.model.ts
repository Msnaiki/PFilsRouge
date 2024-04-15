import { ManagerDto } from "../../user/models/ManagerDto .model";
import { EmployeeDto } from "../../user/models/EmployeeDto .model";


export interface TaskDTO {
  taskId: number;
  title: string;
  description: string;
  status: string;
  assignedBy: ManagerDto;  // Full ManagerDTO object
  assignedTo: EmployeeDto; // Full EmployeeDTO object
}