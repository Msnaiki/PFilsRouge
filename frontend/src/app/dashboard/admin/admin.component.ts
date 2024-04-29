import { Component } from '@angular/core';
import { AdminService } from './admin.service';
import { AuthService } from '../../authentication/auth.service';
import { Manager} from '../../models/manager.model';
import { ManagerDto} from '../../models-dto/manager.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient} from '@angular/common/http';
import { CreateManagerDialogComponent } from './create-manager-dialog/create-manager-dialog.component';
import { UpdateManagerDialogComponent } from './update-manager-dialog/update-manager-dialog.component';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {
  managerForm: FormGroup;
  updateForm: FormGroup;
  managers: ManagerDto[] = [];
  selectedManager: ManagerDto | undefined;
  showCreatePopup: boolean = false;
  showUpdatePopup: boolean = false;
  username:string;

  constructor(private adminService: AdminService,private authService: AuthService, private fb: FormBuilder,private http: HttpClient, private router: Router,private dialog: MatDialog ) {
    this.managerForm = this.fb.group({
      name: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.updateForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      name: ['', Validators.required],
      managerId: ['', Validators.required]
    });
    this.username =authService.getUserNameFromToken();
  }


  ngOnInit() {
    this.loadManagers();
  }

  openCreateManagerDialog(): void {
    const dialogRef = this.dialog.open(CreateManagerDialogComponent, {
      width: '250px', // or any other size
      data: {} // You can pass any required data here
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadManagers(); // Reload or refresh data if needed
    });
  }



 
  updateEmployee(manager: ManagerDto): void {
    const dialogRef = this.dialog.open(UpdateManagerDialogComponent, {
      width: '250px',
      data: manager  
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.adminService.updateManager(result).subscribe({
          next: (response) => {
            console.log('Update successful', response);
            this.loadManagers(); // Reload employees to reflect changes
          },
          error: (error) => console.error('Update failed', error)
        });
      }
    });
  }

  loadManagers(): void {
    this.adminService.getAllManagers().subscribe({
      next: (data) => this.managers = data,
      error: (err) => console.error('Failed to get managers', err)
    });
  }
  
  
  onSubmit(): void {
    if (this.updateForm.valid) {
      const manager: Manager = this.updateForm.value;
      this.adminService.updateManager(manager).subscribe({
        next: (updatedManager) => {
         
          alert('Update successful!');
        },
        error: (error) => {
          console.error('Error updating manager:', error);
          alert('Update failed. See console for details.');
        }
      });
    } else {
      alert('Please fill all fields correctly.');
    }
  }


  deleteManager(managerId: number): void {
    // Display confirmation dialog before deleting a manager
    if (confirm('Are you sure you want to delete this manager?')) {
      this.adminService.deleteManager(managerId).subscribe({
        next: () => {
          this.managers = this.managers.filter(manager => manager.managerId !== managerId);
        },
        error: (err) => console.error('Failed to delete manager', err)
      });
    } 
  }

  logout() {
    this.authService.logout();
  }
  
  
}
