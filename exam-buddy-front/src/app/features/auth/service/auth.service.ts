import { inject, Injectable, signal } from '@angular/core';
import { AuthApiService } from './auth.api.service';
import { ICreateUser, ILoginUser, IUser } from '../models/user';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _authApiService = inject(AuthApiService);
  private user = signal<IUser | null>(null);
  private router = inject(Router);

  constructor() {
    const user = localStorage.getItem('user');
    if (user) {
      this.user.set(JSON.parse(user));
    }
  }

  login(data: ILoginUser) {
    return this._authApiService.login(data).subscribe((res: IUser) => {
      this.user.set(res);
      console.log(res);
      localStorage.setItem('user', JSON.stringify(res));
      localStorage.setItem('token', res.accessToken);
      this.router.navigate(['/']);
    });
  }

  signup(data: ICreateUser) {
    return this._authApiService.signup(data).subscribe((res: IUser) => {
      this.user.set(res);
      console.log(res);
      localStorage.setItem('user', JSON.stringify(res));
      localStorage.setItem('token', res.accessToken);
      this.router.navigate(['/']);
    });
  }

  logout() {
    this.user.set(null);
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.router.navigate(['/auth/login']);
  }

  getUser() {
    return this.user;
  }
}
