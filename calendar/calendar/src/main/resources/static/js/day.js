// DOM 요소들 선택
const prevNextIcon = document.querySelectorAll(".icons span");
const addEventButton = document.getElementById('addEventButton'); // 일정 추가 버튼
const modal = document.getElementById('modal'); // 모달
const closeModalButton = document.getElementById('close-modal'); // 모달 닫기 버튼
const modalTitle = document.getElementById('modal-title');
const modalDetails = document.getElementById('modal-details'); // 모달 내용
const modalPlace = document.getElementById('modal-place'); // 장소 추가
const deleteEventButton = document.getElementById('delete-event-btn'); // 삭제 버튼
const modalStartTime = document.getElementById('modal-start-time'); // 시작 시간 추가
const modalEndTime = document.getElementById('modal-end-time'); // 종료 시간 추가
let date = new Date(); // 현재 날짜
let currYear = date.getFullYear();
let currMonth = date.getMonth();

// 이벤트 저장 배열
let eventDataList = [];

// 날짜와 요일을 설정하는 함수
function setDateAndDay() {
    const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    const dayElement = document.querySelector('.day');
    const dateElement = document.querySelector('.date');
    
    // 오늘의 요일과 날짜 설정
    const day = days[date.getDay()];
    const dayNumber = date.getDate();
    
    dayElement.textContent = day;
    dateElement.textContent = dayNumber;
    
    // 일요일은 빨간색, 토요일은 파란색으로 설정
    if (day === 'Sunday') {
        dayElement.style.color = '#ce0000';
    } else if (day === 'Saturday') {
        dayElement.style.color = '#0060ce';
    } else {
        dayElement.style.color = 'black';
    }
}

// 페이지 로드 시 날짜와 요일 설정
setDateAndDay();

// 하루씩 이동하는 함수
const changeDay = (direction) => {
    date.setDate(date.getDate() + direction); // 하루씩 이동 (양수: 다음날, 음수: 이전날)
    currYear = date.getFullYear();
    currMonth = date.getMonth();
    setDateAndDay(); // 날짜와 요일 다시 설정
}

// 좌우 버튼 클릭 시 하루 앞, 하루 뒤로 이동
prevNextIcon.forEach(icon => {
    icon.addEventListener("click", () => {
        if (icon.id === "prev") {
            changeDay(-1); // 하루 뒤로 이동
        } else if (icon.id === "next") {
            changeDay(1); // 하루 앞으로 이동
        }
    });
});

// 시간 설정 (0시부터 23시까지의 시간 배열 생성)
function setHourlyTime() {
    const timeElements = document.querySelectorAll(".gmt09time__tm");

    if (timeElements.length !== 24) {
        console.error("There should be exactly 24 .gmt09time__tm elements.");
        return;
    }

    // 0시부터 23시까지의 시간 배열 생성
    for (let i = 0; i < 24; i++) {
        const hour = i < 10 ? `0${i}:00` : `${i}:00`; // 00:00, 01:00, ..., 23:00

        // 각 .gmt09time__tm 요소에 시간 삽입
        timeElements[i].textContent = hour;
    }
}

// 페이지 로드시 시간 설정
setHourlyTime();

//---------------------------------------------------------------------------------------

// 이벤트가 없을 때 "이벤트 없음" 표시
function updateEventDisplay(events) {
    const noneEventsDiv = document.querySelector('.none-events');
    const eventBars = document.querySelectorAll('.event-bar');
    
    if (events.length === 0) {
        // 이벤트가 없을 때
        noneEventsDiv.classList.add('show');
        eventBars.forEach(eventBar => eventBar.remove());
    } else {
        // 이벤트가 있을 때
        noneEventsDiv.classList.remove('show');
    }
}

//---------------------------------------------------------------------------------------

// 모달 열기 함수
function openModal(event) {
    const eventData = event.target.closest('.event-bar').data;
    modalTitle.textContent = eventData.title;
    modalDetails.textContent = `내용: ${eventData.content}`;
    modalPlace.textContent = `장소: ${eventData.place}`;
    modalStartTime.textContent = `시작 시간: ${eventData.start_time}`;
    modalEndTime.textContent = `종료 시간: ${eventData.end_time}`;
    deleteEventButton.data = eventData; // 삭제 버튼에 이벤트 데이터 저장
    modal.style.display = 'block';
}

