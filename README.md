![](.github/banner.png)

JWizard is an open-source Discord music bot handling audio content from various multimedia sources with innovative web
player. This repository contains a library that shares code between the JWizard Core and JWizard API projects. It
includes additional helper classes for logging, database handling, and communication protocols based on AMQP and
WebSockets.

## Table of content

* [Clone and install](#clone-and-install)
* [Package on local environments](#package-on-local-environments)
* [Docker containers](#docker-containers)
* [Documentation](#documentation)
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
# jda api
JWIZARD_JDA_APP_ID=<JDA application identifier>
JWIZARD_JDA_SECRET=<JDA application secret>
# discord oidc (oauth2)
JWIZARD_OIDC_APP_ID=<OAuth2 application identifier>
JWIZARD_OIDC_SECRET=<OAuth2 application secret>
# vault
JWIZARD_VAULT_ROOT_TOKEN=<Vault root token (used in .env files in other JWizard projects>
# mysql
JWIZARD_MYSQL_USERNAME=<MySQL database username, by default root>
JWIZARD_MYSQL_PASSWORD=<MySQL database password>
JWIZARD_MYSQL_DB_NAME=<MySQL database name, by default jwizard-db>
# lavalink
JWIZARD_LAVA_NODE_TOKEN=<Authentication token for development Lavalink nodes (all)>
JWIZARD_LAVA_YT_SOURCE_VERSION=1.8.3
JWIZARD_LAVA_NODE_1_YT_REFRESH_TOKEN=<Refresh token for youtube source, used only if youtube source is active>
JWIZARD_LAVA_NODE_2_YT_REFRESH_TOKEN=<Refresh token for youtube source, used only if youtube source is active>
# ports (should not be changed)
JWIZARD_VAULT_PORT=8761
JWIZARD_MYSQL_PORT=8762
JWIZARD_LAVA_NODE_1_PORT=8766
JWIZARD_LAVA_NODE_2_PORT=8767
```

2. To run all docker containers for this project, type:

```bash
$ docker compose up -D
```

This command will create and run following containers:

| Name                | Port(s) | Description                  |
|---------------------|---------|------------------------------|
| jwizard-vault       | 8761    | Secret keys storage service. |
| jwizard-mysql-db    | 8762    | MySQL database.              |
| jwizard-lava-node-1 | 8766    | Lavalink #1 node.            |
| jwizard-lava-node-2 | 8767    | Lavalink #2 node.            |

> NOTE: Alternatively, you can run single Lavalink node, but in `application.dev.yml` you must remove second Lavalink~~
> node declaration. Running 2 nodes are useful for checking load-balancer in performance tests.

AD: Alternatively you can run containers separately via:

```bash
$ docker compose up -D <container name>
```

where `<container name>` is the name of the container (available container you will find above in the table).

## Documentation

For detailed documentation, please visit [JWizard documentation](https://jwizard.pl/docs).
<br>
Documentation for latest version (with SHA) you will find [here](https://docs.jwizard.pl/jwl) - in KDoc format.

## Contributing

We welcome contributions from the community! Please read our [CONTRIBUTE](./CONTRIBUTE.md) file for guidelines on how
to get involved.

## License

This project is licensed under the AGPL-3.0 License - see the LICENSE file for details.
