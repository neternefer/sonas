import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MainFormService } from '../services/main-form.service';

@Component({
  selector: 'app-main-form',
  templateUrl: './main-form.component.html',
  styleUrls: ['./main-form.component.css']
})
export class MainFormComponent implements OnInit {

  Social: any = ['Facebook', 'Twitter', 'Linkedin', 'Github'];
  address!: FormGroup;
  contact!: FormGroup;
  socialLink: any;

  constructor(private fb: FormBuilder,
              private service: MainFormService) {
    
  }
  
  ngOnInit(): void {
    this.address= this.fb.group({
      street: ['', [Validators.required, Validators.minLength(2)]],
      city: ['', [Validators.required, Validators.minLength(2)]],
      state: ['', [Validators.required, Validators.minLength(2)]],
      zip: ['', [Validators.required, Validators.minLength(2)]],
    });
    this.contact = this.fb.group({
      phone: ['', [Validators.required, Validators.minLength(9)]],
      socialLink: ['', [Validators.required]]
    });
  }

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.log(this.address.value);
  }

  changeSocial(e: any) {
    this.socialLink.setValue(e.target.value, {
      onlySelf: true
    })
  }

}
