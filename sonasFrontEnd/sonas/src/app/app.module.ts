import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatListModule } from '@angular/material/list';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { CvComponent } from './cv/cv.component';
import { JobComponent } from './job/job.component';
import { MainFormComponent } from './main-form/main-form.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserBoardComponent } from './user-board/user-board.component';
import { AdminBoardComponent } from './admin-board/admin-board.component';

import { authInterceptorProviders } from '../helpers/auth.interceptor';
import { UserComponent } from './user/user.component'; 
import { UserDetailsComponent } from './user-details/user-details.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    UserComponent,
    PortfolioComponent,
    CvComponent,
    JobComponent,
    MainFormComponent,
    LoginComponent,
    RegisterComponent,
    UserBoardComponent,
    AdminBoardComponent,
    UserDetailsComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatDatepickerModule, 
    MatFormFieldModule,
    MatNativeDateModule,
    MatListModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
