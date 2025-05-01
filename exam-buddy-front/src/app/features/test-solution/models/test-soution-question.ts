import { IQuestion } from '@shared/models';

export interface TestSolutionQuestion {
  question: IQuestion;
  timeTakenSeconds: number;
  selectedOptionId: number;
  status: TestSolutionQuestionStatus;
  isQuestionSaved: boolean;
}

export enum TestSolutionQuestionStatus {
  CORRECT = 'CORRECT',
  INCORRECT = 'INCORRECT',
  NOT_ATTEMPTED = 'NOT_ATTEMPTED',
}
