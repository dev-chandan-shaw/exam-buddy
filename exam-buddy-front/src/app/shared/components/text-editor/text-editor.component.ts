import { CommonModule } from '@angular/common';
import { Component, effect, input, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { EditorComponent } from '@tinymce/tinymce-angular';

@Component({
  selector: 'app-text-editor',
  imports: [
    CommonModule,
    MatIconModule,
    MatTabsModule,
    MatCardModule,
    EditorComponent,
  ],
  templateUrl: './text-editor.component.html',
  styleUrl: './text-editor.component.scss',
})
export class TextEditorComponent {
  height = input<number>();
  type = input<'Question' | 'Option'>();
  placeholder = input<string>('Write your question here... 📝');

  constructor() {
    effect(() => {
      this.intitalizeEditor();
    });
  }
  intitalizeEditor() {
    this.init = {
      height: this.type() === 'Question' ? '220px' : '160px',
      menubar: false,
      resize: false,
      placeholder: this.placeholder(),
      plugins: [
        'anchor',
        'autolink',
        'charmap',
        'codesample',
        'emoticons',
        'image',
        'link',
        'lists',
        'media',
        'searchreplace',
        'table',
        'visualblocks',
        'wordcount',
      ],
      toolbar:
        'undo redo | blocks | bold italic | alignleft aligncenter alignright | bullist numlist | image', // 👈 This adds the image button
      content_style: `
        body { font-family:Arial,sans-serif; font-size:14px }
        img { max-width: 100%; height: auto; }
      `,
    };
  }
  init: EditorComponent['init'];
}
