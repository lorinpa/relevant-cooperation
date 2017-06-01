import { IdInterface } from './id-interface';

 export class Proposal implements IdInterface {
     id:number;
     title:string;
     message:string;
     createdAt:string;
     partners: number[];

     constructor(id:number, title:string) {
         this.id = id;
         this.title = title;
         this.message;
         this.createdAt;
         this.partners;
     }
}

export class PartnerProposal extends Proposal {
    ownerName: String;
    ownerEmail: String;

    constructor(id:number, title:string) {
        super(id, title);
        this.message;
        this.createdAt;
        this.partners;
        this.ownerName;
        this.ownerEmail;

    }
}

