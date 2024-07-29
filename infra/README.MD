# Course System Infrastructure

## Local Development Environment

To start it:

```bash
docker-compose -f local/docker-compose.yaml up -d
```

To stop it:

```bash
docker-compose -f local/docker-compose.yaml down
```

If you want to clean up all actual volumes use:

```bash
docker-compose -f local/docker-compose.yaml down -v
```

#### Postgres

For PostgreSQL you could use Intellij DB with `localhost:5432`, database `course_system_db` (privileged username `postgres` with a password `sombraPostgresql`).

