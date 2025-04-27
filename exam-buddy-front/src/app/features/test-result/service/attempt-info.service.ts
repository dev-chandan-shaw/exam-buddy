import { inject, Injectable, signal } from '@angular/core';
import { IAttemptInfo } from '../models/attempt-info';
import { AttemptInfoApiService } from './api/attempt-info-api.service';

@Injectable({
  providedIn: 'root',
})
export class AttemptInfoService {}
