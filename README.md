# Bank System

A full-stack bank management application with a Spring Boot backend and a React/Vite frontend.

## Project Structure

```text
Bank-System1/
  backend/        Spring Boot API and server-rendered pages
  my-react-app/   React frontend built with Vite
```

## Requirements

- Java 21 or newer
- Node.js and npm
- Git

## Run The Backend

Open a terminal in the backend folder:

```powershell
cd C:\Users\apaosha\Desktop\projects\Bank-System1\backend
.\mvnw.cmd spring-boot:run
```

The backend runs on:

```text
http://localhost:8081
```

Useful backend links:

```text
http://localhost:8081/swagger-ui/index.html
http://localhost:8081/h2-console
```

## Run The Frontend

Open a second terminal in the React app folder:

```powershell
cd C:\Users\apaosha\Desktop\projects\Bank-System1\my-react-app
npm install
npm run dev
```

The frontend usually runs on:

```text
http://localhost:5173
```

## Test And Build

Backend tests:

```powershell
cd C:\Users\apaosha\Desktop\projects\Bank-System1\backend
.\mvnw.cmd clean test
```

Frontend lint:

```powershell
cd C:\Users\apaosha\Desktop\projects\Bank-System1\my-react-app
npm run lint
```

Frontend production build:

```powershell
cd C:\Users\apaosha\Desktop\projects\Bank-System1\my-react-app
npm run build
```

Frontend dependency audit:

```powershell
cd C:\Users\apaosha\Desktop\projects\Bank-System1\my-react-app
npm audit --audit-level=moderate
```

## Common Issues

### npm cannot find package.json

Run npm commands inside `my-react-app`, not the repository root:

```powershell
cd C:\Users\apaosha\Desktop\projects\Bank-System1\my-react-app
```

### Port 8081 is already in use

Find the process using the backend port:

```powershell
Get-NetTCPConnection -LocalPort 8081 | Select-Object LocalAddress,LocalPort,State,OwningProcess
Get-Process -Id <PID>
```

Stop it:

```powershell
Stop-Process -Id <PID> -Force
```

Then start the backend again:

```powershell
.\mvnw.cmd spring-boot:run
```

### Frontend shows an old Vite error

Stop and restart the frontend dev server:

```powershell
Ctrl+C
npm run dev
```

## Notes

- The backend uses an in-memory H2 database, so data resets when the backend restarts.
- The frontend API client points to `http://localhost:8081`.
- Keep backend and frontend running in separate terminals during development.
