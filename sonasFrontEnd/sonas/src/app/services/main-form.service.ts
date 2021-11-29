import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class MainFormService {

  Social: any = ['Facebook', 'Twitter', 'Linkedin', 'Github'];

  Degree: any = ["High School Diploma", "Professional Certificate", "BA/BSc","MA/MSc","PhD", "other"];

  Technology: Array<any> = [
    { name: 'Android', value: 'android' },
    { name: 'Java', value: 'java' },
    { name: 'Kotlin', value: 'kotlin' },
    { name: 'Objective C', value: 'objectiveC' },
    { name: 'Swift', value: 'swift' },
    { name: 'JavaScript', value: 'javascript' },
    { name: 'Express', value: 'express' },
    { name: 'Ruby', value: 'ruby' },
    { name: 'NodeJS', value: 'nodejs' },
    { name: 'Rails', value: 'rails' },
    { name: 'Sinatra', value: 'sinatra' },
    { name: 'PHP', value: 'php' },
    { name: 'Larvel', value: 'larvel' },
    { name: 'Python', value: 'python' },
    { name: 'Django', value: 'django' },
    { name: 'Flask', value: 'flask' },
    { name: 'Scala', value: 'scala' },
    { name: 'MySQL', value: 'mysql' },
    { name: 'PostgreSQL', value: 'postgresql' },
    { name: 'SQLite', value: 'sqlite' },
    { name: 'MongoDB', value: 'mongodb' },
    { name: 'Cassandra', value: 'cassandra' },
    { name: 'ReactJS', value: 'reactjs' },
    { name: 'VueJS', value: 'vuejs' },
    { name: 'AngularJS', value: 'angulajs' },
    { name: 'Docker', value: 'docker' },
    { name: 'Kubernetes', value: 'kubernetes' },
    { name: 'AWS', value: 'aws' },
    { name: 'Azure', value: 'azure' },
    { name: 'Google Cloud', value: 'googlecloud' },
    { name: 'C++', value: 'c++' },
    { name: 'C', value: 'c' },
    { name: 'C#', value: 'c#' },
    { name: 'R', value: 'r' },
    { name: 'SQL', value: 'sql' }
  ];

  UserType: any = ["BASIC", "PREMIUM", "GOLD"];

  constructor() {}
}
