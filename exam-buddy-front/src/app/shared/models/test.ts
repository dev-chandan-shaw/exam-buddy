import { IExam } from './exam';
import { IQuestion } from './question';
import { ISubject } from './subject';

export interface ITest {
  id: number;
  title: string;
  pyqTest: boolean;
  sectionTest: boolean;
  published: boolean;
  totalTime: number;
  totalMarks: number;
  exam: IExam;
  testSections: TestSection[];
}

export interface TestSection {
  id: number;
  marks: number;
  time: number;
  subject: ISubject;
  questions: IQuestion[]; // Assuming you'll have a Question interface
}

export interface ITestInfo {
  id: number;
  title: string;
  totalQuestions: number;
  totalMarks: number;
  totalTime: number;
  lastAttemptedAt: Date;
  isPyqTest: boolean;
  isSectionTest: boolean;
  isAttempted: boolean;
  isPaused: boolean;
}
