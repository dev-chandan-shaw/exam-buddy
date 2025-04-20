import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ITest } from '@shared/models';
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

  getTestByPublish(isPublished: boolean) {
    return this.http.get<ITest[]>(
      this.baseUrl + `tests/test?isPublished=${isPublished}`
    );
  }

  getAllTestsByExamId(id: number) {
    return this.http.get<ITest[]>(this.baseUrl + `tests/test?examId=${id}`);
  }

  getAllTests() {
    return this.http.get<ITest[]>(this.baseUrl + 'tests');
  }

  getTestById(id: number) {
    return this.http.get<ITest>(this.baseUrl + `tests/test/${id}`);
  }
}
