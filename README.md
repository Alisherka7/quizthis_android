<div align="center">

  <h1>QuizThis - Android App</h1>

<h4>
    <a href="https://www.youtube.com/watch?v=bFql1jxSedA">Presentation Video</a>
  <span> · </span>
    <a href="https://github.com/Alisherka7/quizthis_android/issues">Request Feature</a>
  </h4>

  <img src="https://github.com/Alisherka7/quizthis_android/assets/38793933/9d83463d-7d56-4b84-a800-60b07c340293" width="500px"/>
</div>

<!-- TechStack -->
### :space_invader: Tech Stack

  <summary>Front End</summary>
  <ul>
    <li><a href="https://www.java.com/en/">Java</a></li>
    <li><a href="https://developer.android.com/studio">Android studio</a></li>
    <li><a href="https://figma.com/">Figma</a></li>
  </ul>


  <summary>Server</summary>
  <ul>
    <li><a href="https://www.java.com/en/">Java</a></li>
    <li><a href="https://spring.io/projects/spring-boot/">Spring Boot</a></li>
    <li><a href="https://spring.io/projects/spring-security/">Spring Security</a></li>
    <li><a href="https://aws.amazon.com/">Amazon Web Servicce</a></li>
    <li><a href="https://www.nginx.com/">Nginx</a></li>
  </ul>

<summary>DevOps</summary>
  <ul>
    <li><a href="https://github.com/">GitHub</a></li>
  </ul>

<!-- About the Project -->
## :star2: About the Project
**QuizThis은** 사용자들이 자신만의 원하는 학습 세트를 생성하고 학습할 수 있는 플래시 카드나 퀴즈 모드를 제공하는 플랫폼입니다.


### 어플리케이션 디자인 - <a href="https://www.figma.com/community/file/1267863084414820155/QuizThis---Android-App-with-source-code">소스 파일</a>


<img src="https://github.com/Alisherka7/quizthis_android/assets/38793933/83b41e9c-9301-4280-aee1-298feeb95087" width="800px"/>

### 프로젝트 설계:

<img src="https://github.com/Alisherka7/quizthis_android/assets/38793933/0cad9645-3d46-47aa-b81f-039689d08408" width="480px"/>


<!-- Getting started -->
## :toolbox: Getting started
프로젝트는 FrontEnd - ```Android Studio``` 와 BackEnd - ```Spring Boot```기반으로 개발되었습니다.
프로젝트 설치 방법은 다음과 같습니다.
## :book: Documentation

### :gear: Installation
* Java JDK -> 18.0.1
* Spring boot 3.1.0
* <a href="https://www.jetbrains.com/ko-kr/community/education/#students">Intellij Idea Ultimate Version!</a> -> <a href="https://goddaehee.tistory.com/215"> 무료 설치 방법!!!! </a>
* <a href="https://www.apachefriends.org/">XAMPP Local Web Server</a>
* <a href="https://git-scm.com/">GIT</a>

### 데이터베이스 설치 
1. 해당 리포지토리 프로젝트를 자신의 컴퓨터에서 다운받으세요. <br>
    1.1 <a href="https://github.com/StyleRent/BackEnd-SpringBoot/archive/refs/heads/main.zip">**압축 버전을 다운 받는 방법**</a> <br>
    1.2 CMD 또는 터미널을 열고 원하는 폴도에서 ```git clone https://github.com/StyleRent/BackEnd-SpringBoot.git``` 명령어로 해당 프로젝트를 다운 받으세요 <br>

2. XAMPP 웹 서버와 MySQL을 실행하세요.<br>
<img width="332" alt="Screen Shot 2023-03-31 at 9 12 44" src="https://user-images.githubusercontent.com/38793933/228991598-14a5e427-1a97-430e-ad14-33ba6d98a026.png">

  2.1 기준으로 Mysql은  ``` ttp://localhost:3306/ ``` 주소로 실행시킵니다. **Admin** 보튼 클릭으로 **PhpMyAdmin** 데이터베이스 관리 페이지로 넘어갑니다.
  
  <img width="445" alt="Screen Shot 2023-03-31 at 9 15 06" src="https://user-images.githubusercontent.com/38793933/228991820-89133485-3a40-40ee-b697-221159f12ce0.png">

   2.2 New 버튼으로 새로운 테이블 생성하세요. 테이블명 - "**stylerent**", user - **root**, 비밀번호 없습니다.!!


### 프로젝트를 설치

3.0 프로젝트를 IntellijIdea로 열리세요.<br>
<img width="457" alt="Screen Shot 2023-03-31 at 9 07 53" src="https://github.com/Alisherka7/quizthis_android/assets/38793933/c62274aa-2abd-4d62-9484-c943d7e0e7c3">

3.1 열리신 프로젝트 Build가 끝나고 나서 스프링부트와 데이터베이스 연동을 하세요.!

<img width="241" alt="Screen Shot 2023-03-31 at 9 23 26" src="https://user-images.githubusercontent.com/38793933/228992738-0a2bfb44-07c0-4ac1-a33e-107718dc5d65.png">

3.2 스프링 부트 프로젝트의 설정 파일은 ``` Backend - SrpingBoot/src/resources/application.properties ```에 있습니다.

3.3 데이터베이스 연동 설정은 다음과 같습니다. <br>

<img width="560" alt="Screen Shot 2023-03-31 at 9 26 24" src="https://github.com/Alisherka7/quizthis_android/assets/38793933/af2837c9-fb86-428c-8f13-ae7133361af6">

4. 프로젝트 설정 과정은 끝났습니다. 


## Anrdoid Studio 프로젝트 설치
백엔드와 서버 설치하고 나서 이제 안드로이드 앱 프로젝트를 설치합니다.

1. 해당 리포지토리를 다운을 받아 안드로이드로 열리세요:
2. 안드로이드 앱과 백엔드 스프링부트는 ```Retrofit2```라이브로리를 활용하여 RestAPI를 통해 통신합니다.
  <img width="445" alt="Screen Shot 2023-03-31 at 9 15 06" src="https://github.com/Alisherka7/quizthis_android/assets/38793933/b657546f-127c-4da4-ba22-9230d4e99e47">
