import { IQuestion } from "./question";
import { ISubject, ITopic } from "./subject";

export interface ITestAnalysis {
    testName: string;
    rankOfUser: number;
    rankOutOf: number;
    subjectScores: ISubjectScore[];
    topicAnalysis: ITopicAnalysis[];
}

export interface ISubjectScore {
    subject: ISubject;
    marksObtained: number;
    totalMarks: number;
    timeTakenSeconds: number;
    totalTimeSeconds: number;
    accuracy: number;
    attemptedQuestions: number;
    totalQuestions: number;
}

export interface ITopicAnalysis {
    topic: ITopic;
    questions: ITopicQuestion[];
}

export interface ITopicQuestion{
    question: IQuestion;
    questionNumber: number;
    isAttempted: boolean;
    isCorrect: boolean;
}



interface questionAnalysis {
    totalAttempt: number;
    averageTime: number;
    accuracy: number;
}