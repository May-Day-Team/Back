// 초기 데이터 (서버에서 데이터 불러오기 전에 기본값을 설정)
let accountData = {};  // 가계부 데이터
let scheduleData = {'2024-12-01': [
        { color: 'red', title: '회의' },
        { color: 'blue', title: '운동' }
    ]};  // 일정 데이터

// 서버에서 가계부 데이터를 가져오는 함수
async function fetchAccountData(date) {
    try {
        const response = await fetch(`/api/account/${date}`);
        if (data.result.result_code == 200) {
            throw new Error('Failed to fetch account data');
        }
        accountData = await response.json();
        updateAccountBook(date); // 가계부 업데이트
    } catch (error) {
        console.error('Error fetching account data:', error);
        accountData = {}; // 데이터 가져오지 못하면 빈 객체로 처리
        updateAccountBook(date);
    }
}

// 서버에서 일정 데이터를 가져오는 함수
async function fetchScheduleData(date) {
    try {
        const response = await fetch(`/api/schedules/${date}`);
        if (data.result.result_code == 200) {
            throw new Error('Failed to fetch schedule data');
        }
        scheduleData = await response.json();
        updateSchedules(date); // 일정 업데이트
    } catch (error) {
        console.error('Error fetching schedule data:', error);
        scheduleData = {}; // 데이터 가져오지 못하면 빈 객체로 처리
        updateSchedules(date);
    }
}

// 가계부 데이터 업데이트 함수
function updateAccountBook(date) {
    const accountList = document.getElementById('account-list');
    accountList.innerHTML = '';

    console.log(`Updating account book for date: ${date}`);
    if (accountData[date]) {
        console.log(`Found data for date: ${date}`);
        accountData[date].forEach(entry => {
            const listItem = document.createElement('li');
            listItem.className = 'account-item';

            const place = document.createElement('span');
            place.className = 'account-place';
            place.textContent = entry.place;

            const amount = document.createElement('span');
            amount.className = 'account-amount';
            amount.textContent = `${entry.amount.toLocaleString('ko-KR')} 원`;

            listItem.appendChild(place);
            listItem.appendChild(amount);
            accountList.appendChild(listItem);
        });
    } else {
        console.log(`No data found for date: ${date}`);
        accountList.innerHTML = '<li>No entries for this date.</li>';
    }
}

// 일정 업데이트 함수
function updateSchedules(date) {
    const schedules = scheduleData[date] || []; // 일정이 없으면 빈 배열
    console.log(`Schedules for ${date}: ${JSON.stringify(schedules)}`);

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

// 날짜 업데이트를 위한 기본 함수
function getSelectedDate() {
    return localStorage.getItem("selectedDate") || getCurrentDate();
}

const selectedDate = getSelectedDate();
console.log(`Selected Date: ${selectedDate}`);

// 날짜 선택 시 스케줄과 가계부 업데이트
function updateSubCalendar(date) {
    localStorage.setItem("selectedDate", date); // 선택된 날짜를 localStorage에 저장
    console.log("Updating Sub Account with date:", date);
    fetchAccountData(date);  // 가계부 데이터 가져오기
    fetchScheduleData(date); // 일정 데이터 가져오기
}

// 초기 로드 시 데이터 업데이트
$(document).ready(function() {
    $('#mainleft').load('../Calendar/index.html', function(response, status, xhr) {
        if (status == "error") {
            console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
        } else {
            const todayFormattedDate = new Date().toISOString().split('T')[0];
            localStorage.setItem("selectedDate", todayFormattedDate);
            if (window.updateSubCalendar) {
                window.updateSubCalendar(todayFormattedDate);
            }
        }
    });

    // 'Friends' 메뉴 클릭 시 모달 띄우기
    $('#friends-option').click(function() {
        $('#friends-modal-overlay').fadeIn();
    });

    // 모달 바깥 부분 클릭 시 모달 닫기
    $('#friends-modal-overlay').click(function(e) {
        if ($(e.target).is('#friends-modal-overlay')) {
            $('#friends-modal-overlay').fadeOut();
        }
    });

    // 모달 내에서 '닫기' 버튼 클릭 시 모달 닫기
    $(document).on('click', '#friends-modal-overlay .friends-window__bg', function() {
        $('#friends-modal-overlay').fadeOut();
    });
});

// 외부에서 호출할 수 있는 subcalendar 업데이트 함수
window.updateSubCalendar = function(date) {
    localStorage.setItem("selectedDate", date); // 선택된 날짜를 localStorage에 저장
    console.log("Updating Sub Account with date:", date); // 디버깅용 로그
    fetchAccountData(date);  // 가계부 데이터 가져오기
    fetchScheduleData(date); // 일정 데이터 가져오기
};
