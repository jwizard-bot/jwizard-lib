![](.github/banner.png)

[[About project](https://jwizard.pl/about)]

JWizard is an open-source Discord music bot handling audio content from various multimedia sources
with innovative web player. This repository contains a library that shares code between the JWizard
Core and JWizard API projects. It includes additional helper classes for logging, database handling,
and communication protocols based on AMQP and WebSockets.

## Table of content

* [Clone and install](#clone-and-install)
* [Package on local environments](#package-on-local-environments)
* [Docker containers](#docker-containers)
* [Contributing](#contributing)
* [License](#license)

## Clone and install

1. Make sure you have at least JDK 17 and Kotlin 2.0.
2. Clone this repository via:

```shell
$ git clone https://github.com/jwizard-bot/jwizard-lib
```

## Package on local environments

1. To package library to maven local, type:

- for UNIX based systems:

```bash
$ ./gradlew clean
$ ./gradlew publishToMavenLocal
```

- for Windows systems:

```bash
.\gradlew clean
.\gradlew publishToMavenLocal
```

## Docker containers

1. Before run containers, create `.env` file based `example.env` and fill with below schema:

```properties
# core instances
JWIZARD_JDA_SECRET_1_INSTANCE=<JDA application secret (first instance)>
JWIZARD_JDA_SECRET_2_INSTANCE=<JDA application secret (second instance)>
JWIZARD_REST_API_TOKEN=<Rest API token used in communication between JWizard API and Core instances>
# discord oidc (oauth2)
JWIZARD_OIDC_APP_ID=<OAuth2 application identifier>
JWIZARD_OIDC_SECRET=<OAuth2 application secret>
# server (api)
JWIZARD_GITHUB_TOKEN=<GitHub personal token (used for GitHub API calls)>
# vault
JWIZARD_VAULT_ROOT_TOKEN=<Vault root token (used in .env files in other JWizard projects>
# mysql
JWIZARD_MYSQL_USERNAME=<MySQL database username, by default root>
JWIZARD_MYSQL_PASSWORD=<MySQL database password>
JWIZARD_MYSQL_DB_NAME=<MySQL database name, by default jwizard-db>
# rabbitmq
JWIZARD_RABBITMQ_USERNAME=<RabbitMQ server username>
JWIZARD_RABBITMQ_PASSWORD=<RabbitMQ server password>
# management
JWIZARD_MANAGEMENT_DEFAULT_ADMIN_LOGIN=<Management panel default admin login>
# common
JWIZARD_GEOLOCATION_API_KEY=<geolocation api key, when is blank api is turned off>
JWIZARD_AES_SECRET_KEY=<16 bytes of AES-128 secret value>
# lavalink
JWIZARD_AUDIO_NODE_TOKEN=<Authentication token for development Lavalink nodes (all)>
JWIZARD_AUDIO_YT_SOURCE_VERSION=<yt source version>
JWIZARD_AUDIO_YT_PO_TOKEN=<proof of origin token>
JWIZARD_AUDIO_YT_PO_VISITOR_DATA=<proof of origin visitor data>
# ports (leave unchanged)
JWIZARD_VAULT_PORT=8761
JWIZARD_MYSQL_PORT=8762
JWIZARD_AUDIO_NODE_1_PORT=8766
JWIZARD_AUDIO_NODE_2_PORT=8767
JWIZARD_CORE_INSTANCE_1_PORT=8768
JWIZARD_CORE_INSTANCE_2_PORT=8769
```

2. To run all docker containers for this project, type:

```bash
$ docker compose up -D
```

This command will create and run following containers:

| Name                | Port(s)    | Description                           |
|---------------------|------------|---------------------------------------|
| jwizard-vault       | 8761       | Secret keys storage service.          |
| jwizard-mysql-db    | 8762       | MySQL database.                       |
| jwizard-lava-node-1 | 8767       | Lavalink #1 node.                     |
| jwizard-lava-node-2 | 8768       | Lavalink #2 node.                     |
| jwizard-rabbitmq    | 8771, 8772 | RabbitMQ server and management panel. |

> [!TIP]
> Alternatively, you can run single Lavalink node, but in `docker-compose.yml` file you must
> remove second Lavalink node declaration. Running 2 nodes are useful for checking load-balancer in
> performance tests.

AD: Alternatively you can run containers separately via:

```bash
$ docker compose up -D <container name>
```

where `<container name>` is the name of the container (available container you will find above in
the table).

3. Apply database migrations:

* Clone `jwizard-tools` repository via:

```bash
$ git clone https://github.com/jwizard-bot/jwizard-tools
```

* Set-up Python environment (see `README.md` file
  in [jwizard-tools](https://github.com/jwizard-bot/jwizard-tools) repository),
* Run migrations for `self` and `infra` via:

```bash
$ (venv) python src/db_migrator.py --pipeline infra
$ (venv) python src/db_migrator.py --pipeline self
```

> [!TIP]
> More information about JWizard Python migrator you could find in
> [jwizard-tools](https://github.com/jwizard-bot/jwizard-tools) repository.

## Contributing

We welcome contributions from the community! Please read our [CONTRIBUTING](./CONTRIBUTING.md) file
for guidelines on how to get involved.

## License

This project is licensed under the AGPL-3.0 License - see the LICENSE file for details.
