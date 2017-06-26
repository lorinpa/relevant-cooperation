import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
 
})
export class ContactFormComponent implements OnInit {

  constructor() { }

  msgs = ["Need help?","support@public-action.org"];
  idx = 0;

  ngOnInit() {
  }

  toggle() {
      this.idx = this.idx === 0 ? 1 : 0;
  }

}
