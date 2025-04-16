import { IExam } from 'app/features/exam/models';

export interface CreateTestDialogData {
  exams: IExam[];
}

export interface CreateTestRequest {
  title: string;
  examId: number;
  pyqTest: boolean;
}