// 모달 닫기 함수
function closeModal() {
    modal.style.display = 'none';
}

// 모달 닫기 버튼 클릭 이벤트
closeModalButton.addEventListener('click', closeModal);

//---------------------------------------------------------------------------------------

// 이벤트 삭제 함수
function deleteEvent() {
    const eventData = deleteEventButton.data;
    const eventBars = document.querySelectorAll('.event-bar');
    eventBars.forEach(eventBar => {
        if (eventBar.data === eventData) {
            eventBar.remove();
        }
    });
    // eventDataList에서 해당 이벤트 삭제
    eventDataList = eventDataList.filter(data => data !== eventData);
    updateEventDisplay(eventDataList);
    closeModal(); // 모달 닫기
}

// 삭제 버튼 클릭 이벤트
deleteEventButton.addEventListener('click', deleteEvent);

// 더미 데이터를 이용해 이벤트 추가
function addEvent(dummyData) {
    eventDataList.push(dummyData); // 이벤트 리스트에 추가
    const eventArea = document.querySelector('.event-area > div');
    const eventBar = document.createElement('div');
    eventBar.classList.add('event-bar');

    const eventColorTack = document.createElement('div');
    eventColorTack.classList.add('event-bar__colortack');
    eventColorTack.style.backgroundColor = dummyData.color; // 더미 데이터의 색상 적용

    const eventTitle = document.createElement('div');
    eventTitle.classList.add('event-bar__title');
    const eventTitleContent = document.createElement('li');
    eventTitleContent.classList.add('event-bar__content', 'event-text');
    eventTitleContent.textContent = dummyData.title;
    eventTitle.appendChild(eventTitleContent);

    const eventTime = document.createElement('div');
    eventTime.classList.add('event-bar__time', 'event-text');
    const allTime = document.createElement('div');
    allTime.classList.add('event-bar__alltime');
    allTime.setAttribute('aria-hidden', 'true');
    allTime.textContent = '종일';
    const startTime = document.createElement('div');
    startTime.classList.add('event-bar__starttime');
    startTime.textContent = dummyData.start_time.split('T')[1];
    const endTime = document.createElement('div');
    endTime.classList.add('event-bar__endtime');
    endTime.textContent = dummyData.end_time.split('T')[1];
    eventTime.appendChild(allTime);
    eventTime.appendChild(startTime);
    eventTime.appendChild(document.createElement('div')); // Divider
    eventTime.appendChild(endTime);

    eventBar.appendChild(eventColorTack);
    eventBar.appendChild(eventTitle);
    eventBar.appendChild(eventTime);

    eventBar.data = dummyData; // 더미 데이터를 eventBar에 저장

    // 이벤트 바 클릭 시 모달 열기
    eventBar.addEventListener('click', openModal);

    eventArea.appendChild(eventBar);
}

// 일정 추가 버튼 클릭 시 이벤트 추가
addEventButton.addEventListener('click', () => {
    const dummyData = {
        "cal_id": 1,
        "user_id": "shs00925",
        "title": "2조 회의",
        "content": "김호용 교수님의 즐거운 과제 시간",
        "start_date": "2024-11-13T00:00:00",
        "start_time": "2024-11-13T11:00:00",
        "end_date": "2024-11-14T00:00:00",
        "end_time": "2024-11-14T15:00:00",
        "ring_at": "2024-11-13T10:00:00",
        "place": "영진전문대학교 도서관 스터디룸 10",
        "color": "black", // 색상은 CSS 색상 값으로 변경
        "block_yn": "N",
        "repeat_yn": "N"
    };

    addEvent(dummyData);

    // 현재 추가된 모든 이벤트를 가져와서 배열로 전달
    updateEventDisplay(eventDataList);
});

// 초기 호출
updateEventDisplay(eventDataList); // 이 함수는 이벤트 목록에 따라 호출
