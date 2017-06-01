import { MlButton } from './../../../../ml/components/controls/button/mlButton';
import { Component, OnInit } from '@angular/core';
import {EditableGroup} from '../../editable-group';

@Component({
  selector: 'app-delete',
  template: `<ml-button aspect="raised, colored, primary" (click)="delete()" ripple>Delete</ml-button>`
})
export class DeleteComponent implements OnInit {

  parent_control:EditableGroup;

  delete() {
    if (this.parent_control) {
      this.parent_control.deleteData();
    }
  }

  constructor() { }

  ngOnInit() {
  }

}
