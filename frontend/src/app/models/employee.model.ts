import { Manager } from "./manager.model";
import { Task } from "./task.model";

export interface Employee {
    employeeId: number;
    username: string;
    name: string;
    password: string;
    bestQuality:string;
    manager: Manager;
    tasks: Task[];

  }