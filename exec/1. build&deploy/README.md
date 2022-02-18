# Gitlab 소스 클론 이후 빌드 및 배포할 수 있도록 정리한 문서

## 1) 버전

| 순번 | 구분    | 버전            |
| ---- | ------- | --------------- |
| 1    | JVM     | AWS Corretto 11 |
| 2    | node.js | 16.13.0         |
| 3    | mysql   | 8.0.28          |

## 2) 배포 명령어

| 순번 | 명령어                            | 비고                     |
| ---- | --------------------------------- | ------------------------ |
| 1    | git pull origin develop           | 소스코드 pull            |
| 2    | sh ~/S06P12A305/scripts/deploy.sh | 빌드, 배포 스크립트 실행 |

## 3) 배포 시 특이사항

- `node` 버전을 `16.13.0`을 사용해야 합니다.

## 4) DB 접속 정보

| 순번 | 계정 아이디 | 계정 패스워드 |
| ---- | ----------- | ------------- |
| 1    | root        | qnrwjrqnrwjr  |
| 2    | kskim       | qnrwjrqnrwjr  |
| 3    | eskim       | qnrwjrqnrwjr  |
| 4    | jklee       | qnrwjrqnrwjr  |
