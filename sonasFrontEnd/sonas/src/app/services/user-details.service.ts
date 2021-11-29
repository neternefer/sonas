import { User } from '../models/user';
import { Contact } from '../models/contact';
import { Address } from '../models/address';
import { Social } from '../models/social';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class UserDetailsService {

  private baseUrl = 'http://localhost:8100/api/users';

  constructor(private http: HttpClient) { }

  getUserDetails(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  getAllUserDetails(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}`);
  }

  createUserDetails(user: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}/new`, user);
  }

  updateUserDetails(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/update/${id}`, value);
  }

  deleteUserDetails(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, { responseType: 'text' });
  }

  getContacts(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/contact/${id}`);
  }

  getAllContacts(): Observable<Contact[]> {
    return this.http.get<Contact[]>(`${this.baseUrl}/contact`);
  }

  createContact(contact: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}/contact/new`, contact);
  }

  updateContact(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/contact/update/${id}`, value);
  }

  deleteContact(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/contact/delete/${id}`, { responseType: 'text' });
  }

  getAddress(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/address/${id}`);
  }

  getAllAddresses(): Observable<Address[]> {
    return this.http.get<Address[]>(`${this.baseUrl}/address`);
  }

  createAddress(contact: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}/address/new`, contact);
  }

  updateAddress(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/address/update/${id}`, value);
  }

  deleteAddress(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/address/delete/${id}`, { responseType: 'text' });
  }

  getSocial(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/social/${id}`);
  }

  getAllSocialLinks(): Observable<Social[]> {
    return this.http.get<Social[]>(`${this.baseUrl}/social`);
  }

  createSocial(contact: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}/social/new`, contact);
  }

  updateSocial(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/social/update/${id}`, value);
  }

  deleteSocial(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/social/delete/${id}`, { responseType: 'text' });
  }
}
