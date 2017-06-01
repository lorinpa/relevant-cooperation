import { MlButton } from './../../../../ml/components/controls/button/mlButton';
import { Component, OnInit } from '@angular/core';
import {EditableGroup} from '../../editable-group';

@Component({
  selector: 'app-update',
  template: `<ml-button aspect="raised, colored, primary" (click)="updateData()" ripple>Update</ml-button>`
})
export class UpdateComponent implements OnInit {

  parent_control: EditableGroup;

  constructor() { }

  updateData() {
    if (this.parent_control) {
      this.parent_control.updateData();
    }
    
  }

  ngOnInit() {
  }

}
