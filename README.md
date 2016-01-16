# hipstr

https://www.packtpub.com/application-development/clojure-web-development-essentials

based on Luminus template version: 1.16.7
               
``` sh
$ lein new luminus myapp --template-version 1.16.7
```

## Prerequisites

### Leiningen

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

### PostgreSQL

#### Run Container

``` sh
docker run -it -d \
  --name hipstr-db \
  -e POSTGRES_USER=hipstr -e POSTGRES_PASSWORD=p455w0rd \
  -p 5432:5432 \
  postgres:9.3.5
```

### Connect to the database with psql

``` sh
docker run -it --rm \
  --link hipstr-db:db \
  postgres:9.3.5 \
  sh -c 'exec psql -h "$DB_PORT_5432_TCP_ADDR" -p "$DB_PORT_5432_TCP_PORT" -U hipstr -d postgres'
```

## Configuration
 
Add `profiles.clj`.

``` clj
{:dev {:env {:dev? true
             :db-classname "org.postgresql.Driver"
             :db-subprotocol "postgresql"
             :db-subname "//localhost/postgres"
             :db-user "hipstr"
             :db-password "p455w0rd"}}}
```

## Running

To start a web server for the application, run:

``` sh
$ lein ring server
```

or

``` sh
$ lein ring uberjar
$ java -jar \
  -Ddb.classname=org.postgresql.Driver \
  -Ddb.subprotocol=postgresql \
  -Ddb.subname=//localhost/postgres \
  -Ddb.user=hipstr \
  -Ddb.password=p455w0rd \
  ./target/hipstr-0.1.0-SNAPSHOT-standalone.jar
```

## License

Copyright © 2016 FIXME
