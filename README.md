# FableStats

Fable Stats - a simple plugin for Spigot 1.16-1.21.x, which allows to show in chat information about the character (default). The plugin also supports the Placeholders API, which gives you the ability to fully configure config.yml for your server.

<img width="337" height="132" alt="image" src="https://github.com/user-attachments/assets/d72fc5cf-7ae8-4e5f-b34d-bacaa32f63a9" />

## Features

- Displays player statistics via `/stats` command
- Fully customizable messages in `config.yml`
- PlaceholderAPI integration (optional)
- **Multi-language support (English / Russian / Ukrainian)**

## Available Placeholders

| Placeholder | Description |
|---|---|
| `%fablestats_player_name%` | Player name |
| `%fablestats_time_played_total%` | Total play time (e.g. "123 h 45 min" / "123 ч 45 мин") |
| `%fablestats_time_since_last_played%` | Current session duration |
| `%fablestats_first_join_date%` | First join date (dd.MM.yyyy) |
| `%fablestats_deaths%` | Total deaths |
| `%fablestats_kills%` | Player kills (PvP) |
| `%fablestats_mob_kills%` | Mob kills |
| `%fablestats_damage_dealt%` | Total damage dealt |
| `%fablestats_damage_taken%` | Total damage taken |
| `%fablestats_distance_walked%` | Distance walked (e.g. "1234 m" / "1234 м") |

## Multi-language Support

The plugin supports **English** (`en`), **Russian** (`ru`) and **Ukrainian** (`uk`) languages. The language setting affects:

- Command messages (no permission, reload confirmation, errors)
- Placeholder units (hours, minutes, distance, N/A)

### Configuration

Set the language in `config.yml`:

```yaml
# Language (en / ru / uk)
lang: en
```

Language files are stored in `plugins/FableStats/lang/` and are auto-generated on first startup. You can edit them to customize any message.

### Localized strings

| Key | English | Russian | Ukrainian |
|---|---|---|---|
| `no-permission` | You don't have permission to reload the config. | У вас нет прав для перезагрузки конфигурации. | У вас немає прав для перезавантаження конфігурації. |
| `config-reloaded` | FableStats config reloaded. | Конфигурация FableStats перезагружена. | Конфігурацію FableStats перезавантажено. |
| `only-player` | Only a player can use this command. | Только игрок может использовать эту команду. | Тільки гравець може використовувати цю команду. |
| `time-hours` | h | ч | год |
| `time-minutes` | min | мин | хв |
| `distance-suffix` | m | м | м |
| `not-available` | N/A | Н/Д | Н/Д |

To apply language changes, run `/stats reload` or restart the server.

## Commands

| Command | Permission | Description |
|---|---|---|
| `/stats` | `fablestats.use` | Show player statistics |
| `/stats reload` | `fablestats.reload` | Reload plugin configuration and language |

## Build

```bash
./mvnw clean package
```

Output JAR goes to `target/fablestats-1.1.jar`.
