import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { CvComponent } from './cv/cv.component';
import { JobComponent } from './job/job.component';
import { MainFormComponent } from './main-form/main-form.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserBoardComponent } from './user-board/user-board.component';
import { AdminBoardComponent } from './admin-board/admin-board.component';

import { authInterceptorProviders } from '../helpers/auth.interceptor';

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
    AdminBoardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
