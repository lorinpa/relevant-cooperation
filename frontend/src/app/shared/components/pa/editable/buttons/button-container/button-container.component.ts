import { Component, OnInit,ViewChild,
  ViewContainerRef, ComponentFactoryResolver,
  ComponentFactory, ComponentRef, Input } from '@angular/core';


import {EDITABLE_OPERATIONS, EditableButtonGroup} from '../../editable-button-group';
import {EditableGroup} from '../../editable-group';
import {EditableButtonInterface} from '../../editable-button-interface';

import {CancelComponent} from '../cancel/cancel.component';
import {UpdateComponent} from '../update/update.component';
import {DeleteComponent} from '../delete/delete.component';

@Component({
  selector: 'editable-button-container',
  template:`<ng-template #editableButtonContainer></ng-template>
            <ng-template #optionalButtonContainer></ng-template>`
})

export class ButtonContainerComponent implements OnInit, EditableButtonInterface {

  @Input() parent_control: EditableGroup;
  @ViewChild("editableButtonContainer", { read: ViewContainerRef }) container;
  @ViewChild("optionalButtonContainer", { read: ViewContainerRef }) optional_container;
  componentRef: ComponentRef<Component>;
  optionalRef: ComponentRef<Component>;
  group_control: EditableButtonGroup;
  

  change_view(operation:number) {
    this.container.clear();
    const factory: ComponentFactory<Component> = this.selectButtonView(operation);
    this.componentRef = this.container.createComponent(factory);
    this.componentRef.instance['parent_control'] = this.parent_control; 
    this.optional_container.clear(); 
    if (operation === EDITABLE_OPERATIONS.UPDATE) {
        
        const optionalFactory: ComponentFactory<Component> = this.resolver.resolveComponentFactory(CancelComponent);
        this.optionalRef = this.optional_container.createComponent(optionalFactory);
        this.optionalRef.instance['parent_control'] = this.parent_control;
    } 
  }

  selectButtonView(operation:number) {     
      let cf: ComponentFactory<Component>;
      switch(operation) {
          case EDITABLE_OPERATIONS.CANCEL:
              cf = this.resolver.resolveComponentFactory(CancelComponent);
              break;
          case EDITABLE_OPERATIONS.UPDATE:
              cf = this.resolver.resolveComponentFactory(UpdateComponent);
              break;
          case EDITABLE_OPERATIONS.DELETE:
              cf = this.resolver.resolveComponentFactory(DeleteComponent);
              break;
      }
      return cf;
  }

  constructor(private resolver: ComponentFactoryResolver) { 
      this.group_control = new EditableButtonGroup();
  }

  ngOnInit() {
    this.change_view(EDITABLE_OPERATIONS.DELETE);
    this.parent_control.registerEditableButton(this);
  }

}
