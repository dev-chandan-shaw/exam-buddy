import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { IExam } from '@shared/models';
import { environment } from 'app/environments/environment';

@Injectable({ providedIn: 'root' })
export class ExamService {
  private _apiUrl = environment.api;
  private _http = inject(HttpClient);

  getExams() {
    return this._http.get<IExam[]>(this._apiUrl + 'exams');
  }
}
