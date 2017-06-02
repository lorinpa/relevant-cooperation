import {Pipe,PipeTransform} from '@angular/core';


@Pipe({  name: "keywordFilter"}) 
export class KeywordPipe implements PipeTransform {
     transform(value: any, args: string[]): any {
       var lower_args = args.map(function(rec) {
               return rec.toLowerCase;
       });
       if (value !== undefined && value !== null && value.length)
        return value.filter(rec=> {
                    if ( rec['keyword'] && rec['keyword'].includes(lower_args)) return true; 
            });    
    } 

    
}
