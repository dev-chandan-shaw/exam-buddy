import { ISubject } from '@shared/models';
import { TestSolutionQuestion } from './test-soution-question';

export interface TestSolutionSection {
  subject: ISubject;
  questions: TestSolutionQuestion[];
}
