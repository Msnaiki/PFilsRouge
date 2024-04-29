import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ManagerService } from '../manager.service';
import { TaskDTO } from '../../../models-dto/task.model';
import {AuthService} from '../../../authentication/auth.service'
import { EmployeeDto } from '../../../models-dto/employee.model';

@Component({
  selector: 'app-task-creation-dialog',
  templateUrl: './task-creation-dialog.component.html',
  styleUrl: './task-creation-dialog.component.css'
})
export class TaskCreationDialogComponent {

  taskData: TaskDTO = {
    taskId: 0, // This could be set to undefined or removed if the backend auto-generates IDs
    title: '',
    description: '',
    status: 'Pending',
    assignedTo: {
      employeeId: 0, username: '', name: '', bestQuality: '',
      managerDto: { managerId: 0, username: '', name: '' }
    },
    assignedBy: { managerId: 0, username: '', name: '' }
  };
  employees: EmployeeDto[] = [];

  constructor(
    public dialogRef: MatDialogRef<TaskCreationDialogComponent>,
    private managerService: ManagerService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const managerId = this.authService.getUserIdFromToken();
    this.taskData.assignedBy = { managerId: managerId, username: 'managerUsername', name: 'Manager Name' };
    this.taskData.assignedTo.managerDto = { managerId: managerId, username: 'managerUsername', name: 'Manager Name' };
    
    this.loadEmployees();
    

  }

  loadEmployees() {
    this.managerService.getAllEmployees().subscribe({
      next: (data) => {
        this.employees = data;
      },
      error: (error) => {
        console.error('Failed to load employees', error);
      }
    });
  }

  onCreateTask() {
    if (this.taskData.assignedTo) {
        const taskPayload = {
            ...this.taskData,
            assignedTo: this.taskData.assignedTo
        };

        this.managerService.createTask(taskPayload).subscribe({
            next: (response) => {
                this.dialogRef.close(true); // Optionally indicate success
            },
            error: (error) => {
                console.error('Failed to create task', error);
                this.dialogRef.close(false); // Optionally indicate failure
            }
        });
    } else {
        console.error('No employee selected for the task');
    }
}
}


