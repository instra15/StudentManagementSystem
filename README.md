# StudentManagementSystem

基于 **Spring Boot 4.1.0 + JPA + MySQL** 的学生信息管理系统，提供学生数据的增删查功能，采用 DTO 分层设计与统一响应封装。

## 技术栈

| 技术 | 版本 / 说明 |
|------|------------|
| Java | 17 |
| Spring Boot | 4.1.0 |
| Spring Data JPA | 随 Boot 父依赖 |
| MySQL Connector/J | 运行时依赖 |
| Lombok | 编译期注解（@Data / @NoArgsConstructor 等） |
| Maven | 构建工具（自带 Wrapper） |

## 项目结构

```
src/main/java/com/example/studentmanagementsystem/
├── StudentManagementSystemApplication.java   # 启动类
├── Response.java                            # 统一响应封装
├── DAO/
│   ├── Student.java                         # 学生实体（JPA Entity）
│   └── StudentRepository.java               # 数据访问层（JpaRepository）
├── DTO/
│   └── StudentDTO.java                      # 数据传输对象
├── Converter/
│   └── StudentConverter.java                # Entity ↔ DTO 互转
├── service/
│   ├── StudentService.java                  # 业务接口
│   └── StudentServiceImpl.java              # 业务实现
├── controller/
│   └── StudentController.java               # REST 控制器
└── exception/
    ├── StudentAlreadyExist.java             # 学号重复异常
    └── StudentNotFoundException.java       # 学生不存在异常
```

## 环境要求

- JDK 17+
- Maven 3.8+（或用项目自带的 `mvnw`）
- MySQL 8.0+，数据库名 `student_management`

## 快速开始

### 1. 克隆仓库

```bash
git clone https://github.com/instra15/StudentManagementSystem.git
cd StudentManagementSystem
```

### 2. 准备数据库

```sql
CREATE DATABASE student_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

`application.properties` 中已配置数据源，默认连接 `localhost:3306/student_management`，账号 `root`。

> ⚠️ 当前配置文件中密码为明文，建议改为环境变量或配置中心管理，避免提交敏感信息。

### 3. 编译运行

```bash
# Linux / macOS
./mvnw clean spring-boot:run

# Windows
mvnw.cmd clean spring-boot:run
```

启动成功后默认端口 `8080`。

## API 接口

所有接口返回统一格式 `Response<T>`：

```json
{
  "data": { ... },
  "success": true,
  "errorMsg": null
}
```

### 查询学生

| 方法 | URL | 说明 |
|------|-----|------|
| GET | `/student/find/id/{id}` | 按主键 ID 查询 |
| GET | `/student/find/name/{name}` | 按姓名查询 |
| GET | `/student/find/no/{no}` | 按学号查询 |

**示例：**

```bash
curl http://localhost:8080/student/find/id/1
```

```json
{
  "data": {
    "studentNo": "2024001",
    "name": "张三",
    "age": 20,
    "className": 3
  },
  "success": true,
  "errorMsg": null
}
```

### 新增学生

| 方法 | URL | 说明 |
|------|-----|------|
| POST | `/student/add` | 新增学生（学号不可重复） |

**请求体：**

```json
{
  "studentNo": "2024002",
  "name": "李四",
  "age": 21,
  "className": 2
}
```

```bash
curl -X POST http://localhost:8080/student/add \
  -H "Content-Type: application/json" \
  -d '{"studentNo":"2024002","name":"李四","age":21,"className":2}'
```

> 若学号已存在，返回 `success: false` 并提示 `StudentAlreadyExist`。

### 删除学生

| 方法 | URL | 说明 |
|------|-----|------|
| DELETE | `/student/delete/id/{id}` | 按主键 ID 删除 |

```bash
curl -X DELETE http://localhost:8080/student/delete/id/1
```

## 数据模型

### Student 表（`student`）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT (PK, AUTO_INCREMENT) | 主键 |
| studentNO | VARCHAR | 学号（唯一） |
| name | VARCHAR | 姓名 |
| age | INT | 年龄 |
| className | INT | 班级编号 |

### StudentDTO

| 字段 | 类型 | 说明 |
|------|------|------|
| studentNo | String | 学号 |
| name | String | 姓名 |
| age | int | 年龄 |
| className | int | 班级编号 |

## 设计说明

- **分层架构**：Controller → Service → Repository，职责清晰
- **DTO 隔离**：对外暴露 `StudentDTO`，内部使用 `Student` 实体，通过 `StudentConverter` 互转，避免实体直接暴露
- **统一响应**：`Response<T>` 封装 `data` + `success` + `errorMsg`，前端可统一处理成功/失败
- **异常处理**：自定义 `StudentNotFoundException` 和 `StudentAlreadyExist`，在 Service 层捕获并转为 Response，不抛出到 Controller

## 已知问题与改进建议

| 问题 | 建议 |
|------|------|
| `application.properties` 明文密码 | 改用环境变量 `${DB_PASSWORD}` 或 Spring Cloud Config |
| `className` 为 `int` 类型 | 若班级为字符串（如"三年二班"），应改为 `String`；若用编号可保留但建议加注释说明映射关系 |
| Controller 接收 `Student` 而非 `StudentDTO` | `addNewStudent` 建议改为接收 `StudentDTO`，在 Service 内转换，保持 DTO 边界一致性 |
| 缺少全局异常处理器 | 添加 `@ControllerAdvice` + `@ExceptionHandler`，统一处理未捕获异常 |
| 缺少参数校验 | 在 DTO 上加 `@NotBlank`、`@Min` 等注解，Controller 用 `@Valid` 触发 |
| 无分页查询 | 列表查询建议增加 `Pageable` 分页支持 |
| 无单元测试覆盖业务层 | 当前仅有启动测试，建议为 `StudentServiceImpl` 补充 Mockito 单元测试 |
| 数据库表无唯一索引 DDL | 建议在 `studentNO` 上加 `UNIQUE KEY`，防止并发插入重复数据 |

## 提交记录

| Commit | 说明 |
|--------|------|
| Initial commit | 项目初始化 |
| Fix controller routes, add create/delete, fix save | 修正路由、补全增删功能 |
| className -> String; addNewStudent uses DTO | 班级字段调整、新增接口改用 DTO |

## License

未指定，默认保留所有权利。如需开源，建议添加 LICENSE 文件（如 MIT）。
