# Digital Banking - Angular Frontend

A modern Angular frontend application for the Digital Banking system, featuring customer management, account operations, and transaction handling.

## Technical Stack

- **Angular**: 15.x
- **Bootstrap**: 5.2.3
- **Bootstrap Icons**: 1.10.3
- **RxJS**: 7.8.0
- **TypeScript**: 4.9.4

## Project Structure

```
src/
├── app/
│   ├── components/
│   │   ├── navbar/              # Navigation component
│   │   ├── customers/           # Customer listing and search
│   │   ├── new-customer/        # Customer creation form
│   │   ├── accounts/            # Account operations and history
│   │   └── customer-accounts/   # Customer's accounts view
│   ├── models/                  # TypeScript interfaces
│   │   ├── customer.model.ts
│   │   ├── account-operation.model.ts
│   │   └── account-details.model.ts
│   ├── services/                # HTTP services
│   │   ├── customer.service.ts
│   │   └── accounts.service.ts
│   ├── app-routing.module.ts    # Route configurations
│   ├── app.module.ts            # Main module
│   └── app.component.ts         # Root component
├── environments/                # Environment configurations
│   ├── environment.ts
│   └── environment.prod.ts
├── index.html                   # Main HTML file
├── main.ts                      # Application entry point
└── styles.css                   # Global styles
```

## Features

### 1. Customer Management
- **Search Customers**: Search by name with real-time filtering
- **Create Customer**: Form validation with required fields and email validation
- **Delete Customer**: Confirmation dialog before deletion
- **View Customer Accounts**: Navigate to customer-specific accounts

### 2. Account Operations
- **Search Account**: Look up account by ID with pagination
- **View Balance**: Real-time account balance display
- **Operations History**: Paginated transaction history
- **Perform Transactions**:
  - **Debit**: Withdraw from account
  - **Credit**: Deposit to account
  - **Transfer**: Send money between accounts

### 3. Navigation
- Bootstrap dark navbar with dropdown menus
- Active route highlighting
- Responsive design for mobile devices

## Prerequisites

- Node.js (v16 or higher)
- npm (v8 or higher)
- Backend API running on `http://localhost:8085`

## Installation

1. **Navigate to the project directory**:
   ```bash
   cd digital-banking-frontend
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

## Running the Application

### Development Server

```bash
npm start
```

or

```bash
ng serve
```

Navigate to `http://localhost:4200/`. The application will automatically reload if you change any source files.

### Production Build

```bash
npm run build
```

The build artifacts will be stored in the `dist/` directory.

## Backend Configuration

The backend host is configured in the environment files:

**Development** ([environment.ts](src/environments/environment.ts)):
```typescript
export const environment = {
  production: false,
  backendHost: "http://localhost:8085"
};
```

**Production** ([environment.prod.ts](src/environments/environment.prod.ts)):
```typescript
export const environment = {
  production: true,
  backendHost: "http://localhost:8085"
};
```

## API Endpoints Used

### Customer Endpoints
- `GET /customers` - List all customers
- `GET /customers/search?keyword={keyword}` - Search customers
- `POST /customers` - Create customer
- `DELETE /customers/{id}` - Delete customer

### Account Endpoints
- `GET /accounts/{accountId}/pageOperations?page={page}&size={size}` - Get account with operations
- `POST /accounts/debit` - Debit from account
- `POST /accounts/credit` - Credit to account
- `POST /accounts/transfer` - Transfer between accounts

## Component Details

### NavbarComponent
- Bootstrap navbar with routing links
- Dropdown menu for customer operations
- Mobile responsive with hamburger menu

### CustomersComponent
- Search functionality with reactive forms
- Data table with delete and view accounts actions
- Error handling with user-friendly messages

### NewCustomerComponent
- Reactive form with validation
- Name: Required, minimum 4 characters
- Email: Required, valid email format
- Success/error alerts

### AccountsComponent
- Two-column layout (account info + operations)
- Account search with pagination
- Transaction operations (Debit, Credit, Transfer)
- Dynamic form fields based on operation type
- Real-time balance updates

### CustomerAccountsComponent
- Displays customer information
- Retrieves data from router state
- Placeholder for account listing functionality

## Styling

The application uses:
- **Bootstrap 5** for layout and components
- **Bootstrap Icons** for visual elements
- Custom CSS for additional styling
- Responsive design principles

## Form Validation

### New Customer Form
- **Name**: Required, minimum 4 characters
- **Email**: Required, valid email format

### Account Operations Form
- **Amount**: Required, must be greater than 0
- **Description**: Required
- **Account Destination**: Required for transfers

## Error Handling

- HTTP errors are caught and displayed to users
- Confirmation dialogs for destructive operations
- Form validation prevents invalid submissions
- Alert messages for operation results

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Development Tips

1. **Enable CORS** on the backend for local development
2. **Update backend port** in environment files if different from 8085
3. **Bootstrap dropdown** requires Bootstrap JavaScript bundle (included in index.html)
4. **Hot reload** is enabled for rapid development

## Future Enhancements

- Customer accounts listing API integration
- Account creation functionality
- Transaction filtering and search
- Export operations to PDF/CSV
- Real-time notifications
- User authentication and authorization
- Dashboard with statistics

## License

This project is for educational purposes based on Professor Mohamed Youssfi's Digital Banking architecture.
