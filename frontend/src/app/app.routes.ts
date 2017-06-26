import { PartnerProposalsComponent } from 'app/shared/views/proposals/partner.proposals.component';
import { MyProposalsComponent } from './shared/views/proposals/my.proposals.component';
import { SearchComponent } from './shared/views/search/search.component';
import { FrontPageComponent } from './shared/views/front-page/front-page.component';
import { LoggedInGuard } from './shared/guards/logged-in.guard';
import { LoginComponent } from './shared/components/pa/login/login.component';
import { ModifyUserProfileComponent } from './shared/views/user-profiles/modify-user-profile/modify-user-profile.component';
import { KeywordsComponent } from './shared/views/keywords/keywords.component';
import { Routes } from '@angular/router';
import {AppComponent} from './app.component';
import { ProposalsComponent } from "app/shared/views/proposals/proposals.component";
import { PublicProposalsComponent } from "app/shared/views/proposals/public-proposals.component";
import { RegistrationComponent } from "app/shared/components/pa/registration/registration.component";
import { ContactFormComponent } from "app/shared/components/pa/contact-form/contact-form.component";



export const routes: Routes = [
  { path: 'home', component: AppComponent },
  { path: 'modprofile',  canActivate: [LoggedInGuard], component: ModifyUserProfileComponent},
  { path: 'login', component: LoginComponent},
  { path: 'logout', component: LoginComponent},
  { path: 'register', component: RegistrationComponent},
  { path: 'keywords', component: KeywordsComponent},
  { path: 'search', canActivate: [LoggedInGuard], component: SearchComponent},
  { path: 'proposals', canActivate: [LoggedInGuard], component: ProposalsComponent},
  { path: 'my-proposals', canActivate: [LoggedInGuard], component: MyProposalsComponent},
  { path: 'partner-proposals', canActivate: [LoggedInGuard], component: PartnerProposalsComponent},
  { path: 'public-proposals', canActivate: [LoggedInGuard], component: PublicProposalsComponent},
  { path: 'contact', component: ContactFormComponent },
  { path: '**', component: FrontPageComponent }
  
];
