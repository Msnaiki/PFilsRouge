import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployeeDto } from '../../models-dto/employee.model';
import { TaskDTO } from '../../models-dto/task.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = 'http://localhost:8080';


  constructor(private http: HttpClient) { }

  getTasksByEmployee(employeeId: number): Observable<TaskDTO[]> {
    const params = new HttpParams().set('employeeId', employeeId.toString());
    return this.http.get<TaskDTO[]>(`${this.apiUrl}/task/em/getByEmployee`, {  params });
  }


  updateTaskStatus(taskId: number, employeeId: number, newStatus: string): Observable<any> {
    const params = new HttpParams()
      .set('taskId', taskId.toString())
      .set('employeeId', employeeId.toString())
      .set('newStatus', encodeURIComponent(newStatus));
    return this.http.put(`${this.apiUrl}/task/em/updateStatus`, {}, { params });
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

    // Correctly setting the newPassword as a query parameter
    return this.http.put(`${this.apiUrl}/auth/em/update-password`, {}, {
        headers: headers,
        params: { newPassword: encodeURIComponent(newPassword) },
        responseType: 'text' as 'json'
    });
}
}
