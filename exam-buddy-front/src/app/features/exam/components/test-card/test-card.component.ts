import { CommonModule } from '@angular/common';
import { Component, inject, input, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { ITestInfo } from '@shared/models';
import { OngoingTestApiService } from 'app/features/ongoing-test/services/api/ongoing-test-api.service';

@Component({
  selector: 'app-test-card',
  imports: [
    MatCardModule,
    MatButtonModule,
    RouterModule,
    CommonModule,
    MatIconModule,
  ],
  templateUrl: './test-card.component.html',
  styleUrl: './test-card.component.scss',
})
export class TestCardComponent {
  test = input.required<ITestInfo>();
  private _activeTestApiService = inject(OngoingTestApiService);
  private _router = inject(Router);

  startTest() {
    this._activeTestApiService.startTest(this.test().id).subscribe((res) => {
      this._router.navigate(['active-test']);
    });
  }
}
