import { MlSpinnerMod } from './shared/components/ml/components/spinner/mlSpinnerMod';
import { ProposalService } from './shared/services/proposal.service';
import { MlChipMod } from './shared/components/ml/components/chip/mlChipMod';
import { MlTitleMod } from './shared/components/ml/components/title/mlTitleMod';
import { MlGridMod } from './shared/components/ml/components/grid/mlGridMod';
import { MlTooltipMod } from './shared/components/ml/components/tooltip/mlTooltipMod';
//import { editalbleMod } from './shared/components/pa/editable/editableMod';
import { LoggedInGuard } from './shared/guards/logged-in.guard';

import { UserService } from './shared/services/user.service';

import { SortKeyword } from './shared/pipes/sort-keyword';
import { MlToggleMod } from './shared/components/ml/components/controls/toggle/mlToggleMod';
import { KeywordPipe } from './shared/pipes/keyword-pipe';
import { MlRippleMod } from './shared/components/ml/components/ripple/mlRippleMod';
import { MlTextfieldMod } from './shared/components/ml/components/controls/textfield/mlTextfieldMod';
import { MlDialogMod } from './shared/components/ml/components/dialog/mlDialogMod';
import { MlListMod } from './shared/components/ml/components/list/mlListMod';
import { UserProfileService } from './shared/services/user-profile-service.service';
import { KeywordServiceService } from './shared/services/keyword-service.service';
import { MlRadioMod } from './shared/components/ml/components/controls/radio/mlRadioMod';
import { MlTabsMod } from './shared/components/ml/components/tabs/mlTabsMod';
import { MlPageLoaderMod } from './shared/components/ml/components/loader/mlContentLoaderMod';
import { MlIconMod } from './shared/components/ml/components/icon/mlIconMod';
import { MlLayoutMod } from './shared/components/ml/components/layout/mlLayoutMod';
import { MlValidationErrorMod } from './shared/components/ml/components/controls/error/mlValidationErrorMod';
import { MlSelectfieldMod } from './shared/components/ml/components/controls/selectfield/mlSelectfieldMod';
import { MlSwitchMod } from './shared/components/ml/components/controls/switch/mlSwitchMod';
import { MlTableMod } from './shared/components/ml/components/table/mlTableMod';
import { MlButtonMod } from './shared/components/ml/components/controls/button/mlButtonMod';
import { MlButton } from './shared/components/ml/components/controls/button/mlButton';
import { MlMenuMod } from './shared/components/ml/components/menu/mlMenuMod';
import { MlCardMod } from './shared/components/ml/components/card/mlCardMod';

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { routes } from './app.routes';

import { AppComponent } from './app.component';

import { MlMenu } from './shared/components/ml/components/menu/mlMenu';
import { ReactiveFormsModule } from '@angular/forms';
import { KeywordsComponent } from './shared/views/keywords/keywords.component';
import { ModifyUserProfileComponent } from './shared/views/user-profiles/modify-user-profile/modify-user-profile.component';
import { MdlModule } from '@angular-mdl/core';
import { LoginComponent } from './shared/components/pa/login/login.component';
import { FrontPageComponent } from './shared/views/front-page/front-page.component';
import { SearchComponent } from './shared/views/search/search.component';
import { MyProposalsComponent } from './shared/views/proposals/my.proposals.component';
import { PartnerProposalsComponent } from "app/shared/views/proposals/partner.proposals.component";
import { ProposalsComponent } from './shared/views/proposals/proposals.component';
import { RegistrationComponent } from './shared/components/pa/registration/registration.component';
import { PublicProposalsComponent } from './shared/views/proposals/public-proposals.component';
import { SortByDirective } from "app/shared/directives/sort-by.directive";
import { ContactFormComponent } from "app/shared/components/pa/contact-form/contact-form.component";




@NgModule({
  declarations: [
    AppComponent,
    KeywordsComponent,
    ModifyUserProfileComponent,
    KeywordPipe,
    SortKeyword,
    LoginComponent,
    FrontPageComponent,
    SearchComponent,
    MyProposalsComponent,
    PartnerProposalsComponent,
    ProposalsComponent,
    RegistrationComponent,
    PublicProposalsComponent,
    ContactFormComponent,
    SortByDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MlCardMod,
    MlMenuMod,
    MlTableMod,
    MlSwitchMod,
    MlSelectfieldMod, 
    MlValidationErrorMod, 
    MlLayoutMod,  
    MlIconMod,
    MlPageLoaderMod,
    MlTabsMod,
    MlRadioMod,
    MlListMod,
    MlDialogMod,
    MlTextfieldMod,
    MlRippleMod,
    MlToggleMod,
    MlTooltipMod,
    MlGridMod,
    MlTitleMod,
    MlChipMod,
    MlSpinnerMod,

    ReactiveFormsModule,
    MdlModule,
    RouterModule.forRoot(routes, { useHash: true })
    
  ],
  entryComponents: [
  ],
  providers: [KeywordServiceService, UserProfileService, UserService, LoggedInGuard, ProposalService],
  
  bootstrap: [AppComponent]
})
export class AppModule { }
