// DOM 요소들 선택
const daysTag = document.querySelector(".days"),
      currentDate = document.querySelector(".current-date"),
      currentYear = document.querySelector(".current-year"),
      prevNextIcon = document.querySelectorAll(".icons span"),
      viewButtons = document.querySelectorAll(".view-buttons button"),
      addSchedule = document.querySelectorAll(".wahha"),
      addScheduleAWHNE = document.querySelectorAll(".awhne"),
      eventListContainer = document.getElementById('eventListContainer'), // 일정 리스트 컨테이너
      addEventButton = document.getElementById('addEventButton'), // 일정 추가 버튼
      modal = document.getElementById('modal'), // 모달
      closeModalButton = document.getElementById('close-modal'), // 모달 닫기 버튼
      modalTitle = document.getElementById('modal-title'),
      modalDetails = document.getElementById('modal-details'); // 모달 내용
      modalPlace = document.getElementById('modal-place'); // 장소 추가
      deleteEventButton = document.getElementById('delete-event-btn'); // 삭제 버튼

let date = new Date(),
    currYear = date.getFullYear(),
    currMonth = date.getMonth(),
    currentView = "date"; // 기본 뷰는 주로 설정

const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

// 주별 캘린더 렌더링
const renderWeekView = () => {
    const startOfWeek = new Date(currYear, currMonth, date.getDate() - date.getDay()); // 주의 첫날 (일요일)
    const endOfWeek = new Date(currYear, currMonth, startOfWeek.getDate() + 6); // 주의 마지막날 (토요일)

    currentDate.innerText = `${months[currMonth]} ${startOfWeek.getDate()} ~ ${endOfWeek.getDate()}`;
    currentYear.innerText = currYear;

    const weekDays = [];
    for (let i = 0; i < 7; i++) {
        const day = new Date(startOfWeek);
        day.setDate(startOfWeek.getDate() + i);
        weekDays.push(day.getDate());
    }

    const dodwElements = document.querySelectorAll(".week .datehead .dodw");
    dodwElements.forEach((element, index) => {
        element.innerText = weekDays[index];
    });

    syncScheduleSize();
}

// 이전/다음 주로 이동하는 함수
const changeWeek = (direction) => {
    date.setDate(date.getDate() + direction * 7);
    currYear = date.getFullYear();
    currMonth = date.getMonth();
    renderWeekView();
}

// 뷰 변경 함수
const changeView = (view) => {
    currentView = view;
    if (view === "month") {
        renderCalendar();
    } else if (view === "week") {
        renderWeekView();
    } else if (view === "day") {
        renderDayView();
    }
}

// 월, 주, 일 버튼 클릭 시 해당 뷰로 전환
viewButtons.forEach(button => {
    button.addEventListener("click", () => {
        changeView(button.id.replace("-view", ""));
    });
});

// 좌우 버튼 클릭 시 이전/다음 주로 이동
prevNextIcon.forEach(icon => {
    icon.addEventListener("click", () => {
        if (icon.id === "prev") {
            changeWeek(-1);
        } else if (icon.id === "next") {
            changeWeek(1);
        }
    });
});

// 시간 설정
function setHourlyTime() {
    const timeElements = document.querySelectorAll(".gmt09time__tm");

    if (timeElements.length !== 24) {
        console.error("There should be exactly 24 .gmt09time__tm elements.");
        return;
    }

    // 0시부터 23시까지의 시간 배열 생성
    for (let i = 1; i < 24; i++) {
        const hour = i < 10 ? `0${i}:00` : `${i}:00`; // 00:00, 01:00, ..., 23:00

        // 각 .gmt09time__tm 요소에 시간 삽입
        timeElements[i].textContent = hour;
    }
}

// 페이지 로드시 시간 설정
setHourlyTime();


// week schedule의 크기 동기화 함수
const syncScheduleSize = () => {
    const dateheads = document.querySelectorAll(".datehead");
    const weekschedules = document.querySelectorAll(".weekschedule");

    dateheads.forEach((datehead, index) => {
        const dateheadWidth = datehead.offsetWidth;
        if (weekschedules[index]) {
            weekschedules[index].style.width = `${dateheadWidth}px`;
        }
    });
}

// 바 생성 함수
const createBar = (wahhaElement, awhneWidth, eventData) => {
    const bar = document.createElement('div');
    bar.classList.add('schedule-bar');
    bar.style.width = `${awhneWidth}px`; // awhne의 너비만큼 바의 너비를 설정
    bar.style.height = '15px'; // 바의 높이는 15px
    bar.style.backgroundColor = '#8846f3'; // 바의 색상 설정
    bar.style.position = 'absolute'; // 절대 위치로 설정하여 wahha 내에서 배치

    // 바 클릭 시 모달에 더미 데이터 표시
    bar.addEventListener('click', () => {
        showModal(eventData, wahhaElement, bar); // eventData와 wahhaElement, bar를 함께 넘겨줌
    });

    wahhaElement.appendChild(bar); // wahha 내부에 바를 추가
}

// 모달을 표시하는 함수
function showModal(eventData, wahhaElement, barElement) {
    modalTitle.textContent = eventData.title || "이벤트 제목 없음";
    modalDetails.textContent = `내용: ${eventData.content || "내용 없음"}\n시간: ${formatDate(eventData.start_time)} ~ ${formatDate(eventData.end_time)}`;
    modalPlace.textContent = `장소: ${eventData.place || "장소 정보 없음"}`; // 장소 정보 추가

    // 삭제 버튼 클릭 시 일정 삭제
    deleteEventButton.addEventListener('click', () => {
        deleteEvent(wahhaElement, barElement); // 일정 삭제 함수 호출
        modal.style.display = "none"; // 모달 닫기
    });

    modal.style.display = "block"; // 모달 표시
}

// 일정 삭제 함수
function deleteEvent(wahhaElement, barElement) {
    // 바를 삭제
    if (barElement) {
        barElement.remove();
    }
}

// 시간 형식을 'YYYY-MM-DD HH:mm'에서 'HH:mm'으로 변경하는 함수
function formatDate(dateString) {
    const date = new Date(dateString);
    const hours = date.getHours();
    const minutes = date.getMinutes();
    return `${hours < 10 ? '0' + hours : hours}:${minutes < 10 ? '0' + minutes : minutes}`;
}

// 일정 추가 함수 (사용자가 일정 추가 버튼 클릭 시 호출)
function addEventFromData(eventData) {
    const wahhaElements = document.querySelectorAll(".wahha"); // 날짜별로 바를 넣을 위치

    // 첫 번째 wahha 요소에 바 추가
    if (wahhaElements.length > 0) {
        const wahhaElement = wahhaElements[0]; // 예시로 첫 번째 날짜에 추가
        const awhneWidth = 100; // 바의 너비 (예시로 100px)
        createBar(wahhaElement, awhneWidth, eventData); // 바를 생성하고 추가
    }
}

// 일정 추가 버튼 클릭 시, 더미 데이터 기반으로 일정 추가
addEventButton.addEventListener('click', function() {
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
        "color": "BLACK",
        "block_yn": "N",
        "repeat_yn": "N"
    };
    addEventFromData(dummyData); // 더미 데이터로 일정 추가
});

// 모달 닫기 버튼 클릭 시 모달 닫기
closeModalButton.addEventListener('click', () => {
    modal.style.display = "none";
});

// 모달 외부 클릭 시 모달 닫기
window.addEventListener('click', (event) => {
    if (event.target === modal) {
        modal.style.display = "none";
    }
});

// 최초 캘린더 렌더링
renderWeekView();