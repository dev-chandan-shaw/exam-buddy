import { TestSolutionSection } from './test-solution-section';

export interface TestSolution {
  testAttemptId: number;
  testName: string;
  sections: TestSolutionSection[];
}
