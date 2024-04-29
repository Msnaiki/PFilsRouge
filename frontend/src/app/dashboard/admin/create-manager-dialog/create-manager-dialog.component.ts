import { Component,Inject, OnInit  } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AdminService } from '../admin.service';
@Component({
  selector: 'app-create-manager-dialog',
  templateUrl: './create-manager-dialog.component.html',
  styleUrl: './create-manager-dialog.component.css'
})
export class CreateManagerDialogComponent {
  managerData ={
    username: '',
    name: '',
    password: '',

  }

  constructor(
    public dialogRef: MatDialogRef<CreateManagerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private adminService: AdminService,
   
  ) {}
  ngOnInit() {

   }
  onCreateManager() {
    this.adminService.createManager(this.managerData).subscribe({
      next: (response) => {
        this.dialogRef.close();
      },
      error: (error) => {
        console.error('Failed to create manager', error);
      }
    });
  }
}
