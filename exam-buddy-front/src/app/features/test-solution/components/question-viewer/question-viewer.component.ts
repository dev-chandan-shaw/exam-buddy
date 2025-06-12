import { CommonModule } from '@angular/common';
import {
  Component,
  computed,
  effect,
  inject,
  linkedSignal,
  OnChanges,
  signal,
  SimpleChanges,
} from '@angular/core';
import { TestSolutionService } from '../../services/test-solution.service';
import { TimeFormatPipe } from '@core/pipes/time-format.pipe';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { IQuestionOption } from '@shared/models';
import { TestSolutionQuestionStatus } from '../../models/test-soution-question';
import { MatDividerModule } from '@angular/material/divider';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@Component({
  selector: 'test-solution-question-viewer',
  imports: [
    CommonModule,
    TimeFormatPipe,
    MatCheckboxModule,
    MatIconModule,
    FormsModule,
    MatDividerModule,
    MatTabsModule,
    MatSlideToggleModule,
  ],
  templateUrl: './question-viewer.component.html',
  styleUrl: './question-viewer.component.scss',
})
export class QuestionViewerComponent {
  private _testSolutionService = inject(TestSolutionService);
  questionState = this._testSolutionService.getSelectedQuestion();
  questionNumber = this._testSolutionService.getQuestionNo();
  reselecteOptionIdMap = this._testSolutionService.getReselectedOptionMap();
  isReattemptEnabled = true;
  reSelectedOptionId = linkedSignal(() => {
    return this.reselecteOptionIdMap().get(this.questionState().question.id);
  });
  isReattempted = computed(() => this.reSelectedOptionId() !== 0);
  isViewSolutionEnabled = signal(false);

  toggleReattempt() {
    this.isReattemptEnabled = !this.isReattemptEnabled;
    this.isViewSolutionEnabled.set(false);
    this._testSolutionService.reIntializeReselectedOptionMap();
  }

  toggleViewSolution() {
    this.isViewSolutionEnabled.set(!this.isViewSolutionEnabled());
    const correctOption = this.questionState().question.options.find(
      (option) => option.correct
    );
    if (correctOption) {
      this._testSolutionService.updateReselectedOptionId(correctOption.id);
    }
  }

  showViewSolution() {
    return (
      this.isViewSolutionEnabled() ||
      !this.isReattemptEnabled ||
      this.isReattempted()
    );
  }
  selectOption(optionId: number) {
    if (!this.isReattemptEnabled) return;
    if (this.isReattempted()) return;
    this._testSolutionService.updateReselectedOptionId(optionId);
  }

  isCorrectOption(option: IQuestionOption): boolean {
    return option.correct && (!this.isReattemptEnabled || this.isReattempted());
  }

  isWrongOption(option: IQuestionOption): boolean {
    if (!option.correct) {
      if (this.isReattemptEnabled && this.isReattempted()) {
        return this.reSelectedOptionId() === option.id;
      }
      if (!this.isReattemptEnabled) {
        return this.questionState().selectedOptionId === option.id;
      }
    }
    return false;
  }

  getOptionIcon(option: IQuestionOption): string {
    if (this.isCorrectOption(option)) return 'check';
    if (this.isWrongOption(option)) return 'replay';

    //no option icon for which user has not selected during reattempt
    // and is not correct or wrong
    const selected = this.reSelectedOptionId();
    if (
      !this.isReattemptEnabled ||
      (this.isReattempted() && selected !== option.id)
    ) {
      return '';
    }
    return 'radio_button_unchecked';
  }

  getQuestionStatusClass() {
    if (this.isCorrectAnswer()) return 'bg-green-600 text-white';
    if (this.isWrongAnswer()) return 'bg-red-600 text-white';
    return 'bg-gray-400 text-white';
  }

  getQuestionStatus() {
    if (this.isCorrectAnswer()) return 'correct';
    if (this.isWrongAnswer()) return 'incorrect';
    return 'skipped';
  }

  isCorrectAnswer() {
    return this.questionState().status === TestSolutionQuestionStatus.CORRECT;
  }

  isWrongAnswer() {
    return this.questionState().status === TestSolutionQuestionStatus.INCORRECT;
  }

  isSkippedAnswer() {
    return (
      this.questionState().status === TestSolutionQuestionStatus.UNATTEMPTED
    );
  }

  getAccuracy() {
    return (
      (this.questionState().question.questionStats.totalCorrect * 100) /
      this.questionState().question.questionStats.totalAttempts
    ).toFixed(0);
  }

  prevQuestion() {
    this._testSolutionService.prevQuestion();
  }

  nextQuestion() {
    this._testSolutionService.nextQuestion();
  }

  saveQuestion() {
    this._testSolutionService.saveQuestion();
  }

  unsaveQuestion() {
    this._testSolutionService.unsaveQuestion();
  }
}
