import { Routes } from '@angular/router';
import { ExamComponent } from './exam.component';
import { TestComponent } from './test/test.component';

export const EXAM_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./exam.component').then((m) => m.ExamComponent),
  },
  {
    path: ':examId/tests',
    loadComponent: () =>
      import('./test/test.component').then((m) => m.TestComponent),
  },
];
