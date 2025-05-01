import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionViewerComponent } from './section-viewer.component';

describe('SectionViewerComponent', () => {
  let component: SectionViewerComponent;
  let fixture: ComponentFixture<SectionViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SectionViewerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SectionViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
