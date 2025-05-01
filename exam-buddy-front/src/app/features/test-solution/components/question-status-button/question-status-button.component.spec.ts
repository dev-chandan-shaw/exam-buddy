import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionStatusButtonComponent } from './question-status-button.component';

describe('QuestionStatusButtonComponent', () => {
  let component: QuestionStatusButtonComponent;
  let fixture: ComponentFixture<QuestionStatusButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuestionStatusButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuestionStatusButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
