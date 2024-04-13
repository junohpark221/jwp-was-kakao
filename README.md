# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 기능 구현
- [x] GET /index.html 응답하기
  - Request Header에서 path 분리하기
  - path에 해당하는 파일을 읽어 응답하기
- [x] CSS 지원하기
  - index.html에서 css 파일 불러오기
- [x] Query String 파싱
  - Query String에서 회원가입 정보 파싱하기
  - User객체를 RAM 메모리에 저장
- [x] Post 방식으로 회원가입
  - GET 방식을 POST 방식으로 전환
- [x] Redirect
  - 회원가입 이후 redirect 방식으로 index.html으로 이동하기