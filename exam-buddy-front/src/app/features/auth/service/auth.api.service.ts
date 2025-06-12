import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'app/environments/environment';
import { ICreateUser, ILoginUser, IUser } from '../models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthApiService {
  private _httpClient = inject(HttpClient);
  private _baseUrl = environment.api;
  login(data: ILoginUser): Observable<IUser> {
    return this._httpClient.post<IUser>(`${this._baseUrl}auth/sign-in`, data);
  }

  signup(data: ICreateUser): Observable<IUser> {
    return this._httpClient.post<IUser>(`${this._baseUrl}auth/sign-up`, data);
  }
}
