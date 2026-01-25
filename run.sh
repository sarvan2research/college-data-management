#!/bin/bash

# ==============================================================================
# Enterprise Run Script for Spring Boot Application
# ==============================================================================
#
# Usage:
#   ./run.sh [options] [arguments...]
#
# Options:
#   -j, --jar <path>       Path to the application JAR file (default: target/college-data-management-1.0.0.jar)
#   -p, --port <number>    Port to run the server on (default: 8080)
#   -c, --config <path>    Path to external configuration file (properties or yaml)
#   -h, --help             Show this help message
#
# Examples:
#   ./run.sh --jar /app/app.jar --port 9090
#   ./run.sh --config /etc/app/application-prod.yml
#   ./run.sh --server.port=8081 --spring.profiles.active=dev (Standard Spring Boot args also work)
#
# ==============================================================================

# --- Configuration ---
APP_NAME="college-data-management"
VERSION="1.0.0"
DEFAULT_JAR_PATH="./${APP_NAME}-${VERSION}.jar"

# --- Defaults ---
JAR_PATH="$DEFAULT_JAR_PATH"
SERVER_PORT="8080"
CONFIG_FILE="./application.yml"
APP_ARGS=()

# --- Memory Defaults ---
HEAP_MAX="${APP_HEAP_MAX:-1g}"
HEAP_INIT="${APP_HEAP_INIT:-512m}"

# --- ANSI Colors ---
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

log_info() { echo -e "${GREEN}[INFO]${NC} $1"; }
log_err() { echo -e "${RED}[ERROR]${NC} $1"; }

# --- 1. Parse Arguments ---
while [[ $# -gt 0 ]]; do
    case $1 in
        -j|--jar)
            JAR_PATH="$2"
            shift 2
            ;;
        -p|--port)
            SERVER_PORT="$2"
            shift 2
            ;;
        -c|--config)
            CONFIG_FILE="$2"
            shift 2
            ;;
        -h|--help)
            sed -n '4,18p' "$0" # Print header comments
            exit 0
            ;;
        *)
            APP_ARGS+=("$1") # Collect other args
            shift
            ;;
    esac
done

# --- 2. Construct Spring Boot Arguments ---
if [ -n "$SERVER_PORT" ]; then
    APP_ARGS+=("--server.port=$SERVER_PORT")
fi

if [ -n "$CONFIG_FILE" ]; then
    # support file path or directory
    APP_ARGS+=("--spring.config.additional-location=file:$CONFIG_FILE")
fi

# --- 3. Check Java & JAR ---
if ! command -v java &> /dev/null; then
    log_err "Java is not installed or not in PATH."
    exit 1
fi

if [ ! -f "$JAR_PATH" ]; then
    log_err "JAR file not found at: $JAR_PATH"
    log_err "Please provide a valid path using --jar or ensure the default exists."
    exit 1
fi

JAVA_VER=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')

log_info "------------------------------------------------"
log_info "Starting $APP_NAME"
log_info "Java Version : $JAVA_VER"
log_info "JAR Path     : $JAR_PATH"
log_info "Heap Settings: Initial=$HEAP_INIT, Max=$HEAP_MAX"
if [ -n "$SERVER_PORT" ]; then log_info "Port         : $SERVER_PORT"; fi
if [ -n "$CONFIG_FILE" ]; then log_info "Config File  : $CONFIG_FILE"; fi
log_info "------------------------------------------------"

# --- 4. Run Application ---
exec java \
  -Xms"${HEAP_INIT}" \
  -Xmx"${HEAP_MAX}" \
  -XX:+UseG1GC \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=./dumps/java_pid%p.hprof \
  -jar "$JAR_PATH" \
  "${APP_ARGS[@]}"
