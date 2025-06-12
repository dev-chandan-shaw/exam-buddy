import { Routes } from '@angular/router';
import { activeTestGuard } from '@core/guards/active-test.guard';
import { authGuard } from '@core/guards/auth.guard';
import { guestGuard } from '@core/guards/guest.guard';
import { LayoutComponent } from '@shared/layout/layout.component';
export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'exam',
  },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
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
    ],
  },

  {
    path: 'active-test',
    canActivate: [authGuard, activeTestGuard],
    loadComponent: () =>
      import('./features/ongoing-test/ongoing-test.component').then(
        (m) => m.OngoingTestComponent
      ),
  },
  {
    path: 'test-analysis/:testId',
    canActivate: [authGuard],

    loadComponent: () =>
      import('./features/test-result/test-result.component').then(
        (m) => m.TestResultComponent
      ),
  },
  {
    path: 'test-solution/:testId',
    canActivate: [authGuard],

    loadComponent: () =>
      import('./features/test-solution/test-solution.component').then(
        (m) => m.TestSolutionComponent
      ),
  },
  {
    path: 'auth',
    canActivateChild: [guestGuard],
    loadChildren: () =>
      import('./features/auth/auth.routes').then((m) => m.AUTH_ROUTES),
  },
  {
    path: '**',
    redirectTo: 'exam',
  },
];
