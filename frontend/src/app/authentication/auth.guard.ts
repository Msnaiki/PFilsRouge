import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
      const isLoggedIn = this.authService.isLoggedIn();

      if (isLoggedIn && (state.url === '/login' || state.url === '/')) {
        this.router.navigate(['/dashboard']);
        return false;
      } else if (!isLoggedIn && state.url === '/dashboard') {
        this.router.navigate(['/login']);
        return false;
      }

      return true;
    }
}