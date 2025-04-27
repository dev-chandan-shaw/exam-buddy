import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverallPerformanceCardComponent } from './overall-performance-card.component';

describe('OverallPerformanceCardComponent', () => {
  let component: OverallPerformanceCardComponent;
  let fixture: ComponentFixture<OverallPerformanceCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OverallPerformanceCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverallPerformanceCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
