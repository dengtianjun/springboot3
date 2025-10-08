# Spring Security Messaging 安全示例

这个示例演示了如何在Spring Boot WebFlux应用程序中使用spring-security-messaging来实现消息安全。

## 功能特性

1. 基于角色的消息访问控制
2. REST API端点安全控制
3. 方法级安全注解(@PreAuthorize)

## 用户凭证

系统中预定义了两个用户：

1. 普通用户：
   - 用户名：`user`
   - 密码：`password`
   - 角色：`USER`

2. 管理员用户：
   - 用户名：`admin`
   - 密码：`admin`
   - 角色：`ADMIN`, `USER`

## 消息安全API端点

### 公共端点（无需认证）
- `GET /api/messages/public` - 获取公共消息

### 用户端点（需要USER角色）
- `GET /api/messages/user` - 获取用户消息
- `POST /api/messages/send` - 发送普通消息

### 管理员端点（需要ADMIN角色）
- `GET /api/messages/admin` - 获取管理员消息
- `POST /api/messages/secure-send` - 发送安全消息

## 安全实现说明

### 1. 依赖引入

在pom.xml中添加了以下依赖：

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-messaging</artifactId>
</dependency>
```

### 2. 方法级安全控制

使用`@PreAuthorize`注解在控制器方法上实现细粒度的访问控制：

```java
@GetMapping("/user")
@PreAuthorize("hasRole('USER')")
public Mono<Map<String, Object>> getUserMessage(Principal principal) {
    // 只有具有USER角色的用户可以访问
}

@GetMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public Mono<Map<String, Object>> getAdminMessage(Principal principal) {
    // 只有具有ADMIN角色的用户可以访问
}
```

### 3. 消息安全原理

spring-security-messaging提供了以下安全功能：

1. **认证集成**：与Spring Security的认证机制无缝集成
2. **授权控制**：支持基于角色和权限的消息访问控制
3. **方法安全**：支持使用@PreAuthorize等注解进行方法级安全控制
4. **响应式支持**：与Spring WebFlux的响应式编程模型兼容

## 测试说明

### 1. 测试公共端点

```bash
curl -u user:password http://localhost:9099/api/messages/public
```

响应示例：
```json
{"message":"This is a public message","timestamp":[2025,10,8,22,33,8,638372000]}
```

### 2. 测试用户端点

```bash
# 使用user用户认证获取用户消息
curl -u user:password http://localhost:9099/api/messages/user

# 发送普通消息
curl -u user:password -H "Content-Type: application/json" -d '{"message":"Hello"}' http://localhost:9099/api/messages/send -X POST
```

响应示例：
```json
{"message":"This is a user-only message","user":"user","timestamp":[2025,10,8,22,33,15,557347000]}
```

```json
{"from":"user","message":"Hello","status":"Message sent successfully","timestamp":[2025,10,8,23,0,41,477811000]}
```

### 3. 测试管理员端点

```bash
# 使用admin用户认证获取管理员消息
curl -u admin:admin http://localhost:9099/api/messages/admin

# 发送安全消息
curl -u admin:admin -H "Content-Type: application/json" -d '{"message":"Secure Hello"}' http://localhost:9099/api/messages/secure-send -X POST
```

响应示例：
```json
{"admin":"admin","message":"This is an admin-only message","timestamp":[2025,10,8,22,33,26,65875000]}
```

```json
{"from":"admin","message":"[SECURE] Secure Hello","status":"Secure message sent successfully","timestamp":[2025,10,8,23,1,2,64231000]}
```

### 4. 权限测试

使用user用户尝试访问admin端点会返回Access Denied错误：

```bash
# 这会失败，因为user用户没有ADMIN角色
curl -u user:password http://localhost:9099/api/messages/admin
```

使用user用户尝试发送安全消息也会失败：

```bash
# 这会失败，因为user用户没有ADMIN角色
curl -u user:password -H "Content-Type: application/json" -d '{"message":"Try Secure"}' http://localhost:9099/api/messages/secure-send -X POST
```

## 核心类说明

### MessageController.java

实现了基于角色的消息安全控制：
- 使用`@PreAuthorize`注解控制方法访问
- 根据用户角色提供不同的消息访问权限
- 集成Principal对象获取当前认证用户信息

## 总结

通过引入spring-security-messaging依赖并结合@PreAuthorize注解，我们可以轻松地在WebFlux应用程序中实现消息安全控制。这种方法具有以下优势：

1. **简单易用**：通过注解方式实现安全控制，代码清晰易懂
2. **灵活配置**：可以针对不同方法设置不同的访问权限
3. **与现有安全机制集成**：复用已有的用户认证和角色管理机制
4. **支持响应式**：与Spring WebFlux完美集成