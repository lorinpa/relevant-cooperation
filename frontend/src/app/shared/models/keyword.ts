//import { Observable } from 'rxjs/Observable';

import { IdInterface } from './id-interface';



export interface KeywordInterface extends IdInterface {
    keyword: string;
}

export class Keyword implements KeywordInterface {
    id: number;
    keyword: string;

    constructor(id: number, keyword: string) {
        this.id = id;
        this.keyword = keyword;
    }
}

export class KeywordList {
    list: KeywordInterface[];
   
    constructor() {
        this.list = [];
    }

     removeKeyword(id: number) {
        this.list = this.list.filter(rec => rec.id !== id);
    }

    addKeyword(kw: Keyword)  {
        try {
            if (!this.list.find(rec => rec.id === kw.id)) {
                this.list.push(kw);          
            }
        } catch (Error) {
            console.log("KeywordList.addKeyword() exception: " + Error);
        }
        
    }

    getList() : Keyword[] {
        return this.list;
    }

    setList(list: Keyword[]) {
        this.list = list;
       
    }
}