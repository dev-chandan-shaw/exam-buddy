import { inject, Injectable, linkedSignal, signal } from '@angular/core';
import {
  ActiveTestQuestionState,
  ActiveTestQuestionStateUpdate,
  ActiveTestSection,
  IActiveTest,
  QuestionStatus,
} from '../models/active-test';
import { OngoingTestApiService } from './api/ongoing-test-api.service';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class ActiveTestService {
  private _router = inject(Router);
  private activeTest = signal<IActiveTest>({} as IActiveTest);
  private activeTestApiService = inject(OngoingTestApiService);
  private currentQuestionIndex = signal(0);
  private lastQuestionState = linkedSignal(
    () => this.currentSection()?.questions[0]
  );
  private currentSection = linkedSignal(
    () => this.activeTest()?.testSections[0]
  );
  private currentQuestionState = linkedSignal(
    () => this.currentSection()?.questions[0]
  );
  private lastSectionQuestionStates = linkedSignal(() => {
    const map = new Map<number, ActiveTestQuestionState>();
    this.activeTest()?.testSections.forEach((section) => {
      map.set(section.id, section.questions[0]);
    });
    return map;
  });

  setActiveTest(test: IActiveTest) {
    this.activeTest.set(test);
  }
  getActiveTest() {
    return this.activeTest;
  }

  setSelectedSection(newSection: ActiveTestSection) {
    const previousSection = this.currentSection();
    this.lastSectionQuestionStates.update((map) =>
      map.set(previousSection.id, this.currentQuestionState())
    );
    this.lastQuestionState.set(this.currentQuestionState());
    this.updateLastQuestionStatus();
    this.currentSection.set(newSection);
    this.currentQuestionState.set(
      this.lastSectionQuestionStates().get(newSection.id) ??
        newSection.questions[0]
    );
    this.currentQuestionIndex.set(
      this.currentSection().questions.indexOf(this.currentQuestionState())
    );
  }

  getSelectedSection() {
    return this.currentSection;
  }

  getCurrentQuestionState() {
    return this.currentQuestionState;
  }
  getCurrentQuestionIndex() {
    return this.currentQuestionIndex;
  }

  setCurrentQuestionState(questionState: ActiveTestQuestionState) {
    this.saveQuestionstate(questionState);
    this.lastSectionQuestionStates.update((map) =>
      map.set(this.currentSection().id, this.currentQuestionState())
    );
    this.lastQuestionState.set(this.currentQuestionState());
    this.updateLastQuestionStatus();
    this.currentQuestionState.set(questionState);
    this.currentQuestionIndex.set(
      this.currentSection().questions.indexOf(questionState)
    );
  }

  getLastQuestionState() {
    return this.lastQuestionState;
  }

  markForReview() {
    if (this.currentQuestionState().selectedOptionId !== 0) {
      this.currentQuestionState().status = QuestionStatus.MARKED_AND_ANSWERED;
    } else {
      this.currentQuestionState().status = QuestionStatus.MARKED;
    }
    this.nextQuestion();
  }

  saveAndNext() {
    if (this.currentQuestionState().selectedOptionId !== 0) {
      this.currentQuestionState().status = QuestionStatus.ANSWERED;
    } else {
      this.currentQuestionState().status = QuestionStatus.NOT_ANSWERED;
    }
    this.nextQuestion();
  }

  clearResponse() {
    if (this.currentQuestionState().selectedOptionId === 0) return;
    this.currentQuestionState().selectedOptionId = 0;
    if (this.currentQuestionState().status === QuestionStatus.ANSWERED) {
      this.currentQuestionState().status = QuestionStatus.NOT_ANSWERED;
    } else if (
      this.currentQuestionState().status === QuestionStatus.MARKED_AND_ANSWERED
    ) {
      this.currentQuestionState().status = QuestionStatus.MARKED;
    }
    this.saveQuestionstate(this.currentQuestionState());
  }

  submitTest() {
    this.saveQuestionstate(this.currentQuestionState());
    this.activeTestApiService.submitTest().subscribe((res) => {
      console.log(res);
      alert('test submitted');
      this._router.navigate(['exam']);
    });
  }

  getMarkedQuestionStatusCount() {
    let count = 0;
    this.currentSection().questions.forEach((question) => {
      if (question.status === QuestionStatus.MARKED) {
        count++;
      }
    });
    return count;
  }

  getAnsweredQuestionStatusCount() {
    let count = 0;
    this.currentSection().questions.forEach((question) => {
      if (question.status === QuestionStatus.ANSWERED) {
        count++;
      }
    });
    return count;
  }

  getNotAnsweredQuestionStatusCount() {
    let count = 0;
    this.currentSection().questions.forEach((question) => {
      if (question.status === QuestionStatus.NOT_ANSWERED) {
        count++;
      }
    });
    return count;
  }

  private updateLastQuestionStatus() {
    if (this.lastQuestionState().status === QuestionStatus.NOT_VISITED) {
      this.lastQuestionState().status = QuestionStatus.NOT_ANSWERED;
    }
    this.saveQuestionstate(this.lastQuestionState());
  }

  private nextQuestion() {
    this.saveQuestionstate(this.currentQuestionState());
    const nextIndex =
      (this.currentQuestionIndex() + 1) %
      this.currentSection().questions.length;
    this.lastQuestionState.set(this.currentQuestionState());
    this.currentQuestionIndex.set(nextIndex);
    this.currentQuestionState.set(this.currentSection().questions[nextIndex]);
  }

  private saveQuestionstate(questionState: ActiveTestQuestionState) {
    const data: ActiveTestQuestionStateUpdate = {
      testAttemptQuestionStateId: questionState.id,
      questionStatus: questionState.status,
      timeTakenSeconds: questionState.timeTakenSeconds,
      sectionTimeTakenSeconds: this.currentSection().timeTakenSeconds,
      selectedOptionId: questionState.selectedOptionId,
      testAttemptSectionId: this.currentSection().id,
      testAttemptId: this.activeTest().id,
    };
    this.activeTestApiService.saveQuestionState(data).subscribe((res) => {
      console.log(res);
    });
  }
}
