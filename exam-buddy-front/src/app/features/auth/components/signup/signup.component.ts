import { Component, inject, signal } from '@angular/core';
import {
  FormControl,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {
  MatError,
  MatFormField,
  MatFormFieldModule,
  MatLabel,
} from '@angular/material/form-field';
import { AuthService } from '../../service/auth.service';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { ICreateUser } from '../../models/user';

@Component({
  selector: 'app-signup',
  imports: [
    MatFormField,
    MatLabel,
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
    MatButtonModule,
    ReactiveFormsModule,
    CommonModule,
    MatIconModule,
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss',
})
export class SignupComponent {
  firstName = new FormControl('', [Validators.required]);
  lastName = new FormControl('', [Validators.required]);
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  private _authService = inject(AuthService);
  private _router = inject(Router);

  onSubmit() {
    const data: ICreateUser = {
      firstName: this.firstName.value!,
      lastName: this.lastName.value!,
      email: this.email.value!,
      password: this.password.value!,
    };
    this._authService.signup(data);
  }

  isDisabled() {
    return (
      this.firstName.invalid ||
      this.lastName.invalid ||
      this.email.invalid ||
      this.password.invalid
    );
  }

  goToLogin() {
    this._router.navigate(['/auth/login']);
  }

  hide = signal(true);
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }
}
