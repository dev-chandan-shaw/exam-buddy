import { Routes } from '@angular/router';
import { ExamCardComponent } from './components/exam-card/exam-card.component';
import { ExamComponent } from './exam.component';
import { ExamListComponent } from './components/exam-list/exam-list.component';

export const EXAM_ROUTES: Routes = [
  {
    path: '',
    component: ExamComponent,
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./components/exam-list/exam-list.component').then(
            (m) => m.ExamListComponent
          ),
      },
      {
        path: ':examId/test',
        loadComponent: () =>
          import('./test/test.component').then((m) => m.TestComponent),
      },
    ],
  },
];
