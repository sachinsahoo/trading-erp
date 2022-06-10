import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'input-payments',
  templateUrl: './input-payments.component.html',
  styleUrls: ['./input-payments.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InputPaymentsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
