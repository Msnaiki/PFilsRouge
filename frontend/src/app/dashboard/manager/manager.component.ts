import { Component,  OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeDto } from '../../models-dto/employee.model';
import { ManagerService } from './manager.service';
import { EmployeeCreationDialogComponent } from './employee-creation-dialog/employee-creation-dialog.component';
import { UpdateEmployeeDialogComponent } from './update-employee-dialog/update-employee-dialog.component';
import { TaskDTO } from '../../models-dto/task.model';
import { TaskCreationDialogComponent } from './task-creation-dialog/task-creation-dialog.component';
import { TaskUpdateDialogComponent } from './task-update-dialog/task-update-dialog.component';
import { AuthService } from '../../authentication/auth.service';
import { UpdatePasswordComponent } from './update-password/update-password.component';


@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements OnInit {
  displayedColumns: string[] = ['username', 'name', 'bestQuality', 'managerId', 'actions'];
  dataSource: EmployeeDto[] = [];
  taskColumns: string[] = ['title', 'description', 'status', 'actions'];
  taskSource: TaskDTO[] = [];
  myTaskSource: TaskDTO[] = [];
  username:string;

  constructor(
    private managerService: ManagerService,
    public dialog: MatDialog,
    private authService: AuthService
  ) {
     this.username = authService.getUserNameFromToken();
     
  }

  ngOnInit() {
    this.loadEmployees();
    this.loadTasks();
    this.loadTasksByManager();
   
  }
  openPassUpdateDialog(): void {
    const dialogRef = this.dialog.open(UpdatePasswordComponent, {
      width: '250px',
      data: { newPassword: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      // You can handle the new password result here if needed
    });
  }

  loadEmployees() {
    this.managerService.getAllEmployees().subscribe({
      next: (data) => {
        this.dataSource = data;
      },
      error: (err) => console.error('Failed to load employees:', err)
    });
  }
  loadTasks() {
    this.managerService.getAllTasks().subscribe({
      next: (tasks) => {
        this.taskSource = tasks;
      },
      error: (err) => console.error('Failed to load tasks:', err)
    });
  }
  loadTasksByManager(): void {
    try {
      const managerId = this.authService.getUserIdFromToken(); // Get manager ID from token
      this.managerService.getTasksByManager(managerId).subscribe({
        next: (tasks) => {
          this.myTaskSource = tasks;  // Save the tasks to the local state
        },
        error: (error) => {
          console.error('Error loading tasks', error);
        }
      });
    } catch (error) {
      console.error('Error retrieving manager ID from token:', error);
    }
  }

  openCreateEmployeeDialog(): void {
    const dialogRef = this.dialog.open(EmployeeCreationDialogComponent, {
      width: '280px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.managerService.createEmployee(result).subscribe({
          next: (response) => {
            this.loadEmployees(); 
          },
          error: (error) => console.error('Failed to create employee:', error)
        });
      }
    });
  }
 

  updateEmployee(employee: EmployeeDto): void {
    const dialogRef = this.dialog.open(UpdateEmployeeDialogComponent, {
      width: '280px',
      data: employee  // Make sure 'employee' includes all necessary fields
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.managerService.updateEmployee(result).subscribe({
          next: (response) => {
            console.log('Update successful', response);
            this.loadEmployees(); // Reload employees to reflect changes
          },
          error: (error) => console.error('Update failed', error)
        });
      }
    });
  }
  deleteEmployee(employeeId: number): void {
    if (confirm('Are you sure you want to delete this employee?')) {
      this.managerService.deleteEmployee(employeeId).subscribe({
        next: () => {
          console.log('Employee deleted successfully');
          this.loadEmployees(); // Reload to update the list
        },
        error: (err) => console.error('Failed to delete employee:', err)
      });
    }
  }
  //tasks section
  
  openCreateTaskDialog(): void {
    const dialogRef = this.dialog.open(TaskCreationDialogComponent, {
      width: '280px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.managerService.createTask(result).subscribe({
          next: (response) => {
            console.log('Task created successfully:', response);
            this.loadTasks(); // Reload to show the new task
          },
          error: (error) => console.error('Failed to create task:', error)
        });
      }
    });
  }

  openUpdateTaskDialog(task: TaskDTO): void {
    const dialogRef = this.dialog.open(TaskUpdateDialogComponent, {
      width: '280px',
      data: task  // pass the current task data to the dialog
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadTasks(); // Reload tasks to reflect updates
      }
    });
  }
  deleteTask(taskId: number): void {
    if (confirm('Are you sure you want to delete this task?')) {
      this.managerService.deleteTask(taskId).subscribe({
        next: () => {
          console.log('Task deleted successfully');
          this.loadTasks(); // Reload to update the list after deletion
        },
        error: (err) => console.error('Failed to delete task:', err)
      });
    }
  }
  logout() {
    this.authService.logout();
  }
  

}
