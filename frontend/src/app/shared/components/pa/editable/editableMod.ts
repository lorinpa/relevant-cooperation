import { MlButtonMod } from './../../ml/components/controls/button/mlButtonMod';
import { ContainerComponent } from './container/container.component';
import { ButtonContainerComponent } from './buttons/button-container/button-container.component';
import { DeleteComponent } from './buttons/delete/delete.component';
import { CancelComponent } from './buttons/cancel/cancel.component';
import { UpdateComponent } from './buttons/update/update.component';
import { InputComponent } from './input/input.component';
import { DisplayComponent } from './display/display.component';
import {NgModule} from "@angular/core";

@NgModule({
    imports: [MlButtonMod],
    entryComponents:[DisplayComponent,
                    DeleteComponent,
                    CancelComponent,
                    UpdateComponent,
                    InputComponent
                    ],
    declarations: [ InputComponent, 
                    DisplayComponent,
                    CancelComponent,
                    DeleteComponent,
                    ContainerComponent,
                    ButtonContainerComponent, 
                    UpdateComponent], 
    exports: [  InputComponent, 
                DisplayComponent,
                CancelComponent,
                DeleteComponent,
                ContainerComponent,
                ButtonContainerComponent, 
                UpdateComponent]}) export class editalbleMod{}
