# sonas

### Test Database

```
   create database sonas;
   
   use sonas;
   
   CREATE USER 'ironhacker'@'localhost' IDENTIFIED BY '1r0nH@ck3r';
   
   GRANT ALL PRIVILEGES ON *.* TO 'ironhacker'@'localhost';
   
   FLUSH PRIVILEGES;
```

### Service ports
| Port | Service
| :--- | :--- 
| 8761 | discovery-service
| 8099 | auth-service
| 8000 | gateway-service
| 8100 | user-service
| 8200 | cv-service
| 8300 | portfolio-service
| 8400 | job-service

### user-service

| Endpoint | Method | Description | Path Params
| :--- | :--- | :--- | :--- 
| /api/users | `GET` | Get all users | None
| /api/users/{id} | `GET` | Get user by user id| `id=[long]`
| /api/users/new | `POST` | Add new user | None
| /api/users/update/{id} | `PUT` | Update user | `id=[long]`
| /api/users/delete/{id} | `DELETE` | Delete user | `id=[long]`

### cv-service

| Endpoint | Method | Description | Path Params
| :--- | :--- | :--- | :--- 
| /api/curriculums | `GET` | Get all cvs| None
| /api/curriculums/{id} | `GET` | Get cv by id | `id=[long]`
| /api/curriculums/{userId} | `GET` | Get cvs by user | `userId=[long]`
| /api/curriculums/new | `POST` | Add new cv | None
| /api/curriculums/update/{id} | `PUT` | Update cv | `id=[long]`
| /api/curriculums/delete/{id} | `DELETE` | Delete cv | `id=[long]`

### portfolio-service

| Endpoint | Method | Description | Path Params
| :--- | :--- | :--- | :--- 
| /api/portfolios | `GET` | Get all portfolios | None
| /api/portfolios/{id} | `GET` | Get portfolio by id| `id=[long]`
| /api/portfolios/{userId} | `GET` | Get portfolios by user id | `userId=[long]`
| /api/portfolios/new | `POST` | Add new portfolio | None
| /api/portfolios/update/{id} | `PUT` | Update portfolio | `id=[long]`
| /api/portfolios/delete/{id} | `DELETE` | Delete portfolio | `id=[long]`

### job-service

| Endpoint | Method | Description | Path Params
| :--- | :--- | :--- | :--- 
| /api/jobs | `GET` | Get all jobs | None
| /api/jobs/{id} | `GET` | Get job by id| `id=[long]`
| /api/jobs/{userId} | `GET` | Get jobs by user id | `userId=[long]`
| /api/jobs/new | `POST` | Add new job | None
| /api/jobs/update/{id} | `PUT` | Update job | `id=[long]`


