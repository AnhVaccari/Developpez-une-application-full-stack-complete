import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar-connected',
  templateUrl: './navbar-connected.component.html',
  styleUrls: ['./navbar-connected.component.scss'],
})
export class NavbarConnectedComponent implements OnInit {
  isMenuOpen = false;

  constructor() {}

  ngOnInit(): void {}

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
