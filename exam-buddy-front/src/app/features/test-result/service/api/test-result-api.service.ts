import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'app/environments/environment';
import { Observable } from 'rxjs';
import { TestResult } from '../../models/test-result';

@Injectable({
  providedIn: 'root',
})
export class TestResultApiService {
  private baseUrl = environment.api;
  private _http = inject(HttpClient);

  getTestResult(id: number): Observable<TestResult> {
    return this._http.get<TestResult>(
      `${this.baseUrl}test-analysis?testAttemptId=${id}`
    );
  }
}
