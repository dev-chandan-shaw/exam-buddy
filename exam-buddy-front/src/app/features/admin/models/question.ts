export interface CreateQuestion {
  description: string;
  passage?: string;
  options: QuestionOption[];
  subtopicId: number;
  testSectionId: number;
  examId: number;
}

export interface QuestionOption {
  description: string;
  correct: boolean;
}
