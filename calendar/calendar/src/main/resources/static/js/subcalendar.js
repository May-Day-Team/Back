document.addEventListener('DOMContentLoaded', function() {
    // 더미 데이터
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
            { color: 'orange', title: '점심 약속' },
            { color: 'gray', title: '산책' }
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

    // 초기 로드 시 일정을 업데이트
    updateSchedules(selectedDate);

    // 외부에서 호출할 수 있도록 updateSchedules 함수를 전역으로 설정
    window.updateSchedules = updateSchedules;
});
