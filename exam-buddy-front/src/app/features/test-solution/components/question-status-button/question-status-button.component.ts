import { Component, input, Input } from '@angular/core';
import { TestSolutionQuestionStatus } from '../../models/test-soution-question';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-question-status-button',
  imports: [MatIconModule, CommonModule],
  templateUrl: './question-status-button.component.html',
  styleUrl: './question-status-button.component.scss',
})
export class QuestionStatusButtonComponent {
  status = input.required<TestSolutionQuestionStatus>();
  isSaved = input<boolean>(false);
  height = input<number>(30);
  width = input<number>(45);
  styleClass = input<string>('');
  getClass() {
    switch (this.status()) {
      case TestSolutionQuestionStatus.CORRECT:
        return 'correct rounded-t-3xl';
      case TestSolutionQuestionStatus.INCORRECT:
        return 'incorrect rounded-b-3xl';
      case TestSolutionQuestionStatus.UNATTEMPTED:
        return 'bg-gray-100 border border-black';
      default:
        return '';
    }
  }
}
