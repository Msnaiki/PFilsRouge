import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Manager } from '../models/manager.model'; // Update with actual model path

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private adminUrl = 'http://localhost:8080/admin'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  createManager(manager: Manager): Observable<Manager> {
    return this.http.post<Manager>(`${this.adminUrl}/createManager`, manager);
  }

  getAllManagers(): Observable<Manager[]> {
    return this.http.get<Manager[]>(`${this.adminUrl}/getAllManagers`);
  }

  findManagerByUsername(username: string): Observable<Manager> {
    return this.http.get<Manager>(`${this.adminUrl}/findManager`, { params: { username } });
  }

  updateManager(manager: Manager): Observable<Manager> {
    return this.http.put<Manager>(`${this.adminUrl}/updateManager`, manager);
  }

  deleteManager(managerId: number): Observable<any> {
    return this.http.delete<any>(`${this.adminUrl}/deleteManager`, { params: { managerId } });
  }
}