import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'view-company',
  templateUrl: './view-company.component.html',
  styleUrls: ['./view-company.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ViewCompanyComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
