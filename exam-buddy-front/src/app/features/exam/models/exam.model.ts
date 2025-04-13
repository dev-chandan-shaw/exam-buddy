import { ISubject } from './subject.model';
export interface IExam {
  id: number;
  title: string;
  description: string;
  subjects: ISubject[];
}
