import { IQuestion } from './question';
import { ISubject } from './subject';

export interface ITestSection {
  id: number;
  marks: number;
  time: number;
  questions: IQuestion[];
  subject: ISubject;
}
