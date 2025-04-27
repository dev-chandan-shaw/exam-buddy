import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionSummaryTableComponent } from './section-summary-table.component';

describe('SectionSummaryTableComponent', () => {
  let component: SectionSummaryTableComponent;
  let fixture: ComponentFixture<SectionSummaryTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SectionSummaryTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SectionSummaryTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
