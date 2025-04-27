import { ISubject } from '@shared/models';

export interface IActiveTest {
  id: number;
  startTime: string;
  completed: boolean;
  testId: number;
  testSections: ActiveTestSection[];
}

export interface ActiveTestSection {
  id: number;
  subject: ISubject;
  timeTakenSeconds: number;
  questions: ActiveTestQuestionState[];
}

export interface ActiveTestQuestionState {
  id: number;
  question: ActiveTestQuestion;
  selectedOptionId: number;
  timeTakenSeconds: number;
  status: QuestionStatus;
}

export interface ActiveTestQuestion {
  id: number;
  description: string;
  passage: string;
  options: OptionResponseDto[];
}

export interface OptionResponseDto {
  id: number;
  description: string;
}

export enum QuestionStatus {
  NOT_VISITED = 'NOT_VISITED',
  NOT_ANSWERED = 'NOT_ANSWERED',
  MARKED = 'MARKED',
  MARKED_AND_ANSWERED = 'MARKED_AND_ANSWERED',
  ANSWERED = 'ANSWERED',
}

export interface ActiveTestQuestionStateUpdate {
  testAttemptQuestionStateId: number;
  questionStatus: QuestionStatus;
  timeTakenSeconds: number;
  sectionTimeTakenSeconds: number;
  selectedOptionId: number;
  testAttemptSectionId: number;
  testAttemptId: number;
}
