import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AnalyticComponent } from '../../shared/components/analytic/analytic.component';
import { TEST_ANALYSIS_DATA } from '@shared/dummy_data/test-analysis';

@Component({
  selector: 'app-exam',
  imports: [RouterModule, AnalyticComponent],
  templateUrl: './exam.component.html',
  styleUrl: './exam.component.scss',
})
export class ExamComponent {
  data = TEST_ANALYSIS_DATA;
}
