import { MlTable } from './../../components/ml/components/table/mlTable';


import { KeywordServiceService } from './../../services/keyword-service.service';
import { KeywordInterface, Keyword } from './../../models/keyword';

import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'app-keywords',
  templateUrl: './keywords.component.html',
  styleUrls: ['./keywords.component.css']
})
export class KeywordsComponent implements OnInit {

  keywords: KeywordInterface[];
  //keywordService: KeywordServiceService;

  constructor(private keywordService : KeywordServiceService) { 
      //this.keywordService = keywordService;
  }

  ngOnInit() {
   // this.keywords = this.keywordService.getKeywords();
   this.getRemoteKeywords();
  }

  getRemoteKeywords() {
     this.keywordService.getKeywords().map(
       res => {
         if (res.ok) {
           return res['_body'];
         }
       },
       err => { }
     ).subscribe(
       msg => {this.keywords = JSON.parse(msg);}
       );
   }

}
