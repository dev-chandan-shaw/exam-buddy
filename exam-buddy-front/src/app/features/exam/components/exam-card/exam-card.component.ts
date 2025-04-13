import { Component, input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { IExam } from '../../models';
@Component({
  selector: 'app-exam-card',
  imports: [MatCardModule],
  templateUrl: './exam-card.component.html',
  styleUrl: './exam-card.component.scss',
})
export class ExamCardComponent {
  exam = input.required<IExam>();
}
