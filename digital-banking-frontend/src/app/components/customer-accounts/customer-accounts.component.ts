import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from '../../models/customer.model';

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrls: ['./customer-accounts.component.css']
})
export class CustomerAccountsComponent implements OnInit {
  customerId!: string;
  customer!: Customer;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Get customer from router state
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras?.state) {
      this.customer = navigation.extras.state as Customer;
    }
  }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['id'];
  }
}
