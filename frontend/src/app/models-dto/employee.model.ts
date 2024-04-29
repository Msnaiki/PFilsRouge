import { ManagerDto } from "./manager.model";

export interface EmployeeDto {
    employeeId: number;
    username: string; 
    name:string;
    managerDto:ManagerDto;
    bestQuality:string;
  }