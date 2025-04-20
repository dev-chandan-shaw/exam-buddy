import { CommonModule } from '@angular/common';
import { Component, effect, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { ExamCardComponent } from '../exam-card/exam-card.component';
import { ExamService } from '@core/services/exam.service';
import { IExam } from '@shared/models';

@Component({
  selector: 'app-exam-list',
  imports: [
    ExamCardComponent,
    MatIconModule,
    MatButtonModule,
    CommonModule,
    RouterModule,
  ],
  templateUrl: './exam-list.component.html',
  styleUrl: './exam-list.component.scss',
})
export class ExamListComponent {
  exams = signal<IExam[]>([]);
  private _examService = inject(ExamService);
  constructor() {
    effect(() => console.log(this.exams()));
  }
  ngOnInit(): void {
    this._examService.getExams().subscribe((exams) => this.exams.set(exams));
  }
}
