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

export const routes: Routes = [
  { path: 'home', component: AppComponent },
  { path: 'modprofile',  canActivate: [LoggedInGuard], component: ModifyUserProfileComponent},
  { path: 'login', component: LoginComponent},
  { path: 'logout', component: LoginComponent},
  { path: 'keywords', component: KeywordsComponent},
  { path: 'search', canActivate: [LoggedInGuard], component: SearchComponent},
  { path: 'proposals', canActivate: [LoggedInGuard], component: ProposalsComponent},
  { path: 'my-proposals', canActivate: [LoggedInGuard], component: MyProposalsComponent},
  { path: 'partner-proposals', canActivate: [LoggedInGuard], component: PartnerProposalsComponent},
  {path: '**', component: FrontPageComponent }
  
];
