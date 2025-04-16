import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'app/environments/environment';
import { CreateTestRequest } from 'app/features/admin/models/create-test';

@Injectable({
  providedIn: 'root',
})
export class TestService {
  readonly baseUrl = environment.api;
  readonly http = inject(HttpClient);

  createTest(request: CreateTestRequest) {
    return this.http.post(this.baseUrl + 'tests', request);
  }
}
