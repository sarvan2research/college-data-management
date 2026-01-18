# Enterprise Run Guide

This project includes a production-ready script `run.sh` to simplify running the application with optimized configurations.

## Quick Start

Run the application with default settings:
```bash
./run.sh
```

## Usage Options

The script supports specific flags for common configurations:

| Flag | Long Flag | Description | Example |
|------|-----------|-------------|---------|
| `-j` | `--jar` | Path to the JAR file | `./run.sh -j /opt/app/my-app.jar` |
| `-p` | `--port` | Server HTTP Port | `./run.sh -p 9090` |
| `-c` | `--config` | External Config File | `./run.sh -c config/prod.yml` |

### Examples

**Run with a custom JAR path:**
```bash
./run.sh --jar /build/artifacts/college-data.jar
```

**Run on a specific port:**
```bash
./run.sh --port 8081
```

**Run with an external configuration file:**
```bash
./run.sh --config /etc/secrets/application.properties
```

**Combine options:**
```bash
./run.sh -j build/app.jar -p 9090 -c config/prod.yml
```

## Environment Configuration

You can tune the JVM memory settings using environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| `APP_HEAP_INIT` | Initial Heap Size (`-Xms`) | `512m` |
| `APP_HEAP_MAX` | Max Heap Size (`-Xmx`) | `1g` |
