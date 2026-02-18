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

Main class: `src/main/java/org/mamoru/fableStats/FableStats.java`.

Components:
- **StatsCommandExecutor** — handles `/stats` command, processes message templates with PlaceholderAPI, sends formatted output to player
- **FablePlaceholderExpansion** — registers custom placeholders (`%fablestats_player_name%`, `%fablestats_time_played_total%`, `%fablestats_time_since_last_played%`, `%fablestats_first_join_date%`, `%fablestats_deaths%`) and listens for `PlayerJoinEvent` to track session start times
- **LangManager** — handles i18n (en/ru) with language files in `src/main/resources/lang/`

PlaceholderAPI is a soft dependency — plugin works without it but logs a warning.

## Key Files

- `pom.xml` — Maven config, repos: SpigotMC Nexus, Sonatype, ExtendedClip
- `src/main/resources/plugin.yml` — plugin metadata, command registration, API version
