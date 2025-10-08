# Spring Boot Security 示例

这是一个在Spring Boot WebFlux应用程序中集成Spring Security的示例项目。

## 功能特性

1. 基于角色的访问控制（RBAC）
2. 用户认证和授权
3. 公共页面和受保护页面的区分
4. 管理员专用区域

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

## 可访问的端点

### 公共端点（无需认证）
- `GET /home` - 主页
- `GET /contact` - 联系页面
- `GET /` - 默认首页

### 用户端点（需要认证）
- `GET /user/profile` - 用户个人资料页面
- `GET /flux` - WebFlux示例
- `GET /mono` - WebFlux示例

### 管理员端点（需要ADMIN角色）
- `GET /admin/` - 管理员主页
- `GET /admin/users` - 用户列表
- `GET /admin/settings` - 设置页面

> 注意：管理员端点的访问控制是通过在 [AdminController.java](src/main/java/com/example/demo/controller/AdminController.java) 上使用 `@PreAuthorize("hasRole('ADMIN')")` 注解实现的，而不是在SecurityConfig中集中配置。

## 安全配置说明

安全配置类位于 [SecurityConfig.java](src/main/java/com/example/demo/config/SecurityConfig.java) 中，实现了以下功能：

1. 使用基于角色的访问控制
2. 配置了表单登录和HTTP基本认证
3. 使用了现代化的密码编码器
4. 为WebFlux应用程序定制的安全配置
5. 启用方法级安全控制(@PreAuthorize注解)

## 测试说明

1. 访问公共页面如 http://localhost:9099/home 不需要登录
2. 访问受保护页面如 http://localhost:9099/user/profile 会重定向到登录页面
3. 使用用户凭证登录后可以访问受保护的页面
4. 使用管理员凭证登录后可以访问管理员专用页面

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── config/           # 安全配置
│   │       ├── controller/       # 控制器
│   │       ├── model/            # 数据模型
│   │       └── service/          # 业务逻辑
│   └── resources/
│       └── application.yml       # 配置文件
```