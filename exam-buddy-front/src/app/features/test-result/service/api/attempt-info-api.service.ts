import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'app/environments/environment';
import { Observable } from 'rxjs';
import { IAttemptInfo } from '../../models/attempt-info';

@Injectable({
  providedIn: 'root',
})
export class AttemptInfoApiService {
  private baseUrl = environment.api;
  private _http = inject(HttpClient);

  getAttemptInfo(testId: number): Observable<IAttemptInfo[]> {
    return this._http.get<IAttemptInfo[]>(
      `${this.baseUrl}attemptInfo?testId=${testId}`
    );
  }
}
