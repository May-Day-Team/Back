document.addEventListener('DOMContentLoaded', function() {
    const selectedDate = localStorage.getItem('selectedDate');
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
        ]
    };

    const schedules = dummySchedules[selectedDate];
    console.log(`Schedules: ${JSON.stringify(schedules)}`); // 디버깅용 로그

    const scheduleList = document.getElementById('schedule-list');

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

