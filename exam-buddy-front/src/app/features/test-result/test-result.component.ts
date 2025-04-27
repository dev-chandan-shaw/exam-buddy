import {
  Component,
  computed,
  inject,
  linkedSignal,
  OnInit,
  signal,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IAttemptInfo } from './models/attempt-info';
import { AttemptInfoApiService } from './service/api/attempt-info-api.service';
import { TestResultApiService } from './service/api/test-result-api.service';
import { TestResult } from './models/test-result';
import { AttempInfoCardComponent } from './components/attemp-info-card/attemp-info-card.component';
import { OverallPerformanceCardComponent } from './components/overall-performance-card/overall-performance-card.component';
import { SectionSummaryTableComponent } from './components/section-summary-table/section-summary-table.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-test-result',
  imports: [
    AttempInfoCardComponent,
    OverallPerformanceCardComponent,
    SectionSummaryTableComponent,
    CommonModule,
  ],
  templateUrl: './test-result.component.html',
  styleUrl: './test-result.component.scss',
})
export class TestResultComponent implements OnInit {
  attemptInfo = signal<IAttemptInfo[]>([]);
  testResult = signal<TestResult>({} as TestResult);
  isloading = signal(true);

  shouldShowResults = computed(() => !this.isloading() && !!this.testResult());

  private _router = inject(ActivatedRoute);
  private _attemptInfoApiService = inject(AttemptInfoApiService);
  private _testResultApiService = inject(TestResultApiService);
  ngOnInit(): void {
    const testId = this._router.snapshot.params['testId'];
    this._attemptInfoApiService.getAttemptInfo(testId).subscribe((res) => {
      this.attemptInfo.set(res);
    });
  }

  onTabChange(attemptId: number) {
    this._testResultApiService.getTestResult(attemptId).subscribe((res) => {
      this.testResult.set(res);
    });
  }
}
