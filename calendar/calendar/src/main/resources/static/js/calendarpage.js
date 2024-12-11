// 데이터 저장 객체
const backupSchedules = {};

// 선택된 날짜를 가져오는 함수
function getSelectedDate() {
    return localStorage.getItem("selectedDate") || getCurrentDate();
}

// 현재 날짜를 YYYY-MM-DD 형식으로 반환
function getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// 스케줄 데이터를 업데이트하는 함수
function updateSchedules(date) {
    const schedules = backupSchedules[date] || []; // 데이터가 없으면 빈 배열 반환
    console.log(`Schedules: ${JSON.stringify(schedules)}`); // 디버깅용 로그

    const scheduleList = document.getElementById('schedule-list');
    scheduleList.innerHTML = '';

    if (schedules.length > 0) {
        schedules.forEach(schedule => {
            const listItem = document.createElement('li');
            listItem.className = 'schedule-item';

            const colorBox = document.createElement('div');
            colorBox.className = 'schedule-color';
            colorBox.style.backgroundColor = schedule.color;

            const title = document.createElement('span');
            title.className = 'schedule-title';
            title.textContent = schedule.title;

            listItem.appendChild(colorBox);
            listItem.appendChild(title);
            scheduleList.appendChild(listItem);
        });
    } else {
        scheduleList.innerHTML = '<li>No schedules for this date.</li>';
    }
}

// /api/calendar에서 스케줄 데이터를 가져오는 함수
async function fetchSchedules() {
    try {
        const selectedDate = getSelectedDate(); // 현재 선택된 날짜 가져오기
        console.log(`Fetching schedules for date: ${selectedDate}`); // 디버깅용 로그

        // API 호출
        const response = await fetch(`/api/calendar`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });
        console.log(response);

        if (!response.ok) {
            throw new Error(`Failed to fetch schedules: ${response.status}`);
        }

        // JSON 데이터 파싱
        const data = await response.json();

        console.log("Fetched Schedules Data:", data); // 디버깅용 로그

        // 가져온 데이터를 backupSchedules에 저장
        backupSchedules[selectedDate] = data;

        // 스케줄 업데이트
        updateSchedules(selectedDate);
    } catch (error) {
        console.error("Error fetching schedules:", error);
    }
}

// 페이지 초기화 및 이벤트 설정
$(document).ready(function () {
    let isAddSchedule = false; // 상태 변수 추가

    // 초기 화면에 month.html 로드
    $('#mainleft').load('/view/month', function (response, status, xhr) {
        if (status === "error") {
            console.error("Failed to load month.html:", xhr.status, xhr.statusText);
        } else {
            // month.html을 성공적으로 로드한 후 달력 초기화 코드 실행
            initCalendar();
        }
    });

    // 'Add Schedule' 버튼 클릭 시 동작
    $('#addschedule').click(function () {
        if (!isAddSchedule) {
            // addschedule.html 로드
            $('#mainleft').load('/view/addschedule', function (response, status, xhr) {
                if (status === "error") {
                    console.error("Failed to load addschedule.html:", xhr.status, xhr.statusText);
                } else {
                    isAddSchedule = true; // 상태 전환
                }
            });
        } else {
            // 페이지 새로고침
            window.location.reload();
        }
    });

    // 'Friends' 메뉴 클릭 시 모달 띄우기
    $('#friends-option').click(function () {
        $('#friends-modal-overlay').fadeIn();
    });

    // 모달 바깥 부분 클릭 시 모달 닫기
    $('#friends-modal-overlay').click(function (e) {
        if ($(e.target).is('#friends-modal-overlay')) {
            $('#friends-modal-overlay').fadeOut();
        }
    });

    // 모달 내에서 '닫기' 버튼 클릭 시 모달 닫기
    $(document).on('click', '#friends-modal-overlay .friends-window__bg', function () {
        $('#friends-modal-overlay').fadeOut();
    });

    // 'x' 버튼이나 '취소' 버튼 클릭 시 addschedule.html을 닫고 calendarpage.html 로드
    $(document).on('click', '#close-modal, #cancel-schedule', function () {
        // `mainleft`에 calendarpage.html을 다시 로드
        $('#mainleft').load('/view/month', function (response, status, xhr) {
            if (status === "error") {
                console.error("Failed to load month.html:", xhr.status, xhr.statusText);
            } else {
                window.location.reload();
                // 상태 변수 초기화
                isAddSchedule = false;
            }
        });
    });

    // 스케줄 데이터 가져오기
    fetchSchedules();
});

// 달력 초기화 함수 (예시)
function initCalendar() {
    console.log('Calendar is initialized');
}

// 외부에서 호출할 수 있는 subcalendar 업데이트 함수
window.updateSubCalendar = function (date) {
    localStorage.setItem("selectedDate", date); // 선택된 날짜를 localStorage에 저장
    console.log("Updating Sub Account with date:", date); // 디버깅용 로그
    updateSchedules(date);
};
