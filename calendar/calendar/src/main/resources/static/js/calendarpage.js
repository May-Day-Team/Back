$(document).ready(function() {
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
            '2024-11-28': [
                { color: 'red', title: '회의' },
                { color: 'blue', title: '운동' }
            ],
            '2024-11-27': [
                { color: 'green', title: '저녁 약속' },
                { color: 'purple', title: '프레젠테이션 준비' }
            ],
            '2024-11-29': [
                { color: 'orange', title: '점심 약속' },
                { color: 'gray', title: '산책' }
            ],
            '2024-12-01': [
                { color: 'red', title: '회의' },
                { color: 'blue', title: '운동' }
            ],
            '2024-11-11': [
                { color: 'black', title: '데이트' },
                { color: 'yellow', title: '외박' }
            ],
            '2024-11-21': [
                { color: 'skyblue', title: '내전' },
                { color: 'pink', title: '친구랑 피시방' },
                { color: 'white', title: '외식' }
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

    // 캘린더를 로드하는 함수
    function loadCalendar(view) {
        $('#mainleft').load(`../calendar/${view}.html`, function(response, status, xhr) {
            if (status === "error") {
                console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
            } else {
                if (view === "month") {
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
    
                    // 이전, 다음 버튼 클릭 이벤트 추가
                    $(document).on('click', '.icons span', function() {
                        const iconId = $(this).attr('id');
                        if (iconId === 'prev') {
                            currMonth -= 1;
                            if (currMonth < 0) {
                                currMonth = 11;
                                currYear -= 1;
                            }
                        } else if (iconId === 'next') {
                            currMonth += 1;
                            if (currMonth > 11) {
                                currMonth = 0;
                                currYear += 1;
                            }
                        }
                        selectedDay = 1; // 월 변경 시 첫째 날로 설정
                        renderCalendar();
                    });
    
                    // 월별 캘린더 렌더링 함수
                    const renderCalendar = () => {
                        const today = new Date(); // 현재 날짜를 동적으로 가져오기
                        let firstDayofMonth = new Date(currYear, currMonth, 1).getDay(),
                            lastDateofMonth = new Date(currYear, currMonth + 1, 0).getDate(),
                            lastDayofMonth = new Date(currYear, currMonth, lastDateofMonth).getDay(),
                            lastDateofLastMonth = new Date(currYear, currMonth, 0).getDate();
                        let liTag = "";
    
                        // 이전 달의 빈 날짜
                        for (let i = firstDayofMonth; i > 0; i--) {
                            let prevMonth = currMonth - 1 < 0 ? 11 : currMonth - 1;
                            let prevYear = currMonth - 1 < 0 ? currYear - 1 : currYear;
                            liTag += `<li class="inactive" data-month="${prevMonth}" data-year="${prevYear}" data-day="${lastDateofLastMonth - i + 1}">${lastDateofLastMonth - i + 1}</li>`;
                        }
    
                        // 현재 달의 날짜
                        for (let i = 1; i <= lastDateofMonth; i++) {
                            let isSelected = i === selectedDay ? "active" : "";
                            liTag += `<li class="${isSelected}" data-month="${currMonth}" data-year="${currYear}" data-day="${i}">${i}</li>`;
                        }
    
                        // 다음 달의 빈 날짜
                        for (let i = lastDayofMonth; i < 6; i++) {
                            let nextMonth = currMonth + 1 > 11 ? 0 : currMonth + 1;
                            let nextYear = currMonth + 1 > 11 ? currYear + 1 : currYear;
                            liTag += `<li class="inactive" data-month="${nextMonth}" data-year="${nextYear}" data-day="${i - lastDayofMonth + 1}">${i - lastDayofMonth + 1}</li>`;
                        }
    
                        // 캘린더 렌더링
                        $('.days').html(liTag);
                        $('.current-date').html(`${months[currMonth]} ${selectedDay}`);
                        $('.current-year').html(currYear);
    
                        // 중복 이벤트 제거 후 새 이벤트 등록
                        $('.days li').off('click').on('click', function() {
                            $('.days li').removeClass('active');
                            $(this).addClass('active');
                            const clickedDay = $(this).data('day');
                            const clickedMonth = $(this).data('month');
                            const clickedYear = $(this).data('year');
                            selectedDay = clickedDay;
                            currMonth = clickedMonth;
                            currYear = clickedYear;
    
                            const formattedDate = `${clickedYear}-${String(clickedMonth + 1).padStart(2, '0')}-${String(clickedDay).padStart(2, '0')}`;
                            console.log(`Selected Date: ${formattedDate}`); // 디버깅용 로그
                            localStorage.setItem('selectedDate', formattedDate);
                            loadSchedule(formattedDate);
                        });
                    };
    
                    // 초기 캘린더 렌더링
                    renderCalendar();
                }
            }
        });
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
    });
});
