import { IExam } from '@shared/models/exam';

export interface CreateTestDialogData {
  exams: IExam[];
}

export interface CreateTestRequest {
  title: string;
  examId: number;
  pyqTest: boolean;
}
