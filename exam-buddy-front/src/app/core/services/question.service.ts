import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'app/environments/environment';
import { CreateQuestion } from 'app/features/admin/models/question';

@Injectable({ providedIn: 'root' })
export class QuestionService {
  private _baseUrl = environment.api;
  private _http = inject(HttpClient);

  addQuestion(request: CreateQuestion) {
    return this._http.post(this._baseUrl + 'questions', request);
  }
}
