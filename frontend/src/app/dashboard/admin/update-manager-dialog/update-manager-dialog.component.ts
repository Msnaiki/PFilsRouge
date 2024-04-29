import { Component,Inject  } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../admin.service';
@Component({
  selector: 'app-update-manager-dialog',
  templateUrl: './update-manager-dialog.component.html',
  styleUrl: './update-manager-dialog.component.css'
})
export class UpdateManagerDialogComponent {
  updateForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<UpdateManagerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  
  
  ) {
    this.updateForm = this.fb.group({
      managerId: [data.managerId, Validators.required],
      username: [data.username, Validators.required],
      name: [data.name, Validators.required],
      password: ['', Validators.required],
     
    });
  }
  onUpdate(): void {
    console.log("on upload called");
    if (this.updateForm.valid) {
      console.log("form valid")
      this.dialogRef.close(this.updateForm.value);
    }else{
      console.log("form not valid")
    }
  }
  onCancel(): void {
    this.dialogRef.close();
  }
 
}
