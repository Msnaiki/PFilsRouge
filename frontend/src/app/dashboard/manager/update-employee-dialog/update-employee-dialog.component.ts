import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EmployeeDto } from '../../../models-dto/employee.model';

@Component({
  selector: 'app-update-employee-dialog',
  templateUrl: './update-employee-dialog.component.html',
  styleUrl: './update-employee-dialog.component.css'
})
export class UpdateEmployeeDialogComponent  {
  updateForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<UpdateEmployeeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EmployeeDto
  ) {
    this.updateForm = this.fb.group({
      employeeId: [data.employeeId, Validators.required],
      username: [data.username, Validators.required],
      name: [data.name, Validators.required],
      bestQuality: [data.bestQuality, Validators.required],
      managerId: [{value: data.managerDto?.managerId, disabled: true}, Validators.required]
    });
   
  }
  
  onUpdate(): void {
    if (this.updateForm.valid) {
      this.dialogRef.close(this.updateForm.value);
    }
  }
  onCancel(): void {
    this.dialogRef.close();
  }


}
