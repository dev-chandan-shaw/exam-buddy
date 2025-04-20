import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatStepperModule } from '@angular/material/stepper';
import { CreateQuestion } from '../../models/question';
import { QuestionService } from '@core/services/question.service';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-question-form',
  imports: [
    MatStepperModule,
    MatFormField,
    MatInputModule,
    ReactiveFormsModule,
    CommonModule,
    MatSelectModule,
    MatButtonModule,
  ],
  templateUrl: './question-form.component.html',
  styleUrl: './question-form.component.scss',
})
export class QuestionFormComponent implements OnInit {
  examId = signal<number>(0);
  testId = signal<number>(0);
  sectionId = signal<number>(0);
  subtopicId = signal<number>(1);
  private _formBuilder = inject(FormBuilder);
  private _questionService = inject(QuestionService);
  private _router = inject(Router);
  private _route = inject(ActivatedRoute);
  questionDescriptionFromGroup = this._formBuilder.group({
    description: ['', Validators.required],
  });
  questionPassageFromGroup = this._formBuilder.group({
    passage: [''],
  });
  firstOptionFromGroup = this._formBuilder.group({
    description: ['', Validators.required],
    correct: [false],
  });
  secondOptionFromGroup = this._formBuilder.group({
    description: ['', Validators.required],
    correct: [false],
  });
  thirdOptionFromGroup = this._formBuilder.group({
    description: ['', Validators.required],
    correct: [false],
  });
  fourthOptionFromGroup = this._formBuilder.group({
    description: ['', Validators.required],
    correct: [false],
  });
  solutionFromGroup = this._formBuilder.group({
    solution: ['', Validators.required],
  });

  ngOnInit() {
    this._route.queryParams.subscribe((params) => {
      this.testId.set(Number(params['testId']));
      this.sectionId.set(Number(params['sectionId']));
      this.examId.set(Number(params['examId']));
      console.log(params);
    });
  }

  onSubmit() {
    const question: CreateQuestion = {
      description: this.questionDescriptionFromGroup.value.description ?? '',
      passage: this.questionPassageFromGroup.value.passage ?? '',
      subtopicId: this.subtopicId(),
      examId: this.examId(),
      testSectionId: this.sectionId(),
      options: [
        {
          description: this.firstOptionFromGroup.value.description ?? '',
          correct: this.firstOptionFromGroup.value.correct ?? false,
        },
        {
          description: this.secondOptionFromGroup.value.description ?? '',
          correct: this.secondOptionFromGroup.value.correct ?? false,
        },
        {
          description: this.thirdOptionFromGroup.value.description ?? '',
          correct: this.thirdOptionFromGroup.value.correct ?? false,
        },
        {
          description: this.fourthOptionFromGroup.value.description ?? '',
          correct: this.fourthOptionFromGroup.value.correct ?? false,
        },
      ],
    };
    this._questionService.addQuestion(question).subscribe((data) => {
      console.log(data);
      this.questionDescriptionFromGroup.reset();
      this._router.navigate([`admin/test/${this.testId()}/draft`]);
    });
  }
}
