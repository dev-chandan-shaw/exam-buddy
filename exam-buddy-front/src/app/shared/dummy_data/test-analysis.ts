import { ITestAnalysis } from '@shared/models/test-analysis';

export const TEST_ANALYSIS_DATA: ITestAnalysis = {
  testName: 'Midterm Assessment - Grade 10',
  rankOfUser: 5,
  rankOutOf: 100,
  attempts: [
    {
      attempId: 1,
      attemptDate: new Date(),
    },
    {
      attempId: 2,
      attemptDate: new Date(),
    },
    {
      attempId: 3,
      attemptDate: new Date(),
    },
  ],
  subjectScores: [
    {
      subject: { name: 'Mathematics' },
      marksObtained: 78,
      totalMarks: 100,
      timeTakenSeconds: 3200,
      totalTimeSeconds: 3600,
      accuracy: 0.87,
      attemptedQuestions: 23,
      totalQuestions: 25,
    },
    {
      subject: { name: 'Science' },
      marksObtained: 85,
      totalMarks: 100,
      timeTakenSeconds: 3300,
      totalTimeSeconds: 3600,
      accuracy: 0.91,
      attemptedQuestions: 24,
      totalQuestions: 25,
    },
    {
      subject: { name: 'English' },
      marksObtained: 72,
      totalMarks: 100,
      timeTakenSeconds: 3100,
      totalTimeSeconds: 3600,
      accuracy: 0.81,
      attemptedQuestions: 22,
      totalQuestions: 25,
    },
    {
      subject: { name: 'Social Studies' },
      marksObtained: 69,
      totalMarks: 100,
      timeTakenSeconds: 2900,
      totalTimeSeconds: 3600,
      accuracy: 0.75,
      attemptedQuestions: 20,
      totalQuestions: 25,
    },
  ],
  topicAnalysis: [],
};
