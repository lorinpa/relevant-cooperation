import {Pipe,PipeTransform} from '@angular/core';


@Pipe({  name: "keywordFilter"}) 
export class KeywordPipe implements PipeTransform {
     transform(value: any, args: string): any {
       
        
       if (value !== undefined && value !== null && value.length)
        return value.filter(rec=> {
                    if ( rec['keyword'] && rec['keyword'].includes(args.toLowerCase())) return true; 
            });    
    } 

    
}
