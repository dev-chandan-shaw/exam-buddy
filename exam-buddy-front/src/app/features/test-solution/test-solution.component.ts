import {
  Component,
  computed,
  effect,
  inject,
  linkedSignal,
  OnInit,
  signal,
} from '@angular/core';
import { TestSolutionApiService } from './services/test-solution.api.service';
import { TestSolutionService } from './services/test-solution.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TestSolutionSection } from './models/test-solution-section';
import { SectionViewerComponent } from './components/section-viewer/section-viewer.component';
import { QuestionNavPanelComponent } from './components/question-nav-panel/question-nav-panel.component';
import { MatIconModule } from '@angular/material/icon';
import { AttemptInfoApiService } from '../test-result/service/api/attempt-info-api.service';
import { IAttemptInfo } from '../test-result/models/attempt-info';

import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-test-solution',
  imports: [
    CommonModule,
    SectionViewerComponent,
    QuestionNavPanelComponent,
    MatIconModule,
    MatSelectModule,
    MatFormFieldModule,
    FormsModule,
  ],
  templateUrl: './test-solution.component.html',
  styleUrl: './test-solution.component.scss',
})
export class TestSolutionComponent implements OnInit {
  private _testSolutionApiService = inject(TestSolutionApiService);
  private _testSolutionService = inject(TestSolutionService);
  private _attempInfoApiService = inject(AttemptInfoApiService);
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  testSolution = this._testSolutionService.getTestSolution();
  currentSection = this._testSolutionService.getSelectedSection();
  attempInfo = signal<IAttemptInfo[]>([]);
  testId = Number(this._route.snapshot.paramMap.get('testId'));
  selectedAttemptId = linkedSignal(
    () => this.attempInfo()[this.attempInfo().length - 1].attemptId
  );

  constructor() {
    effect(() => {
      this.loadSolution();
    });
  }

  ngOnInit(): void {
    this._attempInfoApiService.getAttemptInfo(this.testId).subscribe((info) => {
      console.log(info);
      this.attempInfo.set(info);
    });
  }

  loadSolution() {
    this._testSolutionApiService
      .getTestSolutionByTestAttemptId(this.selectedAttemptId())
      .subscribe((testSolution) => {
        this._testSolutionService.setTestSolution(testSolution);
      });
  }

  selectTestSection(section: TestSolutionSection) {
    this._testSolutionService.setSelectedSection(section);
  }

  goBack() {
    this._router.navigate(['/']);
  }

  goToAnalysis() {
    this._router.navigate(['test-analysis', this.testId]);
  }
}
