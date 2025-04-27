import { ISubject } from '@shared/models';

export interface TestResult {
  id: number;
  testName: string;
  rankOfUser: number;
  rankOutOf: number;
  percentile: number;
  testAttemptId: number;
  subjectStats: SubjectStat[];
  topicsAnalysis: TopicAnalysis[];
}

interface SubjectStat {
  id: number;
  subject: ISubject;
  totalQuestions: number;
  totalMarks: number;
  marksObtained: number;
  timeTakenSeconds: number;
  totalTime: number;
  totalAttemptedQuestions: number;
  accuracy: number;
}

interface TopicAnalysis {
  id: number;
  topic: Topic;
  topicQuestionStats: TopicQuestionStat[];
}

interface Topic {
  id: number;
  name: string;
  subtopics: Subtopic[];
}

interface Subtopic {
  id: number;
  name: string;
}

interface TopicQuestionStat {
  id: number;
  questionNo: number;
  isAttempted: boolean;
  isCorrect: boolean;
}
