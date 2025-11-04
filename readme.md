# redis 스터디

이 프로젝트는 Redis를 공부하기 위한 프로젝트입니다.

## 사전 준비
- `Docker`와 `Docker Desktop`이 설치되어 있어야 합니다.
- 아래 명령어로 로컬 개발용 Redis 컨테이너를 구성합니다.

```bash
docker run -d --name dev-redis -p 6379:6379 redis
```

위 명령은 로컬 포트 `6379`를 컨테이너의 `6379` 포트에 바인딩하고, 컨테이너 이름을 `dev-redis`로 설정합니다.

---

## 기본 스택
- Java 17
- H2 Database
- JPA (Spring Data JPA)
- Gradle

## 실행
- 상단의 Docker 명령으로 Redis 컨테이너가 실행 중이어야 합니다.
- 첫 프로젝트 실행 명령어:

```bash
# macOS / Linux
./gradlew bootRun

# Windows
gradlew.bat bootRun
```
