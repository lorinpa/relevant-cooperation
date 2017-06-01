import { Sorter } from './../utils/sorter';

import {Pipe,PipeTransform} from '@angular/core';

@Pipe({  name: "keywordSort"}) 
export class SortKeyword implements PipeTransform {
     transform(value: any[],  dir:number) : any[] {
       this.direction = dir;
       //return this.sort(value,prop)
       if (value)
        return this.sort(value,'keyword');
       
    } 

    property: string = null;
    direction: number = 1;

    sort(collection: any[], prop: any) : any[] {
       
        this.property = prop;
        this.direction = (this.property === prop) ? this.direction * -1 : 1;
       // console.log("sorting: "+ this.direction);
       // console.log("property: "+ this.property);

        collection.sort((a: any, b: any) => {
            let aVal: any;
            let bVal: any;

            //Handle resolving complex properties such as 'state.name' for prop value
            if (prop && prop.indexOf('.')) {
                aVal = this.resolveProperty(prop, a);
                bVal = this.resolveProperty(prop, b);
            }
            else {
                aVal = a[prop];
                bVal = b[prop];
            }

            //Fix issues that spaces before/after string value can cause such as ' San Francisco'
            if (this.isString(aVal)) aVal = aVal.trim().toUpperCase();
            if (this.isString(bVal)) bVal = bVal.trim().toUpperCase();

            if (aVal === bVal) {
                return 0;
            }
            else if (aVal > bVal) {
                return this.direction * -1;
            }
            else {
                return this.direction * 1;
            }
        });
        //console.log("first value: "+ collection[0].keyword);
        return collection;
        
    }

    isString(val: any): boolean {
        return (val && (typeof val === 'string' || val instanceof String));
    }

    resolveProperty(path: string, obj: any) {
        return path.split('.').reduce(function (prev, curr) {
            return (prev ? prev[curr] : undefined)
        }, obj || self)
    }
}
