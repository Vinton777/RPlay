# RPlay

Configurable roleplay commands for Paper, Purpur, and Folia servers.

## Version 2.1.0

- Sends messages on each recipient's scheduler, including on Folia.
- Treats command arguments as text so MiniMessage tags entered by players cannot change formatting.
- Rejects invalid `/roll` limits, including zero and negative numbers.

The plugin is compiled for Java 17 and uses the Paper 1.20.1 API to retain support for older servers. The latest Paper 26.3 server itself requires Java 25. The `api-version` is kept at 1.19 so the same JAR can load on older compatible servers. Server implementations can differ; test an upgrade on a staging server before replacing a production plugin.

## Commands

| Command | Action |
| --- | --- |
| `/me <text>` | Send a roleplay action to players within the configured radius (200 blocks by default). |
| `/gme <text>` | Send a roleplay action to all players. |
| `/do <text>` | Send a description to all players. |
| `/roll [max]` | Roll a number from 1 through `max` (100 by default). |
| `/try <text>` | Send a random success or failure result to all players. |
| `/rplay reload` | Reload `config.yml`; requires `rplay.reload` (operators by default). |

## Configuration

The generated `plugins/RPlay/config.yml` contains the message formats, local radius, roll broadcast mode, and Russian and English messages. Set `language: ru` or `language: en`, then run `/rplay reload`. Formatting uses MiniMessage; placeholders such as `{player}` and `{message}` are inserted as plain text.

## Build

Run `mvn package` with JDK 17 or newer. The plugin JAR is written to `target/RPlay-2.1.0.jar`.
