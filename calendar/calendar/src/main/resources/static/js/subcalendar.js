document.addEventListener('DOMContentLoaded', function() {
    const selectedDate = getCurrentDate();
    console.log(`Selected Date: ${selectedDate}`); // 디버깅용 로그

    // 날짜별 더미 일정 데이터
    const dummySchedules = {
        '2024-11-19': [
            { color: 'red', title: '회의' },
            { color: 'blue', title: '운동' }
        ],
        '2024-11-18': [
            { color: 'green', title: '저녁 약속' },
            { color: 'purple', title: '프레젠테이션 준비' }
        ],
        '2024-11-17': [
            { color: 'green', title: '저녁 약속' },
            { color: 'purple', title: '저녁 식사' }
        ]
    };

    // 선택된 날짜에 해당하는 일정을 가져옴
    const schedules = dummySchedules[selectedDate];
    console.log(`Schedules: ${JSON.stringify(schedules)}`); // 디버깅용 로그

    const scheduleList = document.getElementById('schedule-list');
    console.log(`scheduleList element: ${scheduleList}`); // 디버깅용 로그

    if (schedules) {
        scheduleList.innerHTML = '';
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
});

// 현재 날짜를 'YYYY-MM-DD' 형식으로 반환하는 함수
function getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}
