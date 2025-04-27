import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttempInfoCardComponent } from './attemp-info-card.component';

describe('AttempInfoCardComponent', () => {
  let component: AttempInfoCardComponent;
  let fixture: ComponentFixture<AttempInfoCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AttempInfoCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AttempInfoCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
