import { AbstractControl, ValidatorFn, Validator, ValidationErrors } from '@angular/forms';

export function unique_value(): ValidatorFn {

    return (control: AbstractControl): { [key: string]: any } => {

     // messy but you get the idea
    var containsError = false;
    let errorSet = control.errors;
    if (errorSet) {
      if (errorSet['unique_value']) {
        containsError = errorSet['unique_value'];
        
      }
    }
   // if (errorSet) control.setErrors( { 'unique_value': true });
    
    return containsError ?  { 'unique_value': true } : null

  };
 


  
}