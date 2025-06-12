import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { AuthService } from 'app/features/auth/service/auth.service';

import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-header',
  imports: [MatMenuModule, MatIconModule, MatButtonModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  private _authService = inject(AuthService);
  user = computed(this._authService.getUser());
  ngOnInit(): void {}
  logout() {
    this._authService.logout();
  }
}
