import { Routes } from '@angular/router';
export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    loadComponent: () =>
      import('./features/exam/exam.component').then((m) => m.ExamComponent),
  },
  {
    path: 'exam',
    loadChildren: () =>
      import('./features/exam/exam.routes').then((m) => m.EXAM_ROUTES),
  },
  {
    path: 'admin',
    loadChildren: () =>
      import('./features/admin/admin.routes').then((m) => m.ADMIN_ROUTES),
  },
  {
    path: 'active-test',
    loadComponent: () =>
      import('./features/ongoing-test/ongoing-test.component').then(
        (m) => m.OngoingTestComponent
      ),
  },
  {
    path: '**',
    redirectTo: 'exam',
    // Note: Cannot use canActivate here either
  },
];
