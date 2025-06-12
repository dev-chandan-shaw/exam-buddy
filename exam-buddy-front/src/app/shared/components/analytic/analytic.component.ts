import { CommonModule } from '@angular/common';
import { Component, Input, input, linkedSignal, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ITestAnalysis } from '@shared/models/test-analysis';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-analytic',
  imports: [CommonModule, MatIconModule, MatTabsModule, MatCardModule],
  templateUrl: './analytic.component.html',
  styleUrl: './analytic.component.scss',
})
export class AnalyticComponent {
  testAnalysis = input.required<ITestAnalysis>();
  totalMarks = linkedSignal(() => {
    return (
      this.testAnalysis()?.subjectScores.reduce(
        (acc, curr) => acc + curr.totalMarks,
        0
      ) || 0
    );
  });
  totalMarksObtained = linkedSignal(() => {
    return (
      this.testAnalysis()?.subjectScores.reduce(
        (acc, curr) => acc + curr.marksObtained,
        0
      ) || 0
    );
  });
  totalTimeTaken = linkedSignal(() => {
    return (
      this.testAnalysis()?.subjectScores.reduce(
        (acc, curr) => acc + curr.timeTakenSeconds,
        0
      ) || 0
    );
  });

  totalTime = linkedSignal(() => {
    return (
      this.testAnalysis()?.subjectScores.reduce(
        (acc, curr) => acc + curr.totalTimeSeconds / 60,
        0
      ) || 0
    );
  });
  avgAccuracy = linkedSignal(() => {
    const totalAccuracySum = this.testAnalysis()?.subjectScores.reduce(
      (acc, curr) => acc + curr.accuracy,
      0
    );
    return totalAccuracySum
      ? totalAccuracySum / this.testAnalysis()?.subjectScores.length
      : 0;
  });

  totalAttemptedQuestions = linkedSignal(() => {
    return (
      this.testAnalysis()?.subjectScores.reduce(
        (acc, curr) => acc + curr.attemptedQuestions,
        0
      ) || 0
    );
  });

  totalQuestions = linkedSignal(() => {
    return (
      this.testAnalysis()?.subjectScores.reduce(
        (acc, curr) => acc + curr.totalQuestions,
        0
      ) || 0
    );
  });

  changeSecondsToMinutes(seconds: number) {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes}m ${remainingSeconds}s`;
  }
}
