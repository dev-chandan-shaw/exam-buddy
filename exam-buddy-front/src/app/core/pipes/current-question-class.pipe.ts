import { Pipe, PipeTransform } from '@angular/core';
import { QuestionStatus } from 'app/features/ongoing-test/models/active-test';

@Pipe({
  name: 'currentQuestionClass',
  standalone: true,
})
export class currentQuestionClassPipe implements PipeTransform {
  transform(questionId: number, currentQuestionId: number): string {
    console.log(questionId, currentQuestionId);

    if (questionId === currentQuestionId) {
      return 'rounded-3xl';
    } else {
      return '';
    }
  }
}
