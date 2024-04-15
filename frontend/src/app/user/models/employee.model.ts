import { Manager } from "./manager.model";
import { Task } from "../../task/models/task.models";
import { User } from "./user.model";

export interface Employee extends User {
    employeeId: number;
    bestQuality: string;
    manager: Manager;
    tasks: Task[];
  }