FROM postgres:12
ENV POSTGRES_USER inventory
ENV POSTGRES_PASSWORD inventory
ENV POSTGRES_DB postgres


FROM library/postgres
COPY init.sql /docker-entrypoint-initdb.d/
