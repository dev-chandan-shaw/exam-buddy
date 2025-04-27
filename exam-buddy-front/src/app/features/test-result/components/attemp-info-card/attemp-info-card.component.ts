import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, input, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { AttemptInfoApiService } from '../../service/api/attempt-info-api.service';
import { IAttemptInfo } from '../../models/attempt-info';

@Component({
  selector: 'app-attemp-info-card',
  imports: [CommonModule, MatTabsModule, MatCardModule],
  templateUrl: './attemp-info-card.component.html',
  styleUrl: './attemp-info-card.component.scss',
})
export class AttempInfoCardComponent {
  data = input.required<IAttemptInfo[]>();
  @Output() tabChange = new EventEmitter<number>();
  onTabChange(index: number) {
    const attemptId = this.data()[index].attemptId;
    this.tabChange.emit(attemptId);
  }
}
