import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTabChangeEvent, MatTabsModule } from '@angular/material/tabs';
import { TestService } from '@core/services/test.service';
import { ITest } from '@shared/models';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
@Component({
  selector: 'app-test-editor',
  imports: [
    MatTabsModule,
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
  ],
  templateUrl: './test-editor.component.html',
  styleUrl: './test-editor.component.scss',
})
export class TestEditorComponent implements OnInit {
  private _route = inject(ActivatedRoute);
  private _router = inject(Router);
  private _testService = inject(TestService);
  currentSectionId = signal<number>(0);
  test = signal<ITest | null>(null);

  ngOnInit(): void {
    const testId = this._route.snapshot.paramMap.get('testId');
    if (testId) {
      const id = Number(testId);
      this._testService
        .getTestById(id)
        .subscribe((test) => this.test.set(test));
    }
  }

  onTabChange(event: MatTabChangeEvent): void {
    const selectedIndex = event.index;
    const selectedSection = this.test()?.testSections[selectedIndex];
    this.currentSectionId.set(selectedSection?.id!);
    console.log(this.currentSectionId());
  }

  navigateToAddQuestionPage() {
    this._router.navigate(['/admin/add-question'], {
      queryParams: {
        examId: this.test()?.exam.id,
        testId: this.test()?.id,
        sectionId: this.currentSectionId(),
      },
    });
  }
}
