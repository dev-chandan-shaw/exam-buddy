import { Component, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { CreateTestComponent } from './components/create-test/create-test.component';
import { IExam } from '../exam/models';
import { ExamService } from '@core/services/exam.service';
import { QuestionFormComponent } from './components/question-form/question-form.component';

@Component({
  selector: 'app-admin',
  imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    QuestionFormComponent,
  ],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss',
})
export class AdminComponent implements OnInit {
  readonly dialog = inject(MatDialog);
  readonly exams = signal<IExam[]>([]);
  readonly examService = inject(ExamService);

  ngOnInit(): void {
    this.examService.getExams().subscribe((exams) => this.exams.set(exams));
  }

  openCreateTestDialog(): void {
    this.dialog.open(CreateTestComponent, {
      data: { exams: this.exams() },
    });
  }
}
