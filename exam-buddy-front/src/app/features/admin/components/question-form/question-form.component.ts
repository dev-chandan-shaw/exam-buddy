import { Component } from '@angular/core';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatStepperModule } from '@angular/material/stepper';
@Component({
  selector: 'app-question-form',
  imports: [MatStepperModule, MatFormField, MatInputModule],
  templateUrl: './question-form.component.html',
  styleUrl: './question-form.component.scss',
})
export class QuestionFormComponent {}
