# Digital Banking - Roadmap des Fonctionnalités

## 📚 Références Vidéo du Professeur Youssfi

### Partie 1: Backend avec Spring Boot ✅ (Complété)
Les fondations de l'application sont en place avec:
- Architecture en couches (DAO, Service, Web)
- Héritage Single Table pour les comptes
- API REST complète

### Partie 2: Client Angular
**Vidéo**: https://www.youtube.com/watch?v=bOoPKctcE0s

**État**: ✅ Frontend Angular créé avec:
- Gestion des clients (CRUD)
- Gestion des comptes
- Opérations bancaires (Débit, Crédit, Transfert)
- Interface responsive avec Bootstrap 5

### Partie 3: Sécurité avec Spring Security et JWT
**Vidéo**: https://www.youtube.com/watch?v=n65zFfl9dqA

**État**: 📋 À implémenter (voir Phase 3 ci-dessous)

---

## 🎯 Fonctionnalités Supplémentaires à Implémenter

## Phase 1: Documentation API avec Swagger ✅ (En cours)

### Objectif
Ajouter une interface interactive pour documenter et tester l'API REST.

### Implémentation

#### Backend
- [x] Ajouter la dépendance SpringDoc OpenAPI
  ```xml
  <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.1.0</version>
  </dependency>
  ```

- [x] Créer la configuration OpenAPI
- [ ] Ajouter des annotations sur les contrôleurs:
  - `@Tag` pour grouper les endpoints
  - `@Operation` pour décrire chaque endpoint
  - `@ApiResponse` pour documenter les réponses
  - `@Schema` pour documenter les DTOs

#### Accès
- **Swagger UI**: http://localhost:8085/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8085/v3/api-docs

### Exemple d'Annotations
```java
@RestController
@Tag(name = "Customers", description = "Customer management APIs")
@CrossOrigin("*")
public class CustomerRestController {

    @Operation(
        summary = "Get all customers",
        description = "Returns a list of all customers in the system"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved list",
        content = @Content(schema = @Schema(implementation = CustomerDTO.class))
    )
    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }
}
```

---

## Phase 2: Gestion Complète des Clients et Comptes

### 2.1 Gestion Avancée des Clients

#### Backend - Nouvelles Fonctionnalités
- [ ] **Validation des données**
  - Email unique
  - Format email valide
  - Nom obligatoire (min 3 caractères)
  - Validation des numéros de téléphone (à ajouter)

- [ ] **Recherche avancée**
  - Recherche par nom (partielle)
  - Recherche par email
  - Filtrage par date de création
  - Tri (nom, email, date)

- [ ] **Pagination**
  - Support de pagination pour les listes
  - Paramètres: page, size, sort

- [ ] **Statistiques clients**
  - Nombre total de clients
  - Nouveaux clients par mois
  - Clients actifs/inactifs

#### Frontend - Améliorations
- [ ] **Interface améliorée**
  - Formulaire de recherche avancée
  - Tri sur les colonnes du tableau
  - Pagination avec contrôles
  - Modal pour édition en ligne

- [ ] **Validation côté client**
  - Validateurs personnalisés
  - Messages d'erreur explicites
  - Confirmation avant suppression

### 2.2 Gestion Avancée des Comptes

#### Backend - Nouvelles Fonctionnalités
- [ ] **Création de comptes depuis l'API**
  ```java
  @PostMapping("/accounts/current")
  public CurrentAccountDTO createCurrentAccount(@RequestBody CreateAccountDTO dto);

  @PostMapping("/accounts/saving")
  public SavingAccountDTO createSavingAccount(@RequestBody CreateAccountDTO dto);
  ```

