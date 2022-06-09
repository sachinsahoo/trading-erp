import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'enter-transaction-modal',
  templateUrl: './enter-transaction-modal.component.html',
  styleUrls: ['./enter-transaction-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EnterTransactionModalComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
