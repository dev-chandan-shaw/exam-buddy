import { Component } from '@angular/core';
import { QuestionViewerComponent } from '../question-viewer/question-viewer.component';

@Component({
  selector: 'test-solution-section-viewer',
  imports: [QuestionViewerComponent],
  templateUrl: './section-viewer.component.html',
  styleUrl: './section-viewer.component.scss',
})
export class SectionViewerComponent {}
