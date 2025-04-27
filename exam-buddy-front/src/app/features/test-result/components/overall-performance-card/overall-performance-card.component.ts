import { CommonModule } from '@angular/common';
import { Component, computed, input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { TestResult } from '../../models/test-result';
import {
  getAvgAccuracy,
  getTotalAttemptedQuestions,
  getTotalMarks,
  getTotalMarksObtained,
  getTotalQuestions,
} from '../../utils/utils';

@Component({
  selector: 'app-overall-performance-card',
  imports: [CommonModule, MatCardModule, MatIconModule],
  templateUrl: './overall-performance-card.component.html',
  styleUrl: './overall-performance-card.component.scss',
})
export class OverallPerformanceCardComponent {
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
}
