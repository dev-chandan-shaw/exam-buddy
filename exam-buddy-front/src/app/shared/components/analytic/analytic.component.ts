import { CommonModule } from '@angular/common';
import {
  Component,
  Input,
  input,
  linkedSignal,
  OnInit,
  signal,
} from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { EditorComponent } from '@tinymce/tinymce-angular';
import { TextEditorComponent } from '../text-editor/text-editor.component';

@Component({
  selector: 'app-analytic',
  imports: [
    CommonModule,
    MatIconModule,
    MatTabsModule,
    MatCardModule,
    TextEditorComponent,
  ],
  templateUrl: './analytic.component.html',
  styleUrl: './analytic.component.scss',
})
export class AnalyticComponent {}
