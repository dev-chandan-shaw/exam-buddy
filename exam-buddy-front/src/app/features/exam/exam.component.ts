import {
  Component,
  effect,
  ElementRef,
  HostListener,
  inject,
  OnInit,
  signal,
  ViewChild,
} from '@angular/core';
import { IExam } from './models';
import { ExamService } from '@core/services/exam.service';
import { ExamCardComponent } from './components/exam-card/exam-card.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-exam',
  imports: [ExamCardComponent, MatIconModule, MatButtonModule, CommonModule],
  templateUrl: './exam.component.html',
  styleUrl: './exam.component.scss',
})
export class ExamComponent implements OnInit {
  exams = signal<IExam[]>([]);
  private _examService = inject(ExamService);
  constructor() {
    effect(() => console.log(this.exams()));
  }
  ngOnInit(): void {
    this._examService.getExams().subscribe((exams) => this.exams.set(exams));
  }
  @ViewChild('btnWrapper') btnWrapper!: ElementRef;
  @ViewChild('scrollContainer') scrollContainer!: ElementRef;

  isSticky = false;

  @HostListener('window:scroll', [])
  @HostListener('window:resize', [])
  onScrollOrResize() {
    const cardListBottom =
      this.scrollContainer.nativeElement.getBoundingClientRect().bottom;
    const windowHeight = window.innerHeight;

    this.isSticky = cardListBottom > windowHeight - 80; // Adjust for spacing
  }

  ngAfterViewInit() {
    this.onScrollOrResize(); // Initial check
  }
}
