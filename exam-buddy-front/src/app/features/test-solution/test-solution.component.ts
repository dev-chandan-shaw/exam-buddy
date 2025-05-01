import {
  Component,
  computed,
  inject,
  linkedSignal,
  OnInit,
} from '@angular/core';
import { TestSolutionApiService } from './services/test-solution.api.service';
import { TestSolutionService } from './services/test-solution.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TestSolutionSection } from './models/test-solution-section';
import { SectionViewerComponent } from './components/section-viewer/section-viewer.component';
import { QuestionNavPanelComponent } from './components/question-nav-panel/question-nav-panel.component';

@Component({
  selector: 'app-test-solution',
  imports: [CommonModule, SectionViewerComponent, QuestionNavPanelComponent],
  templateUrl: './test-solution.component.html',
  styleUrl: './test-solution.component.scss',
})
export class TestSolutionComponent implements OnInit {
  private _testSolutionApiService = inject(TestSolutionApiService);
  private _testSolutionService = inject(TestSolutionService);
  private _route = inject(ActivatedRoute);
  testSolution = this._testSolutionService.getTestSolution();
  currentSection = this._testSolutionService.getSelectedSection();

  ngOnInit(): void {
    const testId = Number(this._route.snapshot.paramMap.get('testId'));
    this._testSolutionApiService
      .getTestSolutionByTestId(testId)
      .subscribe((testSolution) => {
        this._testSolutionService.setTestSolution(testSolution);
      });
  }

  selectTestSection(section: TestSolutionSection) {
    this._testSolutionService.setSelectedSection(section);
  }
}
