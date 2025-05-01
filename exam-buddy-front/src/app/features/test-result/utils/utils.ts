import { TestResult } from '../models/test-result';

export const getTotalMarks = (testResult: TestResult | undefined): number => {
  if (!testResult) return 0;
  return testResult.subjectStats.reduce(
    (acc, curr) => acc + curr.totalMarks,
    0
  );
};

export const getTotalMarksObtained = (testResult: TestResult): number => {
  return testResult.subjectStats.reduce(
    (acc, curr) => acc + curr.marksObtained,
    0
  );
};

export const getTotalTimeMinute = (testResult: TestResult): number => {
  return testResult.subjectStats.reduce((acc, curr) => acc + curr.totalTime, 0);
};

export const getTotalTimeTakenSeconds = (testResult: TestResult): number => {
  return testResult.subjectStats.reduce(
    (acc, curr) => acc + curr.timeTakenSeconds,
    0
  );
};

export const getTotalAttemptedQuestions = (testResult: TestResult): number => {
  return testResult.subjectStats.reduce(
    (acc, curr) => acc + curr.totalAttemptedQuestions,
    0
  );
};

export const getTotalQuestions = (testResult: TestResult): number => {
  return testResult.subjectStats.reduce(
    (acc, curr) => acc + curr.totalQuestions,
    0
  );
};

export const getAvgAccuracy = (testResult: TestResult): number => {
  return (
    (getTotalCorrectQuestions(testResult) * 100) / getTotalQuestions(testResult)
  );
};

export const getTotalCorrectQuestions = (testResult: TestResult): number => {
  return testResult.subjectStats.reduce(
    (acc, curr) => acc + curr.totalCorrectQuestions,
    0
  );
};
