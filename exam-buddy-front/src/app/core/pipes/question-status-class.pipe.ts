import { Pipe, PipeTransform } from '@angular/core';
import { QuestionStatus } from 'app/features/ongoing-test/models/active-test';

@Pipe({
  name: 'questionStatusClass',
  standalone: true,
})
export class QuestionStatusClassPipe implements PipeTransform {
  transform(status: QuestionStatus): string {
    console.log(status);

    switch (status) {
      case 'NOT_VISITED':
        return 'bg-[#f9f9f9] border border-black';
      case 'NOT_ANSWERED':
        return 'bg-[#dc2626] rounded-b-3xl text-white';
      case 'MARKED':
        return 'bg-[#9333ea] text-white rounded-3xl';
      case 'MARKED_AND_ANSWERED':
        return 'bg-[#9333ea] rounded-3xl text-white';
      case 'ANSWERED':
        return 'bg-[#16a34a] rounded-t-3xl text-white';
      default:
        return 'bg-gray-100 text-black';
    }
  }
}
