import {EditableFieldInterface} from './editable-field-interface';
import {EditableButtonInterface} from './editable-button-interface';

export interface EditableGroup {
    setRowActive();
    registerEditableField(EditableFieldInterface);
    registerEditableButton(EditableButtonInterface);
    updateData();
    deleteData();
    reset();
}
