import { Component, Input, OnInit } from '@angular/core';
import { Card, Suit, Value } from '../models/card';
import { DeckService } from '../services/deck.service';

@Component({
  selector: 'app-blackjack-card',
  templateUrl: './blackjack-card.component.html',
  styleUrl: './blackjack-card.component.scss',
})
export class BlackjackCardComponent implements OnInit {
  @Input() card!: Card;
  playerCards: Card[] = []; // Array to store player's cards
  value!: Value;

  constructor(private deckService: DeckService) {}

  getCardColor(suit: Suit) {
    return suit === Suit.Spades || suit === Suit.Clubs ? 'black' : 'red';
  }

  ngOnInit(): void {
    this.dealCard();
    this.dealCard();
    this.value = this.card.value;
  }

  showPlayerCards() {
    // Oyuncunun kartlarını göster
    console.log(this.playerCards);
  }

  dealCard() {
    const card = this.deckService.dealCard();
    if (card) {
      this.playerCards.push(card);
    } else {
      console.log('No more cards in the deck!');
    }
  }
}
