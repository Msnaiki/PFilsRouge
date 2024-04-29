import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button'; 
import { MatCardModule } from '@angular/material/card';
import { EmployeeCreationDialogComponent } from './manager/employee-creation-dialog/employee-creation-dialog.component';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table'; 
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UpdateEmployeeDialogComponent } from './manager/update-employee-dialog/update-employee-dialog.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TaskCreationDialogComponent } from './manager/task-creation-dialog/task-creation-dialog.component';
import { MatSelectModule } from '@angular/material/select';
import { TaskUpdateDialogComponent } from './manager/task-update-dialog/task-update-dialog.component';
import { MatTabsModule } from '@angular/material/tabs'
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from "@angular/material/icon";
import { CreateManagerDialogComponent } from './admin/create-manager-dialog/create-manager-dialog.component';
import { UpdateManagerDialogComponent } from './admin/update-manager-dialog/update-manager-dialog.component'; 
import { MatOptionModule } from '@angular/material/core';
import { UpdatePasswordComponent } from './manager/update-password/update-password.component';
import { UpdatePasswordEmpComponent } from './employee/update-password-emp/update-password-emp.component';



@NgModule({
  declarations: [
    EmployeeCreationDialogComponent,
    UpdateEmployeeDialogComponent,
    TaskCreationDialogComponent,
    TaskUpdateDialogComponent,
    CreateManagerDialogComponent,
    UpdateManagerDialogComponent,
    UpdatePasswordComponent,
    UpdatePasswordEmpComponent
  ],
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    FormsModule,
    ReactiveFormsModule,
    MatTableModule,
    BrowserAnimationsModule,
    MatSelectModule,
    MatTabsModule,
    MatToolbarModule,
    MatIconModule,
    MatOptionModule
    
  ],
  exports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatToolbarModule,
    MatIconModule,
    MatOptionModule,
    MatSelectModule
  ]
})
export class MaterialModule {}
