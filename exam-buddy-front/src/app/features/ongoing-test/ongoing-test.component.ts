import { Component, inject, linkedSignal, OnInit, signal } from '@angular/core';
import { QuesitonNavPanelComponent } from './components/quesiton-nav-panel/quesiton-nav-panel.component';
import { SectionContainerComponent } from './components/section-container/section-container.component';
import { CommonModule } from '@angular/common';
import { ITest } from '@shared/models';
import { ActiveTestSection, IActiveTest } from './models/active-test';
import { OngoingTestApiService } from './services/api/ongoing-test-api.service';
import { QuestionViewerComponent } from './components/question-viewer/question-viewer.component';
import { ActiveTestService } from './services/active-est.service';

@Component({
  selector: 'app-ongoing-test',
  imports: [QuesitonNavPanelComponent, CommonModule, SectionContainerComponent],
  templateUrl: './ongoing-test.component.html',
  styleUrl: './ongoing-test.component.scss',
})
export class OngoingTestComponent implements OnInit {
  test = signal<IActiveTest>({} as IActiveTest);
  private _testApiService = inject(OngoingTestApiService);
  private _activeTestService = inject(ActiveTestService);
  currentSection = this._activeTestService.getSelectedSection();

  ngOnInit(): void {
    this._testApiService.getOngoingTest().subscribe((res) => {
      this.test.set(res);
      console.log(res);
      this._activeTestService.setActiveTest(res);
    });
  }

  selectTestSection(section: ActiveTestSection) {
    this._activeTestService.setSelectedSection(section);
  }
}
