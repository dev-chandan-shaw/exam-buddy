import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { CreateTestComponent } from '../create-test/create-test.component';
import { MatDialog } from '@angular/material/dialog';
import { ExamService } from '@core/services/exam.service';
import { TestService } from '@core/services/test.service';
import { IExam, ITest } from '@shared/models';

@Component({
  selector: 'app-dashboard',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    CommonModule,
    RouterModule,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  readonly dialog = inject(MatDialog);
  readonly exams = signal<IExam[]>([]);
  private _examService = inject(ExamService);
  private _testsService = inject(TestService);
  unpublishedTests = signal<ITest[]>([]);
  publishedTests = signal<ITest[]>([]);

  ngOnInit(): void {
    this._examService.getExams().subscribe((exams) => this.exams.set(exams));
    this._testsService.getAllTests().subscribe((tests: ITest[]) => {
      this.publishedTests.set(tests);
    });
  }

  openCreateTestDialog(): void {
    this.dialog.open(CreateTestComponent, {
      data: { exams: this.exams() },
    });
  }
}
