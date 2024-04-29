import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TaskDTO } from '../../../models-dto/task.model';
import { ManagerService } from '../manager.service';

@Component({
  selector: 'app-task-update-dialog',
  templateUrl: './task-update-dialog.component.html',
  styleUrl: './task-update-dialog.component.css'
})
export class TaskUpdateDialogComponent {
  updateForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<TaskUpdateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: TaskDTO,
    private managerService: ManagerService
  ) {
    this.updateForm = this.fb.group({
      title: [data.title, Validators.required],
      description: [data.description, Validators.required],
      status: [data.status, Validators.required]  // Assuming you use status now
    });
  }

  ngOnInit(): void {
  }

  onUpdate(): void {
    if (this.updateForm.valid) {
      const updatedTask: TaskDTO = {
        ...this.data, // spread the original data
        ...this.updateForm.value // overwrite with updated form values
      };

      this.managerService.updateTask(updatedTask).subscribe({
        next: (response) => {
          this.dialogRef.close(true);
        },
        error: (error) => {
          console.error('Failed to update task', error);
          this.dialogRef.close(false);
        }
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }

}
