import { Injectable } from '@angular/core';
import { environment } from 'app/environments/environment';

@Injectable({ providedIn: 'root' })
export class SubtopicService {
  private _baseUrl = environment.api;
  getSubtopicsBy() {}
}
