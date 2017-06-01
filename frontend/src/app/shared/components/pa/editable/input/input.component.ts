import { Component, Input } from '@angular/core';
import { ControlValueAccessor } from '@angular/forms';
import {Editable} from '../editable';
import {ContainerComponent} from '../container/container.component';


@Component({
  selector: 'editable-input',
  template: `
    <input name="componentProperty" [type]="_parent.control_type" required
           [placeholder]="_parent.place_holder" (change)="changeListener($event)"> `
})
export class InputComponent extends Editable {
     //@Input() control_type='text';
     //private _data: any;
     public _parent: ContainerComponent;

     changeListener(evt) {
       // this._data = evt.target.value;
        this._parent.changeListener(evt);
     }

     
     
     
}
