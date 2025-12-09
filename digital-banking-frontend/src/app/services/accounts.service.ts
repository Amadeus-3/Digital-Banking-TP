import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountDetails } from '../models/account-details.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  private backendHost = environment.backendHost;

  constructor(private http: HttpClient) { }

  public getAccount(accountId: string, page: number, size: number): Observable<AccountDetails> {
    return this.http.get<AccountDetails>(
      `${this.backendHost}/accounts/${accountId}/pageOperations?page=${page}&size=${size}`
    );
  }

  public debit(accountId: string, amount: number, description: string): Observable<void> {
    const data = { accountId, amount, description };
    return this.http.post<void>(`${this.backendHost}/accounts/debit`, data);
  }

  public credit(accountId: string, amount: number, description: string): Observable<void> {
    const data = { accountId, amount, description };
    return this.http.post<void>(`${this.backendHost}/accounts/credit`, data);
  }

  public transfer(
    sourceAccountId: string,
    destinationAccountId: string,
    amount: number,
    description: string
  ): Observable<void> {
    const data = { sourceAccountId, destinationAccountId, amount, description };
    return this.http.post<void>(`${this.backendHost}/accounts/transfer`, data);
  }
}
