import { Component,OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../authentication/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  userRole: string = '';
  constructor(private http: HttpClient, private router: Router,private authService: AuthService) {}

  ngOnInit() {
    this.userRole = this.authService.getUserRole();
  }

  logout() {
  const token = localStorage.getItem('token'); // Ensure this key matches the one used when storing the token

  if (token) {
    const headers = new HttpHeaders().set('Authorization', token);
    this.http.post('http://localhost:8080/auth/logout', {}, { headers: headers })
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
