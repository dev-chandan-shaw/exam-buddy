import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OngoingTestComponent } from './ongoing-test.component';

describe('OngoingTestComponent', () => {
  let component: OngoingTestComponent;
  let fixture: ComponentFixture<OngoingTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OngoingTestComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OngoingTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
