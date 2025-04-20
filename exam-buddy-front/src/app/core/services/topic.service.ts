import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from 'app/environments/environment';

@Injectable({ providedIn: 'root' })
export class TopicService {
  private _baseUrl = environment.api;
  private _http = inject(HttpClient);
  getTopicsBySubject(subjectId: number) {
    this._http.get(this._baseUrl + `subjects/${subjectId}/topics`);
  }
}
