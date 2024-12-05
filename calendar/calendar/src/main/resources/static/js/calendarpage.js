// 데이터
const backupSchedules = {};

function getSelectedDate() {
    return localStorage.getItem("selectedDate") || getCurrentDate();
}

const selectedDate = getSelectedDate();
console.log(`Selected Date: ${selectedDate}`); // 디버깅용 로그

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

function getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

$(document).ready(function () {
    let isAddSchedule = false; // 상태 변수 추가

    // 초기 화면에 month.html 로드
    $('#mainleft').load('../calendar/month.html', function (response, status, xhr) {
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
            $('#mainleft').load('../calendar/addschedule.html', function (response, status, xhr) {
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
        $('#mainleft').load('../calendar/month.html', function (response, status, xhr) {
            if (status === "error") {
                console.error("Failed to load month.html:", xhr.status, xhr.statusText);
            } else {
                window.location.reload();
                // 상태 변수 초기화
                isAddSchedule = false;
            }
        });
<<<<<<< HEAD
    }
    
        // 초기 로드 시 month.html 로드
        loadCalendar("month");
       // 월, 주, 일 버튼 클릭 시 해당 뷰 로드
       $('#month-view').click(function() {
        loadCalendar("month");
    });
<<<<<<< HEAD

    $('#week-view').click(function() {
        loadCalendar("week");
    });

    $('#day-view').click(function() {
        loadCalendar("day");
=======
    // subcalendar.html 로드
    $('#subcalendar').load('../Calendar/subcalendar.html', function(response, status, xhr) {
        if (status === "error") {
            console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
        } else {
            console.log("Sub Calendar page loaded successfully.");
        }
>>>>>>> zeyhnos
    });

    // + 버튼 클릭 시 addschedule.html 로드
    $('#addschedule').click(function() {
        $('#mainleft').load('../calendar/addschedule.html', function(response, status, xhr) {
            if (status === "error") {
                console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
            } else {
                console.log("Add Schedule page loaded successfully.");
            }
        });
    });

    // X 버튼과 취소 버튼 클릭 시 calendarpage.html로 이동
    $(document).on('click', '#close-modal, #cancel-schedule', function() {
        window.location.href = '../mainpage/calendarpage.html';
=======
>>>>>>> bigjihoon
    });
});

// 달력 초기화 함수 (예시)
function initCalendar() {
    // 여기에서 달력 초기화 로직을 작성합니다. 예를 들어, `calendarpage.js`에서 작성한 달력 초기화 코드들을 다시 실행합니다.
    console.log('Calendar is initialized');
    // 달력 관련 JavaScript 코드 추가
    // 예: loadCalendarData(), renderCalendar() 등
}

// 외부에서 호출할 수 있는 subcalendar 업데이트 함수
window.updateSubCalendar = function (date) {
    localStorage.setItem("selectedDate", date); // 선택된 날짜를 localStorage에 저장
    console.log("Updating Sub Account with date:", date); // 디버깅용 로그
    updateAccountBook(date);
    updateSchedules(date);
};
