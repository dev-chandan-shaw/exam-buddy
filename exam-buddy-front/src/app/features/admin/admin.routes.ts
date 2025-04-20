import { Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: '',
        component: DashboardComponent, // default child route
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
      {
        path: 'test/:testId/draft',
        loadComponent: () =>
          import('./components/test-editor/test-editor.component').then(
            (m) => m.TestEditorComponent
          ),
      },
      {
        path: 'add-question',
        loadComponent: () =>
          import('./components/question-form/question-form.component').then(
            (m) => m.QuestionFormComponent
          ),
      },
    ],
  },
];
