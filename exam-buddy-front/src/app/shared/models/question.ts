export interface IQuestion {
  id: number;
  description: string;
  passage?: string;
  options: IQuestionOption[];
  subtopicId: number;
  examId: number;
  questionStats: QuestionStats;
  solution?: string;
}

export interface IQuestionOption {
  id: number;
  description: string;
  correct: boolean;
}

export interface QuestionStats {
  totalAttempts: number;
  totalCorrect: number;
  avgTimeSeconds: number;
}
