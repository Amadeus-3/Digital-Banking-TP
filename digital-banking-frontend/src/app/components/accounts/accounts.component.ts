import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountDetails } from '../../models/account-details.model';
import { AccountsService } from '../../services/accounts.service';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  accountFormGroup!: FormGroup;
  operationFormGroup!: FormGroup;
  currentPage: number = 0;
  pageSize: number = 5;
  accountObservable!: Observable<AccountDetails>;
  operationTypes: string[] = ['DEBIT', 'CREDIT', 'TRANSFER'];

  constructor(
    private fb: FormBuilder,
    private accountsService: AccountsService
  ) { }

  ngOnInit(): void {
    this.accountFormGroup = this.fb.group({
      accountId: this.fb.control('', [Validators.required])
    });

    this.operationFormGroup = this.fb.group({
      operationType: this.fb.control('DEBIT'),
      amount: this.fb.control(0, [Validators.required, Validators.min(1)]),
      description: this.fb.control('', [Validators.required]),
      accountDestination: this.fb.control('')
    });
  }

  handleSearchAccount() {
    let accountId: string = this.accountFormGroup.value.accountId;
    this.accountObservable = this.accountsService.getAccount(accountId, this.currentPage, this.pageSize).pipe(
      catchError(err => {
        alert('Account not found or error occurred!');
        return throwError(err);
      })
    );
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
    let accountId: string = this.accountFormGroup.value.accountId;
    let operationType = this.operationFormGroup.value.operationType;
    let amount: number = this.operationFormGroup.value.amount;
    let description: string = this.operationFormGroup.value.description;
    let accountDestination: string = this.operationFormGroup.value.accountDestination;

    if (operationType === 'DEBIT') {
      this.accountsService.debit(accountId, amount, description).subscribe({
        next: () => {
          alert('Debit operation successful!');
          this.operationFormGroup.reset();
          this.operationFormGroup.patchValue({ operationType: 'DEBIT' });
          this.handleSearchAccount();
        },
        error: err => {
          alert('Error: ' + err.error.message || 'Debit operation failed!');
        }
      });
    } else if (operationType === 'CREDIT') {
      this.accountsService.credit(accountId, amount, description).subscribe({
        next: () => {
          alert('Credit operation successful!');
          this.operationFormGroup.reset();
          this.operationFormGroup.patchValue({ operationType: 'DEBIT' });
          this.handleSearchAccount();
        },
        error: err => {
          alert('Error: ' + err.error.message || 'Credit operation failed!');
        }
      });
    } else if (operationType === 'TRANSFER') {
      this.accountsService.transfer(accountId, accountDestination, amount, description).subscribe({
        next: () => {
          alert('Transfer operation successful!');
          this.operationFormGroup.reset();
          this.operationFormGroup.patchValue({ operationType: 'DEBIT' });
          this.handleSearchAccount();
        },
        error: err => {
          alert('Error: ' + err.error.message || 'Transfer operation failed!');
        }
      });
    }
  }

  get operationType() {
    return this.operationFormGroup.value.operationType;
  }
}
