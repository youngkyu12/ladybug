# ladybug

`ladybug` 는 이번 학기 Android 2D 게임 개발 수업에서 진행할 기말 프로젝트이다.
이 문서는 앞으로 구현할 작업을 체크리스트 형태로 정리한 초안이다.

현재 상태:

- [x] 프로젝트 생성
- [ ] 기본 `MainActivity` 생성
- [x] `ViewBinding` 적용
- [x] `buildConfig` 생성 활성화

## Activity / Scene 구성
- `MainActivity` : 게임 타이틀이자 옵션 선택 용도로 사용
- `ladybughActivity` : In-game 화면.
  - 설정을 적용하여 실행
  - Scenes:
    - `MainScene` : ladybug가 움직임
    - `PauseScene` : 게임이 일시정지되어 재개/종료 를 물음
  - Classes/Objects:
    - `Player` : ladybug 주인공. `SheetSprite` 를 상속하게 될 예정.
    - `MapObject` : 화면 위쪽에서 생성되어 아래쪽으로 흐르는 맵 구성요소들의 공통 기반.
        - `Item` : 무작위로 생성되는 아이템. 화면 안 5개 제한. 5개 이상부터 순차적으로 있던 아이템 제거.
          - '꽃잎 폭탄', '나뭇잎 유도탄', '꽃잎 보호막' 등 총 8종
    - UI Components:
        - `Score` : 게임 진행상황 같은 값을 시각적으로 표시.
        - `Timer` : 게임 진행상황 같은 값을 시각적으로 표시.
        - `Button` : 자이로스코프 입력을 처리하는 UI 객체.

## Activity / App 시작

- [ ] 타이틀 화면(`MainActivity`) 구성
- [ ] 실제 게임 Activity(`LadybugActivity`) 추가 (layout xml 없이)
- [ ] `MainActivity` 에서 `LadybugActivity` 실행
  - [ ] Debug Build 시 1초 후 자동실행
- [ ] 게임 Activity 를 landscape mode 로 고정

## a2dg 연결

- [ ] `ladybug` 에 `a2dg` 모듈 연결
  - [ ] `ladybug` 에서 `a2dg` 모듈 복사/연결
  - [ ] gradle 파일에서 `a2dg` 사용 설정 (`settings.gradle.kts`)
  - [ ] `versions.toml` 의존성 항목 보강
  - [ ] `app` 모듈 의존성 연결 (`build.gradle.kts` / `:app`)
- [ ] `ladybugActivity` 가 `GameActivity`/`BaseGameActivity` 계열을 상속
- [ ] Activity 들을 `.app` package 로 옮김
- [ ] `MainScene` 생성 및 root scene push
- [ ] debug build 일 때만 debug 정보가 보이게 설정
- [ ] debug build 에서 Grid 표시
- [ ] `PauseScene` transparent scene 처리(`isTransparent` / `popAll` 포함)

## MainScene 배경 및 화면 좌표계 설정
- [ ] 가상좌표계 세로방향으로 설정

## Player / 입력 / 이동

- [ ] `Player` 클래스 추가
- [ ] `SheetSprite` 기반 상태(state)별 애니메이션 구성
- [ ] state 에 따라 프레임 Rect 집합 선택 및 애니메이션 전환
- [ ] 입력 처리( 자이로스코프 버튼 표시)
- [ ] 자이로스코프 동작 구현
- [ ] 8종 아이템 효과 적용

## Bullet / Spark / 기본 전투

- [ ] (추가) `Bullet` 클래스 추가
- [ ] 특정 아이템 획득 시 `Bullet` 발사
- [ ] 총알 시작 위치를 플레이어 중심보다 자연스러운 위치로 보정
- [ ] 총알이 화면 밖으로 나가면 삭제

## Enemy / 생성 규칙

- [ ] `Enemy` 클래스 추가
- [ ] `Enemy` 생성 및 배치
- [ ] 화면 위에서 생성되어 아래로 지나가게 처리
- [ ] 화면 밖으로 나가면 삭제
- [ ] `EnemyGenerator` 같은 생성 담당 객체 도입
- [ ] Timer 에 따른 생성 규칙 추가

## Map / 아이템

- [ ] 수직 스크롤 배경(`VertScrollBackground`) 적용
- [ ] 8종 아이템 추가 및 재활용 처리
- [ ] 게임 진행 상황 `Timer` 표시
- [ ] 게임 진행 상황 `Score` 표시

## 충돌 / 판정

- [ ] `IBoxCollidable` 적용
- [ ] `Player` 와 아이템 충돌 처리

## Game Loop / 상태 전환

- [ ] 일시정지/재개 처리
- [ ] `Back` 버튼 처리
- [ ] `Pause` 버튼 추가 및 입력 처리
- [ ] `PausedScene` push/pop 으로 일시정지 UI 구성

## 이펙트 / 마무리

- [ ] 아이템 소멸 이펙트 추가
- [ ] 리소스 정리 및 네이밍 통일
- [ ] 릴리즈 빌드 점검

## Notes

- 이 문서는 Android 2D 게임 개발 수업에서 교수님의 체크리스트 항목을 바탕으로 구성했다.
- 상황에 따라 항목이 추가/삭제될 수 있다.
