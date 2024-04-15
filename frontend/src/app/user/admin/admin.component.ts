import { Component } from '@angular/core';
import { AdminService } from './admin-service.service';
import { Manager } from '../models/manager.model';
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent {

  managers: Manager[] = [];
  selectedManager: Manager | null = null;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadAllManagers();
  }

  loadAllManagers(): void {
    this.adminService.getAllManagers().subscribe(
      (data) => this.managers = data,
      (error) => console.error(error)
    );
}

}
