import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'app/environments/environment';
import { TestSolution } from '../models/test-solution';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TestSolutionApiService {
  private _baseUrl = environment.api;
  private _http = inject(HttpClient);

  getTestSolutionByTestAttemptId(
    testAttempId: number
  ): Observable<TestSolution> {
    return this._http.get<TestSolution>(
      `${this._baseUrl}test-solution/by-test-attempt?testAttemptId=${testAttempId}`
    );
  }

  getTestSolutionByTestId(testId: number): Observable<TestSolution> {
    return this._http.get<TestSolution>(
      `${this._baseUrl}test-solution/by-test?testId=${testId}`
    );
  }
}