- [ ] **Administration des comptes**
  - Activation/Désactivation de compte
  - Modification du statut
  - Modification des limites (découvert, taux d'intérêt)
  - Clôture de compte

- [ ] **Recherche de comptes**
  - Par client
  - Par type (Current/Saving)
  - Par statut
  - Par plage de solde

- [ ] **Historique détaillé**
  - Export en PDF
  - Export en CSV
  - Filtrage par période
  - Filtrage par type d'opération

#### Frontend - Nouvelles Pages
- [ ] **Page de création de compte**
  - Formulaire avec sélection de type
  - Sélection du client
  - Configuration initiale (solde, découvert, taux)

- [ ] **Page d'administration des comptes**
  - Liste avec filtres
  - Actions en masse
  - Modification rapide des paramètres

---

## Phase 3: Sécurité et Authentification

### 3.1 Backend - Spring Security + JWT

#### Dépendances
```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```

#### Entités à Créer
- [ ] **AppUser**
  ```java
  @Entity
  public class AppUser {
      @Id @GeneratedValue
      private Long id;
      private String username;
      private String password; // BCrypt encoded
      private String email;
      private boolean enabled;
      @ManyToMany(fetch = FetchType.EAGER)
      private Collection<AppRole> roles;
  }
  ```

- [ ] **AppRole**
  ```java
  @Entity
  public class AppRole {
      @Id @GeneratedValue
      private Long id;
      private String roleName; // ROLE_USER, ROLE_ADMIN, ROLE_MANAGER
  }
  ```

#### Services à Implémenter
- [ ] **UserService**
  - Création d'utilisateur
  - Attribution de rôles
  - Changement de mot de passe
  - Gestion des profils

- [ ] **JwtService**
  - Génération de token
  - Validation de token
  - Extraction des informations

#### Configuration Security
- [ ] **SecurityConfig**
  - Configuration des endpoints publics/protégés
  - Configuration JWT Filter
  - Configuration CORS
  - Configuration des rôles

- [ ] **JwtAuthenticationFilter**
  - Interception des requêtes
  - Validation du token
  - Configuration du SecurityContext

### 3.2 Frontend - Authentication

#### Services
- [ ] **AuthService**
  ```typescript
  login(username, password): Observable<AuthResponse>
  logout(): void
  refreshToken(): Observable<AuthResponse>
  isAuthenticated(): boolean
  getCurrentUser(): User
  ```

- [ ] **TokenInterceptor**
  - Ajout du token aux requêtes
  - Gestion de l'expiration
  - Redirection vers login

#### Pages
- [ ] **LoginComponent**
  - Formulaire de connexion
  - Validation
  - Gestion des erreurs

- [ ] **RegisterComponent**
  - Création de compte utilisateur
  - Validation du mot de passe

- [ ] **ProfileComponent**
  - Affichage du profil
  - Modification des informations
  - Changement de mot de passe

#### Guards
- [ ] **AuthGuard**
  - Protection des routes
  - Redirection si non authentifié

- [ ] **RoleGuard**
  - Vérification des rôles
  - Accès basé sur les permissions

---

## Phase 4: Audit Trail et Traçabilité

### 4.1 Audit des Opérations

#### Entité Audit
```java
@Entity
public class AuditLog {
    @Id @GeneratedValue
    private Long id;
    private String entityName; // Customer, BankAccount, Operation
    private Long entityId;
    private String action; // CREATE, UPDATE, DELETE, TRANSFER
    private String performedBy; // Username
    private LocalDateTime performedAt;
    private String oldValue; // JSON
    private String newValue; // JSON
    private String ipAddress;
}
```

#### Implémentation
- [ ] **AuditService**
  - Enregistrement automatique des modifications
  - Consultation de l'historique

- [ ] **AuditAspect (AOP)**
  - Interception des méthodes de service
  - Enregistrement automatique

#### Modifications des Entités
- [ ] Ajouter aux entités:
  ```java
  @CreatedBy
  private String createdBy;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedBy
  private String lastModifiedBy;

  @LastModifiedDate
  private LocalDateTime lastModifiedAt;
  ```

### 4.2 Frontend - Historique d'Audit

- [ ] **Page d'audit**
  - Affichage des logs
  - Filtrage par entité/action/utilisateur
  - Export des logs

---

## Phase 5: Gestion des Utilisateurs et Mots de Passe

### 5.1 Backend

#### Endpoints
- [ ] **UserController**
  ```java
  POST   /api/users/register
  POST   /api/users/login
  POST   /api/users/refresh-token
  GET    /api/users/profile
  PUT    /api/users/profile
  POST   /api/users/change-password
  POST   /api/users/reset-password-request
  POST   /api/users/reset-password
  GET    /api/users (ADMIN only)
  PUT    /api/users/{id}/roles (ADMIN only)
  ```

#### Fonctionnalités
- [ ] **Changement de mot de passe**
  - Validation de l'ancien mot de passe
  - Politique de mot de passe fort
  - Historique des mots de passe

- [ ] **Réinitialisation de mot de passe**
  - Token par email
  - Expiration du token
  - Nouveau mot de passe

- [ ] **Gestion des sessions**
  - Token blacklist
  - Déconnexion multiple devices
  - Historique des connexions

### 5.2 Frontend

#### Pages
- [ ] **ChangePasswordComponent**
  - Ancien mot de passe
  - Nouveau mot de passe
  - Confirmation
  - Indicateur de force

- [ ] **ResetPasswordComponent**
  - Demande par email
  - Saisie du token
  - Nouveau mot de passe

- [ ] **UserManagementComponent** (ADMIN)
  - Liste des utilisateurs
  - Attribution des rôles
  - Activation/Désactivation

---

## Phase 6: Dashboard avec Statistiques

### 6.1 Backend - Endpoints Statistiques

#### Services
- [ ] **DashboardService**
  ```java
  // Statistiques générales
  DashboardStats getGeneralStats();

  // Évolution du nombre de clients
  List<StatPoint> getCustomerGrowth(Period period);

  // Évolution des soldes
  List<StatPoint> getBalanceEvolution(Period period);

  // Répartition des comptes par type
  Map<String, Long> getAccountDistribution();

  // Top transactions
  List<TransactionStat> getTopTransactions(int limit);

  // Revenus par période
  List<RevenueStat> getRevenueStats(Period period);
  ```

#### DTOs
```java
public class DashboardStats {
    private long totalCustomers;
    private long totalAccounts;
    private double totalBalance;
    private long todayTransactions;
    private double todayVolume;
    private long activeAccounts;
    private long suspendedAccounts;
}
```

### 6.2 Frontend - Dashboard avec ChartJS

#### Installation
```bash
npm install chart.js ng2-charts
```

#### Composants
- [ ] **DashboardComponent**
  - Cartes de statistiques (KPIs)
  - Graphiques interactifs
  - Filtres de période

#### Types de Graphiques
- [ ] **Line Chart**: Évolution du nombre de clients
- [ ] **Bar Chart**: Volume de transactions par mois
- [ ] **Pie Chart**: Répartition des comptes (Current vs Saving)
- [ ] **Doughnut Chart**: Répartition par statut
- [ ] **Area Chart**: Évolution des soldes
- [ ] **Mixed Chart**: Comparaison multi-critères

#### Exemple d'Implémentation
```typescript
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  // Line Chart Data
  lineChartData: ChartConfiguration['data'] = {
    datasets: [
      {
        data: [],
        label: 'Nouveaux clients',
        fill: true,
        tension: 0.5,
        borderColor: 'rgb(75, 192, 192)',
        backgroundColor: 'rgba(75, 192, 192, 0.2)'
      }
    ],
    labels: []
  };

  lineChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: true }
    }
  };
}
```

#### Visualisations
- [ ] **KPI Cards**
  - Total clients
  - Total comptes
  - Solde total
  - Transactions du jour

- [ ] **Graphiques temporels**
  - Derniers 7 jours
  - Dernier mois
  - Dernière année
  - Période personnalisée

- [ ] **Tableaux de bord par rôle**
  - Admin: Vue globale
  - Manager: Vue par département
  - User: Vue personnelle

---

## Phase 7: Fonctionnalités Supplémentaires

### 7.1 Notifications

#### Backend
- [ ] **NotificationService**
  - Email notifications (Spring Mail)
  - SMS notifications (Twilio)
  - Push notifications

#### Cas d'Usage
- Transaction effectuée
- Solde faible
- Compte suspendu
- Nouvel utilisateur

### 7.2 Export et Rapports

- [ ] **Export PDF**
  - Relevé de compte
  - Historique des transactions
  - Rapport de statistiques

- [ ] **Export Excel**
  - Liste des clients
  - Liste des comptes
  - Transactions

- [ ] **Génération de rapports**
  - Rapport mensuel
  - Rapport annuel
  - Rapport personnalisé

### 7.3 Multidevise

- [ ] **Support de devises multiples**
  - USD, EUR, MAD, etc.
  - Taux de change
  - Conversion automatique

### 7.4 Virements Programmés

- [ ] **Scheduled Transfers**
  - Virements récurrents
  - Virements différés
  - Gestion des échéances

### 7.5 Limites et Plafonds

- [ ] **Transaction Limits**
  - Limite par opération
  - Limite quotidienne
  - Limite mensuelle
  - Alertes de dépassement

### 7.6 Documents

- [ ] **Document Management**
  - Upload de documents (KYC)
  - Stockage sécurisé
  - Validation des documents

### 7.7 Chat Support

- [ ] **Customer Support**
  - Chat en temps réel (WebSocket)
  - Historique des conversations
  - Système de tickets

### 7.8 Mobile App

- [ ] **Application Mobile**
  - Ionic/React Native
  - Même API backend
  - Fonctionnalités essentielles

---

## 📋 Plan d'Implémentation Recommandé

### Sprint 1 (Semaine 1-2)
- [x] ✅ Setup initial et architecture
- [x] ✅ API REST de base
- [x] ✅ Frontend Angular de base
- [ ] 🔄 Documentation Swagger complète

### Sprint 2 (Semaine 3-4)
- [ ] Sécurité Spring Security + JWT
- [ ] Login/Register frontend
- [ ] Guards et interceptors

### Sprint 3 (Semaine 5-6)
- [ ] Audit trail et traçabilité
- [ ] Gestion des utilisateurs
- [ ] Changement de mot de passe

### Sprint 4 (Semaine 7-8)
- [ ] Dashboard backend (statistiques)
- [ ] Dashboard frontend (ChartJS)
- [ ] KPIs et graphiques

### Sprint 5 (Semaine 9-10)
- [ ] Gestion avancée des clients
- [ ] Gestion avancée des comptes
- [ ] Export PDF/Excel

### Sprint 6 (Semaine 11-12)
- [ ] Notifications
- [ ] Fonctionnalités bonus
- [ ] Tests et optimisations

---

## 🛠️ Technologies Requises

### Backend
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- JWT (jjwt)
- SpringDoc OpenAPI
- iText (PDF) / Apache POI (Excel)
- Spring Mail

### Frontend
- Angular 15+
- Bootstrap 5
- Chart.js + ng2-charts
- RxJS
- Angular JWT
- File Saver (exports)

### Outils
- Postman (tests API)
- Maven
- Git
- IDE (IntelliJ IDEA / VS Code)

---

## 📖 Documentation de Référence

1. **Spring Security + JWT**
   - https://www.youtube.com/watch?v=n65zFfl9dqA

2. **Angular Client**
   - https://www.youtube.com/watch?v=bOoPKctcE0s

3. **Swagger/OpenAPI**
   - https://springdoc.org/

4. **Chart.js**
   - https://www.chartjs.org/
   - https://valor-software.com/ng2-charts/

5. **Spring Security**
   - https://spring.io/projects/spring-security

---

## ✅ Critères de Succès

### Fonctionnalités
- [ ] Authentification sécurisée fonctionnelle
- [ ] Tous les CRUD complets et testés
- [ ] Audit trail sur toutes les opérations
- [ ] Dashboard avec au moins 5 graphiques
- [ ] Documentation API complète
- [ ] Tests unitaires > 70% coverage

### Performance
- [ ] Temps de réponse < 200ms
- [ ] Support de 1000+ utilisateurs concurrents
- [ ] Pas de failles de sécurité

### UX/UI
- [ ] Interface responsive
- [ ] Feedback utilisateur sur toutes les actions
- [ ] Navigation intuitive
- [ ] Accessibilité (WCAG 2.1)

---

## 🎓 Compétences Développées

En complétant ce projet, vous maîtriserez:
- Architecture Full Stack moderne
- Sécurité des applications web
- Gestion d'état et JWT
- Visualisation de données
- Audit et traçabilité
- Bonnes pratiques de développement
- Documentation API
- Tests et qualité du code

---

**Bonne chance dans votre développement! 🚀**
