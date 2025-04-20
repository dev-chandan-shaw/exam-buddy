import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TestService } from '@core/services/test.service';
import { ITest } from '@shared/models';
import { TestCardComponent } from '../components/test-card/test-card.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-test',
  imports: [TestCardComponent, CommonModule],
  templateUrl: './test.component.html',
  styleUrl: './test.component.scss',
})
export class TestComponent implements OnInit {
  testList = signal<ITest[]>([]);
  private _route = inject(ActivatedRoute);
  private _testService = inject(TestService);
  ngOnInit(): void {
    this._route.queryParams.subscribe((params) => {
      console.log(params);
    });
    const examId = Number(this._route.snapshot.paramMap.get('examId'));
    this._testService.getAllTestsByExamId(examId).subscribe((tests) => {
      this.testList.set(tests);
    });
  }
}
