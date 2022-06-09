import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'payables',
  templateUrl: './payables.component.html',
  styleUrls: ['./payables.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PayablesComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
