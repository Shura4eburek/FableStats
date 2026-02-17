# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Minecraft Spigot 1.21 plugin (Java 21) that displays customizable player statistics in chat. Integrates with PlaceholderAPI as an optional dependency for template substitution.

## Build

```bash
mvn clean package
```

Output JAR (shaded/uber JAR with dependencies) goes to `target/`. No test suite exists.

## Architecture

Single-class monolith: `src/main/java/org/mamoru/customStats/CustomStats.java` (~200 lines).

Three inner components inside `CustomStats extends JavaPlugin`:
- **StatsCommandExecutor** — handles `/stats` command, processes message templates with PlaceholderAPI, sends formatted output to player
- **CustomPlaceholderExpansion** — registers custom placeholders (`%customstats_time_played_total%`, `%customstats_time_since_last_played%`, `%customstats_first_join_date%`, `%customstats_deaths%`) and listens for `PlayerJoinEvent` to track session start times
- **createConfig()** — generates default `config.yml` with command name and message templates

PlaceholderAPI is a soft dependency — plugin works without it but logs a warning.

## Key Files

- `pom.xml` — Maven config, repos: SpigotMC Nexus, Sonatype, ExtendedClip
- `src/main/resources/plugin.yml` — plugin metadata, command registration, API version