import { Component, inject, model, signal } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {
  CreateTestDialogData,
  CreateTestRequest,
} from '../../models/create-test';
import { MatSelectModule } from '@angular/material/select';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { TestService } from '@core/services/test.service';
@Component({
  selector: 'app-create-test',
  imports: [
    MatDialogModule,
    MatSelectModule,
    FormsModule,
    MatInputModule,
    MatRadioModule,
    MatCheckboxModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    CommonModule,
  ],
  templateUrl: './create-test.component.html',
  styleUrl: './create-test.component.scss',
})
export class CreateTestComponent {
  readonly dialogRef = inject(MatDialogRef<CreateTestComponent>);
  readonly data = inject<CreateTestDialogData>(MAT_DIALOG_DATA);
  readonly exams = this.data.exams;
  readonly fb = inject(FormBuilder);
  readonly testService = inject(TestService);
  readonly form: FormGroup = this.fb.group({
    testTitle: ['', Validators.required],
    examId: [null, Validators.required],
    isPyqTest: [false],
  });
  onClose(): void {
    this.dialogRef.close('Dialog closed!');
  }

  onSubmit() {
    if (this.form.valid) {
      const reqBody: CreateTestRequest = {
        title: this.form.value.testTitle,
        examId: this.form.value.examId,
        pyqTest: this.form.value.isPyqTest,
      };
      this.testService.createTest(reqBody).subscribe(() => {
        this.onClose();
      });
    }
  }
}
