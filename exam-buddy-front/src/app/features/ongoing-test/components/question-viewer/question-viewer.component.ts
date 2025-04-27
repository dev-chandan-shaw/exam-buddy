import {
  Component,
  effect,
  EventEmitter,
  inject,
  input,
  linkedSignal,
  OnChanges,
  OnDestroy,
  Output,
  signal,
  SimpleChanges,
} from '@angular/core';
import {
  ActiveTestQuestionState,
  QuestionStatus,
} from '../../models/active-test';
import { TimeFormatPipe } from '@core/pipes/time-format.pipe';
import { ActiveTestService } from '../../services/active-est.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-question-viewer',
  imports: [TimeFormatPipe, FormsModule, CommonModule],
  templateUrl: './question-viewer.component.html',
  styleUrl: './question-viewer.component.scss',
})
export class QuestionViewerComponent implements OnChanges, OnDestroy {
  private _activeTestService = inject(ActiveTestService);
  questionIndex = this._activeTestService.getCurrentQuestionIndex();
  questionState = this._activeTestService.getCurrentQuestionState();

  selectedOptionId = linkedSignal(() => {
    return this.questionState().selectedOptionId;
  });
  time = linkedSignal(() => {
    return this.questionState().timeTakenSeconds;
  });
  private _timer: any;
  constructor() {
    effect(() => {
      // Trigger effect when questionState changes
      const currentState = this.questionState();
      console.log('Question changed:', currentState);
      if (!currentState) return;
      // Reset timer
      clearInterval(this._timer);
      this._timer = setInterval(() => {
        this.questionState().timeTakenSeconds++;
      }, 1000);
    });
  }
  ngOnChanges(): void {
    // clearInterval(this._timer);
    // this._timer = setInterval(() => {
    //   this.questionState().timeTakenSeconds++;
    // }, 1000);
  }
  ngOnDestroy(): void {
    clearInterval(this._timer);
  }
  handleOptionChange(optionId: number) {
    this.selectedOptionId.set(optionId);
  }

  markForReview() {
    this.questionState().selectedOptionId = this.selectedOptionId();
    this._activeTestService.markForReview();
  }
  saveAndNext() {
    this.questionState().selectedOptionId = this.selectedOptionId();
    this._activeTestService.saveAndNext();
  }
  clearResponse() {
    this.selectedOptionId.set(0);
    this._activeTestService.clearResponse();
  }
}
// export class QuestionViewerComponent implements OnChanges {
//   @Output() nextQuestion = new EventEmitter<number>();
//   questionState = input.required<ActiveTestQuestionState>();
//   questionNumber = input.required<number>();
//   timer: any;
//   time = linkedSignal(() => {
//     return this.questionState().timeTakenSeconds;
//   });
//   selectedOptionId = linkedSignal(() => {
//     return this.questionState().selectedOptionId;
//   });

//   ngOnChanges(changes: SimpleChanges): void {
//     if (changes['questionState']) {
//       clearInterval(this.timer);
//       this.timer = setInterval(() => {
//         this.questionState().timeTakenSeconds++;
//       }, 1000);
//     }
//   }

//   markForReview() {
//     if (this.questionState().selectedOptionId !== 0) {
//       this.questionState().status = QuestionStatus.MARKED_AND_ANSWERED;
//     } else {
//       this.questionState().status = QuestionStatus.MARKED;
//     }
//     this.nextQuestion.emit(this.questionState().id);
//   }
//   saveAndNext() {
//     if (this.questionState().selectedOptionId !== 0) {
//       this.questionState().status = QuestionStatus.ANSWERED;
//     } else {
//       this.questionState().status = QuestionStatus.NOT_ANSWERED;
//     }
//     this.nextQuestion.emit(this.questionState().id);
//   }
//   clearResponse() {
//     this.questionState().selectedOptionId = 0;
//   }

//   handleOptionChange(optionId: number) {
//     this.questionState().selectedOptionId = optionId;
//   }
// }
