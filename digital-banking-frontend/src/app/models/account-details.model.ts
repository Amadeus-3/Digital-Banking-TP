import { AccountOperation } from './account-operation.model';

export interface AccountDetails {
  accountId: string;
  balance: number;
  currentPage: number;
  totalPages: number;
  pageSize: number;
  accountOperationDTOs: AccountOperation[];
}
