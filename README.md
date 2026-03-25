

  
# ☕ Grids & Circle : Coffee Order 
  
> 누워서 간편하게 커피를 주문하고 빠르게 커피를 즐겨 보세요!
<br>  
  

  
## 📌 목차 (Table of Contents)  
  
1. [팀원 소개](#technologist-팀원-소개-team-members)
2. [기술 스택](#gear-기술-스택-tech-stack)
3. [사용자 인터페이스](#computer-사용자-인터페이스-user-interface)
4. [주요 기능](#hammer_and_wrench-주요-기능-features) 
5. [프로젝트 구조도](#open_file_folder-프로젝트-구조도)
6. [깃 컨벤션](#pushpin-깃-컨벤션-git-convention)
<br>

## :technologist: 팀원 소개 (Team Members)
> **프로그래머스 백엔드 9기 11회차 1차 프로젝트 4팀 (라떼4조)**

| 남진우<br/> (Jinwoo Nam) | 강승규<br/> (Seunggyu Kang) | 김동혁<br/> (DongHyeok Kim) | 김한솔<br/> (Hansol Kim) | 이유진 <br/>(Yujin Lee) |
| :---: | :---: | :---: | :---: | :---: |
| <img src="https://avatars.githubusercontent.com/u/123057930?s=64&v=4" alt="image" width="120" height="120" style="border-radius: 50%;" /> | <img src="https://avatars.githubusercontent.com/u/198696583?s=64&v=4&size=64" alt="image" width="120" height="120" style="border-radius: 50%;" /> |<img src="https://avatars.githubusercontent.com/u/258700527?s=64&v=4&size=64" alt="image" width="120" height="120" style="border-radius: 50%;" /> |<img src="https://avatars.githubusercontent.com/u/61941902?s=64&v=44&size=64" alt="image" width="120" height="120" style="border-radius: 50%;" /> |<img src="https://avatars.githubusercontent.com/u/89703649?s=64&v=4&size=64" alt="image" width="120" height="120" style="border-radius: 50%;" /> |
| BE/FE | BE/FE | BE/FE | BE/FE | BE/FE |
| 주문 목록 조회 <br/> 주문 목록 필터링 <br/>| 상품 추가<br/>상품 정보 수정<br/>상품 삭제 |주문 취소<br/>주문 상태 변경<br/>주문상세모달| 상품 목록 조회<br/>유저 정보 관리 | 신규 주문 생성<br/>주문 추가<br/>내 주문 조회 |
<br> 

  
## :gear: 기술 스택 (Tech Stack)  
  
### Backend  
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![JPA(Hibernate)](https://img.shields.io/badge/JPA(Hibernate)-59666C?style=for-the-badge)
  
### Frontend  
![Next.js](https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)  
  
### Deployment  
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
  
<br>  
  
  
<br>  



## :computer: 사용자 인터페이스 (User Interface) 

<details>
<summary> 🛒 상품 구매</summary>

 **상품 구매** 
 
 <img width="1361" height="920" alt="image" src="https://github.com/user-attachments/assets/6dc6540e-bdc5-430d-b22d-99a07ce3bd23" />
 <img width="346" height="520" alt="image" src="https://github.com/user-attachments/assets/c6fc1620-8e6d-41cc-81db-96e3a6f32541" /> <img width="360" height="520" alt="image" src="https://github.com/user-attachments/assets/a9b43c59-286b-45e3-b7e8-4328689e61a0" />

 
</details>


<details>
<summary>🍰 [관리자] 상품 관리</summary>

**상품 목록** <img width="1919" height="819" alt="image (3)" src="https://github.com/user-attachments/assets/1cefd157-d1c2-4514-a6fb-3dc32fe7a51b" />

**상품 작성** <img width="1575" height="805" alt="image" src="https://github.com/user-attachments/assets/a7d7d53e-9046-4f02-92a0-3e024b177185" />


</details>

<details>
<summary>📮 [관리자]주문 관리</summary>
	
| 기능 | 스크린샷 |
| :---: | :---:  |
| **주문 목록** |<img width="1919" height="818" alt="image (2)" src="https://github.com/user-attachments/assets/1c57c13a-1a1e-49f2-aa2c-5d1d2dc464bd" />|
| **주문 상세** |<img width="1916" height="819" alt="주문 상세 모달 화면" src="https://github.com/user-attachments/assets/79f8be21-2040-4a7b-b523-c5777f5d4d1c" />|


</details>

<br>
  
## :hammer_and_wrench: 주요 기능 (Features)  

-   ☕ **상품 관리 (Product)**
	- 관리자는 상품(이름, 카테고리, 가격, 재고, 설명, 이미지)을 새롭게 등록, 수정, 삭제할 수 있습니다.
	- 사용자는 등록된 전체 상품 목록 및 상세 단건을 조회할 수 있습니다.
	- **비관적 락(Pessimistic Lock) 적용**: 다중 스레드 환경에서 재고 감소 시 정합성을 보장합니다.
-   🛒 **주문 관리 (Order)**
	- 사용자는 원하는 상품을 장바구니에 담아 주문을 생성할 수 있습니다.
	- **시간 기반 주문 병합 로직**: 당일 오후 2시 이전에 추가 주문을 할 경우, 새로운 주문 번호를 발급하지 않고 기존 주문 내역에 상품 수량이 병합됩니다.
	- 재고가 부족할 경우 즉각적으로 400 예외(재고 부족) 처리를 진행합니다.
-   👤 **사용자 관리 (User)**
	- 이메일을 기준으로 신규 회원가입(Create) 또는 기존 회원 정보 수정(Update)을 한 번에 처리(Upsert)합니다.
	- 개인 주소지 및 우편번호를 관리합니다.
-   👷‍♂️ **관리자 대시보드 기능 (Admin Order)**
  	-  전체 주문 목록 조회 및 특정 기간별(Period), 특정 사용자별(User) 주문 내역 필터링 조회가 가능합니다.
  	-  주문 상태(`PENDING`, `SHIPPED`, `DELIVERED`, `CANCELLED`)를 변경할 수 있습니다.
  	-  주문 취소 시, 차감되었던 상품의 재고가 원상태로 복구됩니다.
-   🔒 **보안 및 공통 처리**
    -  `GlobalExceptionHandler`를 통한 일관된 예외 응답 처리.
    -  `RsData` 객체를 규격화하여 통일된 응답 폼(ResultCode, Message, Data)을 제공.
    -  프론트엔드 연동을 위한 전역 CORS 설정(`http://localhost:3000` 허용).
  <br>  


  
  ## :open_file_folder: 프로젝트 구조도

### :floppy_disk: Backend (src)


<summary>백엔드 구조도</summary>

```
📦src  
 ┣ 📂main  
 ┃ ┣ 📂java  
 ┃ ┃ ┗ 📂com.back.cafe  
 ┃ ┃    ┣ 📂domain  
 ┃ ┃    ┃ ┣ 📂order (주문 및 관리자 주문 도메인)
 ┃ ┃    ┃ ┣ 📂product (상품 도메인, 동시성 락 처리)
 ┃ ┃    ┃ ┗ 📂siteUser (사용자 도메인)
 ┃ ┃    ┣ 📂global  
 ┃ ┃    ┃ ┣ 📂ExceptionHandler (전역 예외 처리)  
 ┃ ┃    ┃ ┣ 📂entity (Auditing 기본 엔티티)  
 ┃ ┃    ┃ ┣ 📂initData (테스트/개발용 초기 데이터)  
 ┃ ┃    ┃ ┣ 📂rsData (공통 응답 객체)  
 ┃ ┃    ┃ ┣ 📂security (Spring Security 기반 설정)
 ┃ ┃    ┃ ┣ 📂springDoc (Swagger API 문서 설정)
 ┃ ┃    ┃ ┗ 📂webMvc (CORS 등 웹 설정)
 ┃ ┃    ┗ 📜CafeApplication.java  
 ┃ ┗ 📂resources  
 ┃    ┣ 📜application.yaml  
 ┃    ┣ 📜application-dev.yml  
 ┃    ┗ 📜application-test.yml  
 ┗ 📂test (JUnit5 / MockMvc 통합 및 단위 테스트)
```



### 🖥️ Frontend (src)

<summary>프론트 구조도</summary>
	

```
 📂src                  (메인 소스 코드)
 ┣ 📂app                (Next.js App Router 기반 페이지 라우팅)
 ┃ ┣ 📂admin
 ┃ ┃ ┣ 📂[id]
 ┃ ┃ ┃ ┗ 📂edit
 ┃ ┃ ┃    ┗ 📜page.tsx  (관리자 상품 수정 페이지)
 ┃ ┃ ┣ 📂orders
 ┃ ┃ ┃ ┗ 📜page.tsx     (관리자 주문 목록 페이지)
 ┃ ┃ ┗ 📜page.tsx       (관리자 대시보드 메인)
 ┃ ┣ 📂products
 ┃ ┃ ┗ 📂[id]
 ┃ ┃    ┗ 📜page.tsx    (사용자 상품 상세 페이지)
 ┃ ┣ 📜favicon.ico
 ┃ ┣ 📜globals.css
 ┃ ┣ 📜layout.tsx
 ┃ ┗ 📜page.tsx         (사용자 메인 페이지)
 ┣ 📂components         (재사용 가능한 UI 컴포넌트)
 ┃ ┣ 📂order-admin      (관리자 주문 관련 전용 컴포넌트)
 ┃ ┃ ┣ 📜ErrorMessage.tsx
 ┃ ┃ ┣ 📜OrderDetailModal.tsx
 ┃ ┃ ┗ 📜OrderStatusSelect.tsx
 ┃ ┣ 📜AdminNav.tsx
 ┃ ┣ 📜AdminOrderTable.tsx
 ┃ ┣ 📜AdminProductCard.tsx
 ┃ ┣ 📜AdminProductGrid.tsx
 ┃ ┣ 📜AdminSidebar.tsx
 ┃ ┣ 📜OrderComplete.tsx
 ┃ ┣ 📜ProductCard.tsx
 ┃ ┣ 📜ProductGrid.tsx
 ┃ ┣ 📜ProductWrapper.tsx
 ┃ ┗ 📜Sidebar.tsx
 ┣ 📂lib                (API 연동 및 공통 유틸리티)
 ┃ ┣ 📂errors
 ┃ ┃ ┗ 📜appError.ts
 ┃ ┗ 📂orders
 ┃    ┣ 📜mockOrders.ts
 ┃    ┗ 📜orderApi.ts
 ┗ 📂types              (TypeScript 타입 정의)
    ┣ 📜adminProduct.ts
    ┣ 📜order.ts
    ┗ 📜product.ts
```




<br>  

## :pushpin: 깃 컨벤션 (Git Convetion)

### 🧭브랜치전략

**Github-Flow**
<img width="1080" height="536" alt="image" src="https://github.com/user-attachments/assets/9cc5aa0e-10ed-4797-bdd8-750241229514" />


### 📝기능 브랜치 형식
-   `feat/{issue-number}-{feature-name}`
> **예시:**
> `feat/#24-complaint-feedback-CRUD`

| 브랜치 타입 (Type) | 설명 (Description) | 네이밍 규칙 및 예시 (Rule & Example) |
| --- | --- | --- |
| main| 실제 사용자에게 배포되는, 언제든 배포 가능한 안정적인 상태를 유지하는 메인 브랜치입니다. | main |
| develop | 다음 출시 버전을 위해 개발자들이 코드를 합치고 테스트하는 중심 브랜치입니다. | develop |
| feature | 새로운 기능이나 단위 작업을 개발할 때 develop에서 파생되는 브랜치입니다. | feature/이슈번호-기능명예: feature/#12-social-login |
| hotfix | 이미 배포된 main 브랜치에서 발생한 치명적인 버그를 긴급하게 수정할 때 사용합니다. | hotfix/이슈번호-버그명예: hotfix/#45-payment-crash |
| fix | develop 또는 feature 브랜치에서 개발 중에 발견된 일반적인 버그를 수정할 때 사용합니다. | bugfix/이슈번호-버그명예: bugfix/#28-ui-typo |
| release | 새로운 버전을 main으로 배포하기 전, 최종적인 QA(품질 검사) 및 버그 수정을 진행하는 브랜치입니다. | release/버전명예: release/v1.2.0 |
| refactor | 기능 변화 없이 코드 구조나 성능을 개선할 때 사용하는 브랜치입니다. | refactor/이슈번호-작업명예: refactor/#33-query-optimization |

<br>

### 📝 커밋 메시지 형식

<타입>: <이슈번호(optional)> <변경 요약>
> **예시:**
> `feat: #21 시설별 이용 횟수 통계`

### ✅ 커밋 타입 목록

| 타입 | 설명 |
| :--- | :--- |
| **feat** | 새로운 기능 추가 |
| **fix** | 버그 수정 |
| **style** | 코드 포맷 수정 (세미콜론 등) |
| **refactor** | 리팩토링 (기능 변경 없음) |
| **test** | 테스트 코드 추가/수정 |
| **chore** | 빌드 설정, 패키지 등 기타 변경 |
| **remove** | 사용하지 않는 코드/파일 제거 |
| **rename** | 파일 또는 폴더명 변경 |
 
