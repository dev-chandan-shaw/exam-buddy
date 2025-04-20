import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'app/environments/environment';
import { Observable } from 'rxjs';
import {
  ActiveTestQuestionStateUpdate,
  IActiveTest,
} from '../../models/active-test';

@Injectable({
  providedIn: 'root',
})
export class OngoingTestApiService {
  private _baseUrl = environment.api;
  private _http = inject(HttpClient);

  getOngoingTest(): Observable<IActiveTest> {
    return this._http.get<IActiveTest>(
      `${this._baseUrl}test-attempt/active?userId=1`
    );
  }

  startTest(testId: number): Observable<{ userId: number; testId: number }> {
    const body = { userId: 1, testId };
    return this._http.post<any>(
      `${this._baseUrl}test-attempt/start?userId=1`,
      body
    );
  }

  saveQuestionState(data: ActiveTestQuestionStateUpdate) {
    return this._http.put<any>(`${this._baseUrl}test-attempt/save`, data);
  }

  submitTest() {
    return this._http.put<any>(
      `${this._baseUrl}test-attempt/finish?userId=1`,
      {}
    );
  }
}
