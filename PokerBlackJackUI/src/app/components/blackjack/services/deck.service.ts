import { Injectable } from '@angular/core';
import { Card, Suit, Value } from '../models/card';
import { Deck } from '../models/deck';

@Injectable({
  providedIn: 'root'
})
export class DeckService {
  private deck: Deck = {
    cards: []
  };

  constructor() {
    this.createDeck();
  }

  createDeck(): void {
    for (const suit of Object.values(Suit)) {
      for (const value of Object.values(Value)) {
        this.deck.cards.push({
          suit: suit,
          value: value as Value
        });
      }
    }
  }
  
  

  shuffleDeck(): void {
    // Implement Fisher-Yates shuffle
    const deck = this.deck.cards;
    let currentIndex = deck.length;
    let randomIndex;
  
    // While there are elements remaining to shuffle
    while (currentIndex !== 0) {
      // Pick a remaining element
      randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex--;
  
      // Swap the current element with the random element
      [deck[currentIndex], deck[randomIndex]] = [deck[randomIndex], deck[currentIndex]];
    }
  }
  

  dealCard(): Card | null {
    // Check if deck is empty
    if (this.deck.cards.length === 0) {
      return null; // Indicate no cards left
    }
  
    return this.deck.cards.pop()!; // Assuming non-empty deck (checked above)
  }
  

  getDeck(): Deck {
    return this.deck;
  }
}
