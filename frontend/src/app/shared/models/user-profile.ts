import { KeywordServiceService } from './../services/keyword-service.service';
import { KeywordInterface, Keyword } from './keyword';
import { UserProfileInterface } from './user-profile';
import { IdInterface } from './id-interface';

export interface UserProfileInterface extends IdInterface {
    name: string;
    location:string;
    provided_services: Keyword[];
    business_concepts: Keyword[];
    partner_services: Keyword[];

     keywords: Keyword[];

}

export class UserProfile implements UserProfileInterface {
    id: number;
    name: string;
    location: string;
    email: string;
    provided_services: KeywordInterface[];
    business_concepts: KeywordInterface[];
    partner_services: KeywordInterface[];

    keywords: KeywordInterface[];

    constructor(id: number, name: string) {
        this.id = id;
        this.name = name;
        this.provided_services = [];
        this.business_concepts = [];
        this.partner_services = [];
        this.keywords = [];
        this.name;
        this.location;
    }

    addProvidedService(kw: Keyword) {
        try {
            if (!this.provided_services.find(rec => rec.id === kw.id)) {
                this.provided_services.push(kw);
            }
        } catch (Error) {
            console.log("addProvidedService exception: " + Error);
        }
    }

    removeProvidedService(id: number) {
        this.provided_services = this.provided_services.filter(rec => rec.id !== id);
    }

    addPartnerService(kw: Keyword) {
        try {
            if (!this.partner_services.find(rec => rec.id === kw.id)) {
                this.partner_services.push(kw);
            }
        } catch (Error) {
            console.log("addPartnerService exception: " + Error);
        }
    }

    removePartnerService(id: number) {
        this.partner_services = this.partner_services.filter(rec => rec.id !== id);
    }

    addBusinessConcept(kw: Keyword) {
        try {
            if (!this.business_concepts.find(rec => rec.id === kw.id)) {
                this.business_concepts.push(kw);
            }
        } catch (Error) {
            console.log("addPartnerService exception: " + Error);
        }
    }

    removeBusinessConcept(id: number) {
        this.business_concepts = this.business_concepts.filter(rec => rec.id !== id);
    }

     addKeyword(kw: Keyword) {
        try {
            if (!this.keywords.find(rec => rec.id === kw.id)) {
                this.keywords.push(kw);
            }
        } catch (Error) {
            console.log("addKeyword exception: " + Error);
        }
    }

    removeKeyword(id: number) {
        this.keywords = this.keywords.filter(rec => rec.id !== id);
    }
}