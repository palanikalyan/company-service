# Run MySQL in Docker for this Spring Boot demo

This project uses a MySQL database. If your local MySQL is not working, run the official MySQL Docker image using the provided `docker-compose.yml` in the repository root.

From PowerShell (Windows):

1. Start the database:

```powershell
cd $PSScriptRoot\..\..\..\..\..\..\..  # if you open this file from its folder; otherwise cd to the repo root
docker-compose up -d
```

2. Confirm the container and health status:

```powershell
docker ps --filter "name=company_mysql"
docker logs company_mysql --tail 50
```

3. The Spring Boot service uses the database at `jdbc:mysql://localhost:3306/freelance` with username `root` and password `root`. The `application.yml` is already configured for this connection.

4. To stop and remove the container (data persists in a named volume):

```powershell
docker-compose down
```

Notes:
- The MySQL container maps port 3306 on the host. If you already have a local MySQL running, stop it or change the port mapping in `docker-compose.yml` and update `application.yml` accordingly.
- If you need to reset DB data, remove the named volume:

```powershell
docker-compose down -v
docker volume rm company_mysql_data
```
