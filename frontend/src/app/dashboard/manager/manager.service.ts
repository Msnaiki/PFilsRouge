// src/app/manager/manager.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployeeDto } from '../../models-dto/employee.model';
import { TaskDTO } from '../../models-dto/task.model'; // Make sure to have a TaskDTO model

@Injectable({
  providedIn: 'root'
})
export class ManagerService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  // Employee-related methods
  createEmployee(employeeData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/manager/createEmployee`, employeeData);
  }

  getAllEmployees(): Observable<EmployeeDto[]> {
    return this.http.get<EmployeeDto[]>(`${this.apiUrl}/manager/allEmployees`);
  }

  updateEmployee(employeeData: any): Observable<any> {
    return this.http.patch(`${this.apiUrl}/manager/updateEmployee`, employeeData);
  }

  deleteEmployee(employeeId: number): Observable<any> {
    const params = new HttpParams().set('employeeId', employeeId.toString());
    return this.http.delete(`${this.apiUrl}/manager/deleteEmployee`, { params, responseType: 'text' });
  }

  // Task-related methods
  getAllTasks(): Observable<TaskDTO[]> {
    return this.http.get<TaskDTO[]>(`${this.apiUrl}/task/getAllTasks`);
  }

  createTask(taskData: TaskDTO): Observable<TaskDTO> {
    return this.http.post<TaskDTO>(`${this.apiUrl}/task/create`, taskData);
  }

  updateTask(taskData: TaskDTO): Observable<TaskDTO> {
    return this.http.put<TaskDTO>(`${this.apiUrl}/task/updateTask`, taskData);
  }

  deleteTask(taskId: number): Observable<any> {
    const params = new HttpParams().set('taskId', taskId.toString());
    return this.http.delete(`${this.apiUrl}/task/removeTask`, { params, responseType: 'text' });
  }
  getTasksByManager(managerId: number): Observable<TaskDTO[]> {
   
    const params = new HttpParams().set('managerId', managerId.toString());
    return this.http.get<TaskDTO[]>(`${this.apiUrl}/task/em/getByManager`, { params });
  }

  updatePassword(newPassword: string): Observable<any> {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('No token found in local storage for authorization');
    }

    const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    });

    // setting the newPassword as a query parameter
    return this.http.put(`${this.apiUrl}/auth/manager/update-password`, {}, {
        headers: headers,
        params: { newPassword: encodeURIComponent(newPassword) },
        responseType: 'text' as 'json'
    });
}
}
