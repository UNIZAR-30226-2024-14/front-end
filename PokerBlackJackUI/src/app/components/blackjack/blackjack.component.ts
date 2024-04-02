import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-blackjack',
  templateUrl: './blackjack.component.html',
  styleUrls: ['./blackjack.component.scss', '../../../styles.scss'],
})
export class BlackjackComponent implements OnInit {
  dealerHand: Card[] = [];
  playerHand: Card[] = [];
  message: string = '';

  constructor() {}

  ngOnInit(): void {
    this.startGame();
  }

  startGame() {
    // ...
  }

  hit() {
    // ...
  }

  stand() {
    // ...
  }
}

interface Card {
  // ...
}
