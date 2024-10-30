![](.github/banner.png)

JWizard is an open-source Discord music bot handling audio content from various multimedia sources with innovative web
player. This repository contains a library that shares code between the JWizard Core and JWizard API projects. It
includes additional helper classes for logging, database handling, and communication protocols based on AMQP and
WebSockets.

## Table of content

* [Clone and install](#clone-and-install)
* [Package on local environments](#package-on-local-environments)
* [Documentation](#documentation)
* [Contributing](#contributing)
* [License](#license)

## Clone and install

1. Make sure you have at least JVM 17 and Kotlin 2.0.
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

## Documentation

For detailed documentation, please visit [JWizard documentation](https://jwizard.pl/docs).
<br>
Documentation for latest version (with SHA) you will find [here](https://docs.jwizard.pl/jwl) - in KDoc format.

## Contributing

We welcome contributions from the community! Please read our [CONTRIBUTE](./CONTRIBUTE.md) file for guidelines on how
to get involved.

## License

This project is licensed under the AGPL-3.0 License - see the LICENSE file for details.
