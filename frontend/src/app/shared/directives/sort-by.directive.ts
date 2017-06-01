import { Directive, Input, Output, EventEmitter } from '@angular/core';

@Directive({
  selector: '[paSortBy]',
  host: {
    '(click)': 'onClick($event)'
  }
})
export class SortByDirective {
  private sortProperty: string;

  constructor() { }

  @Output()
  sorted: EventEmitter<string> = new EventEmitter<string>();

  @Input('paSortBy')
  set sortBy(value:string) {
    this.sortProperty = value;
  }

  onClick(event:any) {
    event.preventDefault();
    this.sorted.next(this.sortProperty);
  }

}
