import { Component,Inject, OnInit  } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ManagerService } from '../manager.service';
import {AuthService} from '../../../authentication/auth.service';

@Component({
  selector: 'app-employee-creation-dialog',
  templateUrl: './employee-creation-dialog.component.html',
  styleUrl: './employee-creation-dialog.component.css'
})
export class EmployeeCreationDialogComponent implements OnInit {
  employeeData = {
    username: '',
    name: '',
    password: '',
    bestQuality: '',
    manager: {
      managerId: 0
    }
  };

  constructor(
    public dialogRef: MatDialogRef<EmployeeCreationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private managerService: ManagerService,
    private authService: AuthService // Correctly inject AuthService here
  ) {}

  ngOnInit() {
   this.employeeData.manager.managerId = this.authService.getUserIdFromToken();
  }


  onCreateEmployee() {
    this.managerService.createEmployee(this.employeeData).subscribe({
      next: (response) => {
        this.dialogRef.close();
      },
      error: (error) => {
        console.error('Failed to create employee', error);
      }
    });
  }

}
