import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Manager } from '../../models/manager.model';
import { ManagerDto } from '../../models-dto/manager.model';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private apiUrl = 'http://localhost:8080/admin'; // Adjust URL as necessary
  

  constructor(private http: HttpClient) { }

  createManager(manager: Manager): Observable<Manager> {
    return this.http.post<Manager>(`${this.apiUrl}/createManager`, manager);
  }

  getAllManagers(): Observable<ManagerDto[]> {
    return this.http.get<ManagerDto[]>(`${this.apiUrl}/getAllManagers`);
  }

  findManagerByUsername(username: string): Observable<ManagerDto> {
    return this.http.get<ManagerDto>(`${this.apiUrl}/findManager?username=${username}`);
  }

  updateManager(manager: Manager): Observable<ManagerDto> {
    return this.http.put<ManagerDto>(`${this.apiUrl}/updateManager`, manager);
  }

  deleteManager(managerId: number): Observable<string> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text' as 'json'  
    };
  
    return this.http.delete<string>(`${this.apiUrl}/deleteManager?managerId=${managerId}`, httpOptions);
  }
  
  
}
