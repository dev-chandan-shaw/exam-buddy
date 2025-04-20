import { Component, input, linkedSignal } from '@angular/core';
import { QuestionStatus } from '../../models/active-test';
import { QuestionStatusClassPipe } from '@core/pipes/question-status-class.pipe';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-question-button',
  imports: [QuestionStatusClassPipe, CommonModule],
  templateUrl: './question-button.component.html',
  styleUrl: './question-button.component.scss',
})
export class QuestionButtonComponent {
  value = input.required<number>();
  status = input.required<QuestionStatus>();
  height = input(20);
  width = input(30);
  styleClass = input('');
  QuestionStatus = QuestionStatus;
  showCheckmark = linkedSignal(
    () => this.status() === QuestionStatus.MARKED_AND_ANSWERED
  );
}
