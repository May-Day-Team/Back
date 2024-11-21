$(document).ready(function() {
    // 오늘 날짜를 로드하는 함수
    function loadTodaySchedule() {
        const today = new Date();
        const selectedYear = today.getFullYear();
        const selectedMonth = today.getMonth();
        const selectedDate = today.getDate();

        const formattedDate = `${selectedYear}-${String(selectedMonth + 1).padStart(2, '0')}-${String(selectedDate).padStart(2, '0')}`;
        localStorage.setItem('selectedDate', formattedDate);
        loadSchedule(formattedDate); // 일정을 로드하는 함수를 호출
    }

    // 스케줄을 로드하는 함수
    function loadSchedule(date) {
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
                { color: 'pink', title: '저녁 약속' },
                { color: 'black', title: '저녁 식사' }
            ]
        };

        const schedules = dummySchedules[date];
        const scheduleList = $('#schedule-list');

        if (schedules) {
            scheduleList.empty();
            schedules.forEach(schedule => {
                const listItem = $('<li>').addClass('schedule-item');
                const colorBox = $('<div>').addClass('schedule-color').css('background-color', schedule.color);
                const title = $('<span>').addClass('schedule-title').text(schedule.title);

                listItem.append(colorBox).append(title);
                scheduleList.append(listItem);
            });
        } else {
            scheduleList.html('<li>No schedules for this date.</li>');
        }
    }

    // 초기 로드 시 캘린더 로드
    $('#mainleft').load('../Calendar/month.html', function(response, status, xhr) {
        if (status === "error") {
            console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
        } else {
            // 페이지 로드 시 오늘 날짜의 일정을 자동으로 로드
            loadTodaySchedule();

            // 날짜 클릭 이벤트 추가
            $(document).on('click', '.days li', function() {
                $('.days li').removeClass('active');
                $(this).addClass('active');
                const selectedDate = $(this).data('day');
                const selectedMonth = $(this).data('month');
                const selectedYear = $(this).data('year');
                console.log(`Selected Date: ${selectedYear}-${selectedMonth}-${selectedDate}`); // 디버깅용 로그

                // 로컬 스토리지에 선택된 날짜 저장
                const formattedDate = `${selectedYear}-${String(selectedMonth + 1).padStart(2, '0')}-${String(selectedDate).padStart(2, '0')}`;
                localStorage.setItem('selectedDate', formattedDate);
                loadSchedule(formattedDate); // 선택된 날짜의 일정을 로드
            });
        }
    });

    // subcalendar.html 로드
    $('#subcalendar').load('../Calendar/subcalendar.html', function(response, status, xhr) {
        if (status === "error") {
            console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
        } else {
            console.log("Sub Calendar page loaded successfully.");
        }
    });

    // + 버튼 클릭 시 addschedule.html 로드
    $('#addschedule').click(function() {
        $('#mainleft').load('../Calendar/addschedule.html', function(response, status, xhr) {
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
    });
});
