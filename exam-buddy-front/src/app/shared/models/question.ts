export interface IQuestion {
  id: number;
  description: string;
  passage?: string;
  options: IQuestionOption[];
  subtopicId: number;
  examId: number;
}

export interface IQuestionOption {
  id: number;
  description: string;
  correct: boolean;
}
