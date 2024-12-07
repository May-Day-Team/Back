// 더미 데이터
const dummyData = {
    '2024-12-01': [
        { place: 'Restaurant', amount: 25000 },
        { place: 'Cafe', amount: 8000 }
    ],
    '2024-12-02': [
        { place: 'Supermarket', amount: 15000 },
        { place: 'Bookstore', amount: 12000 }
    ],
    '2024-12-03': [
        { place: 'Gym', amount: 20000 },
        { place: 'Pharmacy', amount: 5000 }
    ],
    '2024-11-01': [
        { place: 'Restaurant', amount: 25000 },
        { place: 'Cafe', amount: 8000 }
    ],
    '2024-12-11': [
        { place: '데이트', amount: 100000 },
        { place: '대실', amount: 20000 }
    ],
    '2024-12-21': [
        { place: '롤스킨', amount: 25000 },
        { place: '피시방', amount: 8000 },
        { place: '피시방', amount: 8000 },
        { place: '피시방', amount: 8000 },
        { place: '피시방', amount: 8000 },
        { place: '피시방', amount: 8000 }
    ]
};

// 날짜별 데이터 업데이트 함수
function updateAccountBook(date) {
    const accountList = document.getElementById('account-list');
    accountList.innerHTML = '';

    console.log(`Updating account book for date: ${date}`); // 디버깅용 로그
    if (dummyData[date]) {
        console.log(`Found data for date: ${date}`); // 디버깅용 로그
        dummyData[date].forEach(entry => {
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
        console.log(`No data found for date: ${date}`); // 디버깅용 로그
        accountList.innerHTML = '<li>No entries for this date.</li>';
    }
}

// 더미 데이터
const dummySchedules = {
    '2024-12-01': [
        { color: 'red', title: '회의' },
        { color: 'blue', title: '운동' }
    ],
    '2024-12-02': [
        { color: 'green', title: '저녁 약속' },
        { color: 'purple', title: '프레젠테이션 준비' }
    ],
    '2024-12-03': [
        { color: 'orange', title: '점심 약속' },
        { color: 'gray', title: '산책' }
    ],
    '2024-11-01': [
        { color: 'red', title: '회의' },
        { color: 'blue', title: '운동' }
    ],
    '2024-12-11': [
        { color: 'black', title: '데이트' },
        { color: 'yellow', title: '외박' }
    ],
    '2024-12-21': [
        { color: 'skyblue', title: '내전' },
        { color: 'pink', title: '친구랑 피시방' },
        { color: 'white', title: '외식' },
        { color: 'pink', title: '친구랑 피시방' },
        { color: 'white', title: '외식' },
        { color: 'pink', title: '친구랑 피시방' },
        { color: 'white', title: '외식' }
    ]
};

function getSelectedDate() {
    return localStorage.getItem("selectedDate") || getCurrentDate();
}

const selectedDate = getSelectedDate();
console.log(`Selected Date: ${selectedDate}`); // 디버깅용 로그

function updateSchedules(date) {
    const schedules = dummySchedules[date];
    console.log(`Schedules: ${JSON.stringify(schedules)}`); // 디버깅용 로그

    const scheduleList = document.getElementById('schedule-list');
    scheduleList.innerHTML = '';

    if (schedules) {
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
    updateAccountBook(date);
    updateSchedules(date);
};
