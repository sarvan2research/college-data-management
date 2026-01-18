# Profile-Based Logging Configuration Guide

## Overview
The application now supports **profile-based logging** to provide:
- **Human-readable logs** for local development (easy debugging)
- **JSON structured logs** for production (log aggregation tools)

## How to Use

### Local Development (Default)
By default, the application uses human-readable console logging:

```bash
# Run normally - uses human-readable logs
mvn spring-boot:run

# Or explicitly set profile
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

**Log Output Example:**
```
2026-01-15 06:40:15.123 [http-nio-8080-exec-1] [a1b2c3d4-e5f6-7890-abcd-ef1234567890] INFO  c.a.c.aspect.LoggingAspect - → Entering UserQueryService.getAll() with arguments: []
2026-01-15 06:40:15.145 [http-nio-8080-exec-1] [a1b2c3d4-e5f6-7890-abcd-ef1234567890] INFO  c.a.c.aspect.LoggingAspect - ← Exiting UserQueryService.getAll() | Execution time: 22ms | Return type: List
```

### Production/Staging (JSON Logs)
For production deployments, use the `prod` or `staging` profile:

```bash
# Production
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Staging
mvn spring-boot:run -Dspring-boot.run.profiles=staging
```

**Log Output Example:**
```json
{
  "@timestamp": "2026-01-15T01:10:15.123Z",
  "@version": "1",
  "message": "→ Entering UserQueryService.getAll() with arguments: []",
  "logger_name": "com.aahzi.collegedata.aspect.LoggingAspect",
  "thread_name": "http-nio-8080-exec-1",
  "level": "INFO",
  "level_value": 20000,
  "mdc": {
    "correlationId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
  }
}
```

## Profile Configuration

### Supported Profiles

| Profile | Log Format | Log Level | Use Case |
|---------|-----------|-----------|----------|
| `local` | Human-readable | DEBUG | Local development |
| `dev` | Human-readable | DEBUG | Development environment |
| `default` | Human-readable | DEBUG | No profile specified |
| `staging` | JSON | INFO | Staging environment |
| `prod` | JSON | INFO | Production environment |

### Setting Profiles

**Maven:**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

**Java JAR:**
```bash
java -jar -Dspring.profiles.active=prod target/college-data-management-1.0.0.jar
```

**Environment Variable:**
```bash
export SPRING_PROFILES_ACTIVE=prod
java -jar target/college-data-management-1.0.0.jar
```

**application.yml (if you create one):**
```yaml
spring:
  profiles:
    active: local  # default profile
```

## Log Pattern Details

### Human-Readable Pattern
```
%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%X{correlationId:-NO-CORRELATION-ID}] %-5level %logger{36} - %msg%n
```

**Components:**
- `%d{yyyy-MM-dd HH:mm:ss.SSS}` - Timestamp
- `[%thread]` - Thread name
- `[%X{correlationId:-NO-CORRELATION-ID}]` - Correlation ID from MDC
- `%-5level` - Log level (INFO, DEBUG, WARN, ERROR)
- `%logger{36}` - Logger name (abbreviated to 36 chars)
- `%msg` - Log message
- `%n` - New line

### JSON Pattern
Uses Logstash encoder with:
- All standard fields (`@timestamp`, `message`, `level`, etc.)
- MDC fields (including `correlationId`)
- UTC timezone

## Benefits

### Local Development
✅ **Easy to read** - No JSON parsing needed  
✅ **Quick debugging** - Scan logs visually  
✅ **Correlation ID visible** - Track requests easily  
✅ **DEBUG level** - See detailed application logs  

### Production
✅ **Structured data** - Easy to parse and query  
✅ **Log aggregation** - Works with ELK, Splunk, Datadog  
✅ **Machine-readable** - Automated analysis  
✅ **INFO level** - Reduced log volume  

## Examples

### Switching Between Modes

**Development:**
```bash
# Terminal 1 - Human-readable logs
mvn spring-boot:run

# Make a request
curl http://localhost:8080/api/user-queries

# See readable logs in console
```

**Production:**
```bash
# Terminal 1 - JSON logs
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Make a request
curl http://localhost:8080/api/user-queries

# See JSON logs (pipe to jq for pretty printing)
# Or send to log aggregation service
```

## Tips

1. **Always use `local` or `dev` profile during development** - Much easier to debug
2. **Use `prod` profile in production** - Better for log aggregation tools
3. **Correlation ID is included in both formats** - Track requests in any environment
4. **Change profile without code changes** - Just set environment variable

## Troubleshooting

**Q: Logs are in JSON but I want human-readable**  
A: Check your active profile. Set to `local`, `dev`, or `default`

**Q: Logs are human-readable but I want JSON**  
A: Set profile to `prod` or `staging`

**Q: How do I see which profile is active?**  
A: Look for this log line at startup:
```
The following profiles are active: local
```

**Q: Can I use both formats simultaneously?**  
A: No, but you can configure file appenders for different formats if needed
