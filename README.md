## API

### Swagger

http://localhost:8080/swagger-ui/index.html

## 권한별 API 구분

### kiosk
- 키오스크 API
- API 호출시 키오스크 관리 코드와 함께 전달 필요.

### mngm
- 학교 방문자 관리 API
- http 세션 사용.

### optr
- 시스템 관리자/운영자 API

### vstrs
- 방문자 방문 신청 API

### sign
- 로그인/로그아웃

## Spring Boot

### 기본 설정 프로파일(로컬 개발 환경 기준으로 설정됨)
[application.yml](src%2Fmain%2Fresources%2Fapplication.yml)

### 개발환경 및 기타 환경 초기화시 프로파일
[application-init.yml](src%2Fmain%2Fresources%2Fapplication-init.yml)

### 기본 설정 프로파일(로컬 서버 기준으로 설정됨)
[application-local.yml](src%2Fmain%2Fresources%2Fapplication-local.yml)