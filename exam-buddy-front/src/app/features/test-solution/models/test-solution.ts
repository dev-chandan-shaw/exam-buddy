import { TestSolutionSection } from './test-solution-section';

export interface TestSolution {
  testAttemptId: number;
  testId: number;
  testName: string;
  sections: TestSolutionSection[];
}
