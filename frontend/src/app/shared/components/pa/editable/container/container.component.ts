import {
  Component, ViewChild,
  ViewContainerRef, ComponentFactoryResolver,
  ComponentFactory, ComponentRef, Input, OnInit
} from '@angular/core';

import { NgControl } from '@angular/forms';

import { DisplayComponent } from '../display/display.component';
import { InputComponent } from '../input/input.component';
import { EditableGroup } from '../editable-group';
import { EditableFieldInterface } from '../editable-field-interface';

@Component({
  selector: 'editable-container',
  template: `<ng-template #editableContainer></ng-template>`
})
export class ContainerComponent implements OnInit, EditableFieldInterface {

  @Input() data: any = "";
  @Input() control_type: string = "text";
  @Input() parent_control: EditableGroup;
  @Input() field_name: string;
  @Input() place_holder: string;
  @Input() row_id:number;
  @ViewChild("editableContainer", { read: ViewContainerRef }) container;

  component_mode: number;
  componentRef: ComponentRef<Component>;
  _buffer: any;


  constructor(private resolver: ComponentFactoryResolver) {
    this.component_mode = 0;
  }

  toggle(): void {
    this.component_mode = this.component_mode === 0 ? 1 : 0;
    this.toggleComponent();
  };

  toggleComponent() {
    this.container.clear();
    const factory: ComponentFactory<Component> =
      this.component_mode === 0
        ?
        this.resolver.resolveComponentFactory(DisplayComponent)
        :
        this.resolver.resolveComponentFactory(InputComponent);

    this.componentRef = this.container.createComponent(factory);
    this.componentRef.instance['data'] = this.data;
    if (this.component_mode === 1) {
      this.componentRef.instance['control_type'] = this.control_type;
      this.componentRef.instance['_parent'] = this;
    } else {
      this.componentRef.instance['control_type'] = "text";
    }
  }

  getBuffer() {
    var key_name = this.field_name;
    var value;
    var _obj = {};
    if (this._buffer) {
      value = this._buffer;
    } else {
      value = this.data;
    }
    _obj[key_name] = value;
    return _obj;
  }

  changeListener(evt) {
    this._buffer = evt.target.value;
  }

  refresh() {
    if (this._buffer) {
      this.data = this._buffer;
    }
  }

  ngOnInit() {
    // note we need to do this here. Otherwise, the container is not constructed or 
    // injected (see toogleComponent() for details, we are initialized to a display default)
    this.toggleComponent();
    this.parent_control.registerEditableField(this);
  }
}
