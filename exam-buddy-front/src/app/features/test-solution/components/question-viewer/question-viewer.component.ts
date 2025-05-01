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

@Component({
  selector: 'test-solution-question-viewer',
  imports: [
    CommonModule,
    TimeFormatPipe,
    MatCheckboxModule,
    MatIconModule,
    FormsModule,
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

  toggleReattempt() {
    this.isReattemptEnabled = !this.isReattemptEnabled;
    this._testSolutionService.reIntializeReselectedOptionMap();
  }
  selectOption(optionId: number) {
    if (this.isReattempted()) return;
    this._testSolutionService.updateReselectedOptionId(optionId);
  }

  isCorrect(option: IQuestionOption): boolean {
    return option.correct && (!this.isReattemptEnabled || this.isReattempted());
  }

  isWrong(option: IQuestionOption): boolean {
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
    if (this.isCorrect(option)) return 'check';
    if (this.isWrong(option)) return 'replay';

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
}
