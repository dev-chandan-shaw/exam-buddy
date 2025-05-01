import {
  computed,
  effect,
  Injectable,
  linkedSignal,
  Signal,
  signal,
} from '@angular/core';
import { environment } from 'app/environments/environment';
import { TestSolution } from '../models/test-solution';
import { TestSolutionSection } from '../models/test-solution-section';
import { TestSolutionQuestion } from '../models/test-soution-question';

@Injectable({
  providedIn: 'root',
})
export class TestSolutionService {
  private isReattemptEnabled = signal<boolean>(true);
  private testSolution = signal<TestSolution>({} as TestSolution);

  private selectedSection = linkedSignal(
    () => this.testSolution()?.sections?.[0]
  );
  private selectedQuestion = linkedSignal(
    () => this.selectedSection()?.questions?.[0]
  );
  private questionNumber = linkedSignal(() => {
    return (
      this.selectedSection()?.questions.indexOf(this.selectedQuestion()) + 1
    );
  });

  private reSelectedOptionIdMap = signal(new Map<number, number>());

  getSelectedSection(): Signal<TestSolutionSection> {
    return this.selectedSection;
  }

  get selectedQuestion$() {
    return this.selectedQuestion;
  }

  canReattempt() {
    return this.isReattemptEnabled;
  }

  getTestSolution(): Signal<TestSolution> {
    return this.testSolution;
  }

  getSelectedQuestion(): Signal<TestSolutionQuestion> {
    return this.selectedQuestion;
  }

  getQuestionNo() {
    return this.questionNumber;
  }

  setTestSolution(testSolution: TestSolution) {
    this.testSolution.set(testSolution);
    const map = new Map<number, number>();
    testSolution.sections.forEach((section) => {
      section.questions.forEach((q) => {
        map.set(q.question.id, 0);
      });
    });
    this.reSelectedOptionIdMap.set(map);
  }

  updateReselectedOptionId(optionId: number) {
    const map = new Map(this.reSelectedOptionIdMap());
    map.set(this.selectedQuestion().question.id, optionId);
    this.reSelectedOptionIdMap.set(map);
  }

  reIntializeReselectedOptionMap() {
    const map = new Map<number, number>();
    this.testSolution()?.sections.forEach((section) => {
      section.questions.forEach((q) => {
        map.set(q.question.id, 0);
      });
    });
    this.reSelectedOptionIdMap.set(map);
  }

  getReselectedOptionMap() {
    return this.reSelectedOptionIdMap;
  }

  setSelectedSection(section: TestSolutionSection) {
    this.selectedSection.set(section);
  }

  setSelectedQuestion(question: TestSolutionQuestion) {
    this.selectedQuestion.set(question);
  }

  setCanReattempt(canReattempt: boolean) {
    this.isReattemptEnabled.set(canReattempt);
  }
}
