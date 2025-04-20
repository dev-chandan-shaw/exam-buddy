import {
  Component,
  EventEmitter,
  inject,
  input,
  linkedSignal,
  Output,
} from '@angular/core';
import { ITestSection } from '@shared/models';
import {
  ActiveTestQuestionState,
  ActiveTestSection,
  QuestionStatus,
} from '../../models/active-test';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { ActiveTestService } from '../../services/active-est.service';
import { QuestionButtonComponent } from '../question-button/question-button.component';
import { currentQuestionClassPipe } from '@core/pipes/current-question-class.pipe';

@Component({
  selector: 'app-quesiton-nav-panel',
  imports: [
    MatButtonModule,
    CommonModule,
    QuestionButtonComponent,
    currentQuestionClassPipe,
  ],
  templateUrl: './quesiton-nav-panel.component.html',
  styleUrl: './quesiton-nav-panel.component.scss',
})
export class QuesitonNavPanelComponent {
  testSection = input<ActiveTestSection>();
  private _activeTestService = inject(ActiveTestService);
  QuestionStatus = QuestionStatus;
  currentQuestionState = this._activeTestService.getCurrentQuestionState();
  isCurrentQuestion(question: ActiveTestQuestionState) {
    console.log(this.currentQuestionState().id, question.id);

    return question.id === this.currentQuestionState().id;
  }

  selectQuestionHandler(question: ActiveTestQuestionState) {
    this._activeTestService.setCurrentQuestionState(question);
  }
  submitTest() {
    const result = window.confirm('Are you sure you want to submit the test?');
    if (!result) return;
    this._activeTestService.submitTest();
  }

  getNotVisitedQuestionCount(): number {
    return (
      this.testSection()?.questions.filter(
        (question) => question.status === QuestionStatus.NOT_VISITED
      ).length ?? 0
    );
  }

  getNotAnsweredQuestionCount(): number {
    return (
      this.testSection()?.questions.filter(
        (question) => question.status === QuestionStatus.NOT_ANSWERED
      ).length ?? 0
    );
  }

  getMarkedQuestionCount(): number {
    return (
      this.testSection()?.questions.filter(
        (question) => question.status === QuestionStatus.MARKED
      ).length ?? 0
    );
  }

  getAnsweredQuestionCount(): number {
    return (
      this.testSection()?.questions.filter(
        (question) => question.status === QuestionStatus.ANSWERED
      ).length ?? 0
    );
  }

  getMarkedAndAnsweredQuestionCount(): number {
    return (
      this.testSection()?.questions.filter(
        (question) => question.status === QuestionStatus.MARKED_AND_ANSWERED
      ).length ?? 0
    );
  }

  getQuestionCount(questionsStatus: QuestionStatus): number {
    return (
      this.testSection()?.questions.filter(
        (question) => question.status === questionsStatus
      ).length ?? 0
    );
  }

  getButtonLabel(questionStatus: QuestionStatus) {
    switch (questionStatus) {
      case QuestionStatus.NOT_VISITED:
        return 'Not Visited';
      case QuestionStatus.NOT_ANSWERED:
        return 'Not Answered';
      case QuestionStatus.MARKED:
        return 'Marked';
      case QuestionStatus.ANSWERED:
        return 'Answered';
      case QuestionStatus.MARKED_AND_ANSWERED:
        return 'Marked & Answered';
    }
  }
}
