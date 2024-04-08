import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BlackjackCardComponent } from './blackjack-card/blackjack-card.component';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import {MatButtonModule} from '@angular/material/button';

@NgModule({
  declarations: [BlackjackCardComponent],
  imports: [
    CommonModule,
    MatCardModule,
    MatDividerModule,
    MatProgressBarModule,
    MatButtonModule
  ],
  exports: [BlackjackCardComponent],
})
export class BlackjackModule {}
