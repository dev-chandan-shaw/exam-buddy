import { Routes } from '@angular/router';
import { AdminComponent } from './admin.component';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminComponent,
  },
  {
    path: 'create-exam',
    loadComponent: () =>
      import('./components/create-exam/create-exam.component').then(
        (m) => m.CreateExamComponent
      ),
  },
  {
    path: 'create-subject',
    loadComponent: () =>
      import('./components/create-subject/create-subject.component').then(
        (m) => m.CreateSubjectComponent
      ),
  },
  {
    path: 'create-test',
    loadComponent: () =>
      import('./components/create-test/create-test.component').then(
        (m) => m.CreateTestComponent
      ),
  },
];
