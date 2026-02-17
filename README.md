# Custom Stats

Custom Stats - a simple plugin for Spigot 1.16-1.21.x, which allows to show in chat information about the character (default). The plugin also supports the Placeholders API, which gives you the ability to fully configure config.yml for your server.

<img width="337" height="132" alt="image" src="https://github.com/user-attachments/assets/d72fc5cf-7ae8-4e5f-b34d-bacaa32f63a9" />

## Features

- Displays player statistics via `/stats` command
- Fully customizable messages in `config.yml`
- PlaceholderAPI integration (optional)
- **Multi-language support (English / Russian)**

## Available Placeholders

| Placeholder | Description |
|---|---|
| `%customstats_time_played_total%` | Total play time (e.g. "123 h 45 min" / "123 ч 45 мин") |
| `%customstats_time_since_last_played%` | Current session duration |
| `%customstats_first_join_date%` | First join date (dd.MM.yyyy) |
| `%customstats_deaths%` | Total deaths |
| `%customstats_kills%` | Player kills (PvP) |
| `%customstats_mob_kills%` | Mob kills |
| `%customstats_damage_dealt%` | Total damage dealt |
| `%customstats_damage_taken%` | Total damage taken |
| `%customstats_distance_walked%` | Distance walked (e.g. "1234 m" / "1234 м") |

## Multi-language Support

The plugin supports **English** (`en`) and **Russian** (`ru`) languages. The language setting affects:

- Command messages (no permission, reload confirmation, errors)
- Placeholder units (hours, minutes, distance, N/A)

### Configuration

Set the language in `config.yml`:

```yaml
# Language (en / ru)
lang: en
```

Language files are stored in `plugins/CustomStats/lang/` and are auto-generated on first startup. You can edit them to customize any message.

### Localized strings

| Key | English | Russian |
|---|---|---|
| `no-permission` | You don't have permission to reload the config. | У вас нет прав для перезагрузки конфигурации. |
| `config-reloaded` | CustomStats config reloaded. | Конфигурация CustomStats перезагружена. |
| `only-player` | Only a player can use this command. | Только игрок может использовать эту команду. |
| `time-hours` | h | ч |
| `time-minutes` | min | мин |
| `distance-suffix` | m | м |
| `not-available` | N/A | Н/Д |

To apply language changes, run `/stats reload` or restart the server.

## Commands

| Command | Permission | Description |
|---|---|---|
| `/stats` | `customstats.use` | Show player statistics |
| `/stats reload` | `customstats.reload` | Reload plugin configuration and language |

## Build

```bash
./mvnw clean package
```

Output JAR goes to `target/customstats-1.0.jar`.
