import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss', '../../../styles.scss'],
})
export class HeaderComponent implements OnInit {
  constructor(private router: Router) {}

  isActive(url: string) {
    return this.router.url === url;
  }

  ngOnInit(): void {}
}
