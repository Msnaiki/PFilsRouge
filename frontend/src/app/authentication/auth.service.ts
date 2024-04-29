import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8080/auth/login';
  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: Object,private router: Router) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.loginUrl, { username, password });
  }

  isLoggedIn(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      let token = localStorage.getItem('token');
      return !!token;
    }
    return false;
  }
  getUserRole(): string {
    const token = localStorage.getItem('token');
    if (!token) {
      // Handle the case where the token is not available
      console.error('No token found!');
      return '';
    }


    try {
      const decodedToken = this.decodeToken(token);
      return decodedToken.role || ''; // Ensure the role exists or return an empty string
    } catch (error) {
      // Handle errors in case of a malformed token
      console.error('Failed to decode token:', error);
      return '';
    }
  }



  private decodeToken(token: string): any {
    // Safely decode the JWT using the jwt-decode library
    return jwtDecode(token);
  }
  getUserIdFromToken(): number {
    const token = localStorage.getItem('token');
    if (token) { // Check if token is not null
      const decoded = jwtDecode<{ userId: number }>(token);
      return decoded.userId;
    } else {
      // Handle the case where the token is not present in localStorage
      // For example, throw an error or return a default value
      throw new Error('No token found in local storage');
    }
  }
  getUserNameFromToken(): string {
    const token = localStorage.getItem('token');
    if (token) { 
      const decoded = jwtDecode<{ sub: string }>(token);
      return decoded.sub;
    } else {
      
      throw new Error('No token found in local storage');
    }
  }
  

  logout() {
    const token = localStorage.getItem('token'); // Ensure this key matches the one used when storing the token
  
    if (token) {
      const headers = new HttpHeaders().set('Authorization', token);
      this.http.post('http://localhost:8080/auth/logout',  { headers: headers,responseType: 'text' })
        .subscribe({
          next: (response) => {
            localStorage.removeItem('token'); 
            this.router.navigate(['/login']);
          },
          error: (error) => {
            localStorage.removeItem('token');
            console.error('Error logging out:', error);
          }
        });
    } else {
      localStorage.removeItem('token');
      this.router.navigate(['/login']);  // Redirect if no token is found
    }
  }
  
 
  
}

