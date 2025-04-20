import {
  Component,
  effect,
  inject,
  input,
  linkedSignal,
  OnDestroy,
  signal,
} from '@angular/core';
import { ITestSection } from '@shared/models';
import { QuestionViewerComponent } from '../question-viewer/question-viewer.component';
import { QuesitonNavPanelComponent } from '../quesiton-nav-panel/quesiton-nav-panel.component';
import { ActiveTestSection } from '../../models/active-test';
import { ActiveTestService } from '../../services/active-est.service';

@Component({
  selector: 'app-section-container',
  imports: [QuestionViewerComponent],
  templateUrl: './section-container.component.html',
  styleUrl: './section-container.component.scss',
})
export class SectionContainerComponent implements OnDestroy {
  private _activeTestService = inject(ActiveTestService);
  testSection = this._activeTestService.getSelectedSection();
  private _timer: any;

  constructor() {
    effect(() => {
      clearInterval(this._timer);
      const currentState = this.testSection();
      if (!currentState) return;
      this._timer = setInterval(() => {
        currentState.timeTakenSeconds++;
      }, 1000);
    });
  }

  ngOnDestroy(): void {
    clearInterval(this._timer);
  }
}
// export class SectionContainerComponent {
//   testSection = input<ActiveTestSection>();
//   currentQuesitonId = input.required<number>();
//   selectedQuestionId = linkedSignal(() => this.currentQuesitonId());
//   currentQuesitonState = linkedSignal(() => {
//     return this.testSection()?.questions.find(
//       (question) => question.id === this.selectedQuestionId()
//     );
//   });

//   constructor() {
//     effect(() => {
//       console.log(this.currentQuesitonId());
//     });
//   }

//   nextQuestion(questionId: number) {
//     let index = this.testSection()?.questions.findIndex(
//       (question) => question.id === questionId
//     );
//     console.log('previous index: ', index);

//     if (index === undefined) return;

//     const nextIndex = (index + 1) % (this.testSection()?.questions.length ?? 1);

//     console.log('next index: ', nextIndex);

//     this.selectedQuestionId.set(
//       this.testSection()?.questions[nextIndex].id ?? 0
//     );
//     console.log(this.currentQuesitonState());

//     alert(this.selectedQuestionId());
//   }

//   submitTest() {
//     console.log('submit test');
//   }
// }
