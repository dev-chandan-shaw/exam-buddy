import { Routes } from '@angular/router';
export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'exam',
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
    path: 'test-analysis/:testId',
    loadComponent: () =>
      import('./features/test-result/test-result.component').then(
        (m) => m.TestResultComponent
      ),
  },
  // {
  //   path: '**',
  //   redirectTo: 'exam',
  // },
];
