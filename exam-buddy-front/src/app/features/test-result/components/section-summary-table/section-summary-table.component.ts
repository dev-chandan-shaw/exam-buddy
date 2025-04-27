import { Component, computed, input } from '@angular/core';
import { TestResult } from '../../models/test-result';
import {
  getTotalMarks,
  getAvgAccuracy,
  getTotalMarksObtained,
  getTotalAttemptedQuestions,
  getTotalQuestions,
  getTotalTimeMinute,
  getTotalTimeTakenSeconds,
} from '../../utils/utils';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-section-summary-table',
  imports: [CommonModule, MatCardModule],
  templateUrl: './section-summary-table.component.html',
  styleUrl: './section-summary-table.component.scss',
})
export class SectionSummaryTableComponent {
  testResult = input<TestResult>();
  totalMarks = computed(() => {
    const result = this.testResult();
    return result ? getTotalMarks(result) : 0; // fallback value if testResult is undefined
  });

  avgAccuracy = computed(() => {
    const result = this.testResult();
    return result ? getAvgAccuracy(result) : 0;
  });

  totalMarksObtained = computed(() => {
    const result = this.testResult();
    return result ? getTotalMarksObtained(result) : 0;
  });

  totalAttemptedQuestion = computed(() => {
    const result = this.testResult();
    return result ? getTotalAttemptedQuestions(result) : 0;
  });

  totalQuestions = computed(() => {
    const result = this.testResult();
    return result ? getTotalQuestions(result) : 0;
  });

  totalTimeTakenSeconds = computed(() => {
    const result = this.testResult();
    return result ? getTotalTimeTakenSeconds(result) : 0;
  });

  totalTime = computed(() => {
    const result = this.testResult();
    return result ? getTotalTimeMinute(result) : 0;
  });

  changeSecondsToMinutes = (seconds: number) => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes} min ${remainingSeconds} sec`;
  };
}
