import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuesitonNavPanelComponent } from './quesiton-nav-panel.component';

describe('QuesitonNavPanelComponent', () => {
  let component: QuesitonNavPanelComponent;
  let fixture: ComponentFixture<QuesitonNavPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuesitonNavPanelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuesitonNavPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
