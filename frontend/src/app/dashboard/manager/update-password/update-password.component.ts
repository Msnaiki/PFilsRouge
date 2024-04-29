import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ManagerService } from '../manager.service';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrl: './update-password.component.css'
})
export class UpdatePasswordComponent {
  constructor(
    public dialog: MatDialogRef<UpdatePasswordComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private managerService: ManagerService
  ) {}

  onNoClick(): void {
    this.dialog.close();
  }

  updatePassword(): void {
    this.managerService.updatePassword(this.data.newPassword).subscribe({
      next: (response) => {
        console.log('Password successfully updated');
        this.dialog.close();
      },
      error: (error) => {
        console.error('Failed to update password', error);
      }
    });
  }
}
