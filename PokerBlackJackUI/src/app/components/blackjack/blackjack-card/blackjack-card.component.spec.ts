import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlackjackCardComponent } from './blackjack-card.component';

describe('BlackjackCardComponent', () => {
  let component: BlackjackCardComponent;
  let fixture: ComponentFixture<BlackjackCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BlackjackCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BlackjackCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
