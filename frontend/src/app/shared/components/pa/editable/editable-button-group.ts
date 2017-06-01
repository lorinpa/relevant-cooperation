
/*
    this class will control the toggle logic.
    which button(s) shoud be displayed
*/

export const EDITABLE_OPERATIONS = {
    UPDATE: 1,
    DELETE: 2,
    CANCEL: 3,
    CONFIRM_UPDATE: 4,
    CONFIRM_DELETE: 5
}


export class EditableButtonGroup {
    current_operation: number;
    
}
