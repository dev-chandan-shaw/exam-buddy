import { Component, computed, inject, input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  TestSolutionQuestion,
  TestSolutionQuestionStatus,
} from '../../models/test-soution-question';
import { TestSolutionService } from '../../services/test-solution.service';
import { TestSolutionSection } from '../../models/test-solution-section';
import { QuestionStatusButtonComponent } from '../question-status-button/question-status-button.component';
import { AuthService } from 'app/features/auth/service/auth.service';
@Component({
  selector: 'test-solution-question-nav-panel',
  imports: [CommonModule, QuestionStatusButtonComponent],
  templateUrl: './question-nav-panel.component.html',
  styleUrl: './question-nav-panel.component.scss',
})
export class QuestionNavPanelComponent {
  private _testSolutionService = inject(TestSolutionService);
  private _authService = inject(AuthService);
  testSection = input<TestSolutionSection>();
  selectedQuestion = this._testSolutionService.getSelectedQuestion();
  user = computed(this._authService.getUser());

  TesSolutionQuestionStatus = TestSolutionQuestionStatus;
  selectQuestionHandler(question: TestSolutionQuestion) {
    this._testSolutionService.setSelectedQuestion(question);
  }
  isSelectedQuestion(question: TestSolutionQuestion) {
    console.log(this.selectedQuestion()?.question.id, question.question.id);

    return this.selectedQuestion()?.question.id === question.question.id;
  }

  getQuestionCount(status: TestSolutionQuestionStatus): number {
    return (
      this.testSection()?.questions.filter(
        (question) => question.status === status
      ).length ?? 0
    );
  }
}
