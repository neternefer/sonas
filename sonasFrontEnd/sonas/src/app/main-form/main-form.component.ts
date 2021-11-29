import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { UserDetailsService } from '../services/user-details.service';
import { Country, Countries } from '../models/country';
import { MainFormService } from '../services/main-form.service';

@Component({
  selector: 'app-main-form',
  templateUrl: './main-form.component.html',
  styleUrls: ['./main-form.component.css']
})

export class MainFormComponent implements OnInit {

  countries: Array<Country> = Countries;
  socialList: Array<any>;
  degreeList: Array<any>;
  technologyList: Array<any>;
  userTypeList: Array<any>;

  user!: FormGroup;
  address!: FormGroup;
  contact!: FormGroup;
  education!: FormGroup;
  experience!: FormGroup;
  technology!: FormGroup;
  social!: FormGroup;

  socialLink: any;
  lastDegree: any;
  countryName: any;
  country: any;

  constructor(private fb: FormBuilder,
              private userDetailService: UserDetailsService,
              private mainFormService: MainFormService) {
      this.socialList = mainFormService.Social;   
      this.degreeList = mainFormService.Degree;    
      this.technologyList = mainFormService.Technology;   
      this.userTypeList = mainFormService.UserType;
  }
  
  ngOnInit(): void {
    this.user= this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required, Validators.minLength(8)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      userType: ['', Validators.required],
      contactId: ['', Validators.required, Validators.minLength(1)]
    });
    this.address= this.fb.group({
      street: ['', [Validators.required, Validators.minLength(2)]],
      city: ['', [Validators.required, Validators.minLength(2)]],
      state: ['', [Validators.required, Validators.minLength(2)]],
      zip: ['', [Validators.required, Validators.minLength(2)]],
      countryName: ['', Validators.required]
    });
    this.contact = this.fb.group({
      phone: ['', [Validators.required, Validators.minLength(9)]],
      socialLink: ['']
    });
    this.education = this.fb.group({
      schoolName: ['', [Validators.required, Validators.minLength(10)]],
      schoolStart: ['', Validators.required],
      schoolend: ['', Validators.required],
      studyField: ['', [Validators.required, Validators.minLength(2)]],
      lastDegree: ['', Validators.required]
    });
    this.experience = this.fb.group({
      position: ['', [Validators.required, Validators.minLength(5)]],
      company: ['', Validators.required],
      jobStart: ['', Validators.required],
      jobend: ['', Validators.required],
      duties: ['', Validators.required]
    });
    this.social = this.fb.group({
      socialLink: ['']
    });
    this.technology = this.fb.group({
      checkArray: this.fb.array([])
    });

    this.address.get('countryName')?.valueChanges
      .subscribe(countryName => console
      .log('this.countryFormGroup.get("countryName").valueChanges', countryName));

    this.address.valueChanges
      .subscribe(countryName => console
      .log('this.countryFormControl.valueChanges', countryName));
  }

  changeUserType($event: any) {}

  onCountrySelected($event: any) {
    console.log($event);
  }
  
  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.log(this.address.value);
  }

  selectCountry(code: Country) {
    console.log("Selected country code object: ", code);
  }

  changeSocial(e: any) {
    this.socialLink.setValue(e.target.value, {
      onlySelf: true
    })
  }

  changeDegree(e: any) {
    this.lastDegree.setValue(e.target.value, {
      onlySelf: true
    })
  }

  onCheckboxChange(e: any) {
    const checkArray: FormArray = this.technology.get('checkArray') as FormArray;
  
    if (e.target.checked) {
      checkArray.push(new FormControl(e.target.value));
    } else {
      let i: number = 0;
      checkArray.controls.forEach((item: any) => {
        if (item.value == e.target.value) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }
}
