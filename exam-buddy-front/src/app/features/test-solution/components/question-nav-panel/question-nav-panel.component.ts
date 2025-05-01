import { Component, inject, input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  TestSolutionQuestion,
  TestSolutionQuestionStatus,
} from '../../models/test-soution-question';
import { TestSolutionService } from '../../services/test-solution.service';
import { TestSolutionSection } from '../../models/test-solution-section';
@Component({
  selector: 'test-solution-question-nav-panel',
  imports: [CommonModule],
  templateUrl: './question-nav-panel.component.html',
  styleUrl: './question-nav-panel.component.scss',
})
export class QuestionNavPanelComponent {
  private _testSolutionService = inject(TestSolutionService);
  testSection = input<TestSolutionSection>();
  TesSolutionQuestionStatus = TestSolutionQuestionStatus;
  selectQuestionHandler(question: TestSolutionQuestion) {
    this._testSolutionService.setSelectedQuestion(question);
  }
}
