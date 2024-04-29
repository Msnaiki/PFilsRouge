import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TaskDTO } from '../../models-dto/task.model';
import { EmployeeService } from './employee.service';
import { AuthService } from '../../authentication/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { UpdatePasswordEmpComponent } from './update-password-emp/update-password-emp.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.css'
})
export class EmployeeComponent implements OnInit {
  myTaskSource: TaskDTO[] = [];
  statusUpdateForm!: FormGroup;
  statusControl = new FormControl<string | null>(null, Validators.required);
  statuses: string[] = [
   "pending","progressing","reviewing"
  ];
  username:string;

  constructor(
    private authService: AuthService,
    private employeeService: EmployeeService,
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    public dialog: MatDialog,
  ) {
    this.statusUpdateForm = this.fb.group({
      taskId: [''],
      newStatus: ['']
    });
    this.username =authService.getUserNameFromToken();
  }
  ngOnInit(): void {
    this.loadTasksbyID();
  }
  openPassUpdateDialog(): void {
    const dialogRef = this.dialog.open(UpdatePasswordEmpComponent, {
      width: '250px',
      data: { newPassword: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      // handle the new password result here 
    });
  }
  loadTasksbyID(): void {
    try {
      const employeeId = this.authService.getUserIdFromToken(); 
      this.employeeService.getTasksByEmployee(employeeId).subscribe({
        next: (tasks) => {
          this.myTaskSource = tasks;  
        },
        error: (error) => {
          console.error('Error loading tasks', error);
        }
      });
    } catch (error) {
      console.error('Error retrieving employee ID from token:', error);
    }
    }

    updateTaskStatus(task: TaskDTO, newStatus: string): void {
      if (confirm(`Are you sure you want to change status to ${newStatus}?`)) {
        this.employeeService.updateTaskStatus(task.taskId, task.assignedTo.employeeId, newStatus).subscribe({
          next: () => {
            task.status = newStatus; // Assuming you have a status field in TaskDTO
            this.loadTasksbyID(); // Optionally reload tasks
          },
          error: (err) => console.error('Failed to update status', err)
        });
      }
    }
    logout() {
      const token = localStorage.getItem('token'); // Ensure this key matches the one used when storing the token
    
      if (token) {
        const headers = new HttpHeaders().set('Authorization', token);
        this.http.post('http://localhost:8080/auth/logout', {}, { headers: headers })
          .subscribe({
            next: (response) => {
              localStorage.removeItem('token'); 
              this.router.navigate(['/login']);
            },
            error: (error) => {
              localStorage.removeItem('token');
              console.error('Error logging out:', error);
            }
          });
      } else {
        localStorage.removeItem('token')
        this.router.navigate(['/login']);  // Redirect if no token is found
      }
    }
    
  }





