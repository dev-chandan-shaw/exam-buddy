import { Component, inject, input, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router, RouterModule } from '@angular/router';
import { ITest } from '@shared/models';
import { OngoingTestApiService } from 'app/features/ongoing-test/services/api/ongoing-test-api.service';

@Component({
  selector: 'app-test-card',
  imports: [MatCardModule, MatButtonModule, RouterModule],
  templateUrl: './test-card.component.html',
  styleUrl: './test-card.component.scss',
})
export class TestCardComponent {
  test = input.required<ITest>();
  private _activeTestApiService = inject(OngoingTestApiService);
  private _router = inject(Router);
  getTotalQuestionLength() {
    let total = 0;
    this.test().testSections.forEach((section) => {
      total += section.questions.length;
    });
    return total;
  }
  startTest() {
    this._activeTestApiService.startTest(this.test().id).subscribe((res) => {
      this._router.navigate(['active-test']);
    });
  }
}
