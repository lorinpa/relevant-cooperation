import { Component, Input } from '@angular/core';
import {Editable} from '../editable';

@Component({
  selector: 'editable-display',
  template:  `{{ data }}`
})
export class DisplayComponent extends Editable {


}
