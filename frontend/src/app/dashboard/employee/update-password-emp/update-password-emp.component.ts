import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-update-password-emp',
  templateUrl: './update-password-emp.component.html',
  styleUrl: './update-password-emp.component.css'
})
export class UpdatePasswordEmpComponent {
  constructor(
    public dialog: MatDialogRef<UpdatePasswordEmpComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private employeeService: EmployeeService
  ) {}

  onNoClick(): void {
    this.dialog.close();
  }

  updatePassword(): void {
    this.employeeService.updatePassword(this.data.newPassword).subscribe({
      next: (response) => {
        this.dialog.close();
      },
      error: (error) => {
        console.error('Failed to update password', error);
      }
    });
  }
}
