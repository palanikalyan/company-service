# Login and Admin Dashboard Setup

## ✅ What Was Done

### Backend Changes (Spring Boot)

1. **Converted to H2 Database** - No Docker needed!
   - Updated `application.yml` to use H2 in-memory database
   - H2 console available at: `http://localhost:8082/h2-console`
   - JDBC URL: `jdbc:h2:mem:freelance`
   - Username: `sa`, Password: (empty)

2. **Added Login API** (`/api/admin/login`)
   - Takes username and password
   - Returns user info (id, username, role, email)
   - Added DTOs: `LoginRequest.java` and `LoginResponse.java`
   - Updated `UserService` and `UserServiceImpl` with `findByUsername` method

3. **Seed Data** (`data.sql`)
   - **Admin User**: username=`admin`, password=`admin123`, role=`ADMIN`
   - **Company Users**: `neha` (pass: 12345), `kiran` (pass: password)
   - Pre-populated 5 companies with bank details

### Frontend Changes (Angular)

1. **Login Component** (`/login`)
   - Beautiful form with Tailwind CSS
   - Validates credentials via backend API
   - Redirects admin users to `/admin/dashboard`
   - Redirects company users to `/company/dashboard`

2. **Admin Dashboard** (`/admin/dashboard`)
   - **Two Tabs**: Companies and Users
   - **Companies Tab**: View all companies, activate/deactivate, delete
   - **Users Tab**: View all users, delete (except admin)
   - Shows current logged-in admin name
   - Logout button

3. **Auth Service** (`auth.service.ts`)
   - Handles login/logout
   - Stores user in localStorage
   - Checks if user is admin
   - Used by login and dashboard components

4. **Updated Routes** (`app.routes.ts`)
   - Default route (`/`) → redirects to `/login`
   - `/login` → Login page
   - `/admin/dashboard` → Admin dashboard (for ADMIN role)
   - `/company/dashboard` → Company list (for COMPANY role)

## 🚀 How to Run

### Backend (Spring Boot)
```powershell
cd demo
mvn spring-boot:run
```
- Backend runs on: `http://localhost:8082`
- H2 Console: `http://localhost:8082/h2-console`
- Swagger UI: `http://localhost:8082/swagger-ui/index.html`

### Frontend (Angular)
```powershell
cd frontend-app
npm install  # if not already done
ng serve
```
- Frontend runs on: `http://localhost:4200`

## 🔐 Test Credentials

| Username | Password   | Role    | Access                    |
|----------|------------|---------|---------------------------|
| admin    | admin123   | ADMIN   | Admin Dashboard (full access) |
| neha     | 12345      | COMPANY | Company Dashboard         |
| kiran    | password   | COMPANY | Company Dashboard         |

## 📋 User Flow

1. Open `http://localhost:4200` → Redirects to `/login`
2. **Login as Admin** (admin/admin123):
   - Redirected to `/admin/dashboard`
   - Can view all companies and users
   - Can activate/deactivate/delete companies
   - Can delete users (except admin)

3. **Login as Company User** (neha/12345):
   - Redirected to `/company/dashboard` (company list page)
   - Can view and manage their company data

## 🗂️ Files Created/Modified

### Backend
- ✅ `demo/src/main/resources/application.yml` - Converted to H2
- ✅ `demo/src/main/resources/data.sql` - Fixed role names (ADMIN, COMPANY)
- ✅ `demo/src/main/java/com/example/demo/Controller/AdminController.java` - Added `/login` endpoint, CORS
- ✅ `demo/src/main/java/com/example/demo/Controller/LoginRequest.java` - New DTO
- ✅ `demo/src/main/java/com/example/demo/Controller/LoginResponse.java` - New DTO
- ✅ `demo/src/main/java/com/example/demo/Service/UserService.java` - Added `findByUsername`
- ✅ `demo/src/main/java/com/example/demo/Service/UserServiceImpl.java` - Implemented `findByUsername`

### Frontend
- ✅ `frontend-app/src/app/services/auth.service.ts` - New service
- ✅ `frontend-app/src/app/components/login/` - New component (TS, HTML, CSS)
- ✅ `frontend-app/src/app/components/admin-dashboard/` - New component (TS, HTML, CSS)
- ✅ `frontend-app/src/app/app.routes.ts` - Updated routes

## 🎯 Next Steps (Optional)

1. **Add Route Guards** - Protect admin routes (only allow ADMIN role)
2. **Company Dashboard** - Separate dashboard for company users to manage their own company
3. **Password Hashing** - Use BCrypt for secure password storage
4. **JWT Tokens** - Replace simple login with JWT-based authentication
5. **Form Validation** - Add more robust validation on frontend forms

## 🐛 Troubleshooting

**Backend won't start?**
- Check if port 8082 is available
- Run `mvn clean install` first

**Frontend shows CORS errors?**
- Backend has `@CrossOrigin(origins = "http://localhost:4200")` on AdminController
- Make sure backend is running on port 8082

**Login fails?**
- Check H2 console to see if users table has data
- Verify roles are uppercase: `ADMIN`, `COMPANY`

**H2 data disappears on restart?**
- That's normal for in-memory DB. Data reloads from `data.sql` on each startup.
