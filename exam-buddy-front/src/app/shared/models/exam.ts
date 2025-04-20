import { ISubject } from './subject';

export interface IExam {
  id: number;
  title: string;
  description: string;
  subjects: ISubject[];
}
