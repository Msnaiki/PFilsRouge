import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { AuthService } from './auth.service';

describe('AuthGuard', () => {
  let router: Router;
  let authService: AuthService;
  let authGuard: AuthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      providers: [
        AuthGuard,
        { provide: AuthService, useValue: { isLoggedIn: () => false } }
      ]
    });

    router = TestBed.inject(Router);
    authService = TestBed.inject(AuthService);
    authGuard = TestBed.inject(AuthGuard);
  });

  it('should redirect an unauthenticated user to the login page', () => {
    spyOn(authService, 'isLoggedIn').and.returnValue(false);
    spyOn(router, 'navigate');

    const mockActivatedRouteSnapshot = {} as ActivatedRouteSnapshot;
    const mockRouterStateSnapshot = {} as RouterStateSnapshot;

    const result = authGuard.canActivate(mockActivatedRouteSnapshot, mockRouterStateSnapshot);
    expect(result).toBeFalse();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should allow an authenticated user to access the route', () => {
    spyOn(authService, 'isLoggedIn').and.returnValue(true);

    const mockActivatedRouteSnapshot = {} as ActivatedRouteSnapshot;
    const mockRouterStateSnapshot = {} as RouterStateSnapshot;

    const result = authGuard.canActivate(mockActivatedRouteSnapshot, mockRouterStateSnapshot);
    expect(result).toBeTrue();
  });
});
