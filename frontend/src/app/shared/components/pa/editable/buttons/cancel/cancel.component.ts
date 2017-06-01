import { MlButton } from './../../../../ml/components/controls/button/mlButton';
import { Component, OnInit } from '@angular/core';
import {EditableGroup} from '../../editable-group';

@Component({
  selector: 'app-cancel',
  template: `<ml-button aspect="raised, colored, primary" (click)="cancel()" ripple>Cancel</ml-button>`
})
export class CancelComponent implements OnInit {

  parent_control:EditableGroup;

  cancel() {
    if (this.parent_control) {
      this.parent_control.reset();
    }
  }

  constructor() { }

  ngOnInit() {
  }

}
