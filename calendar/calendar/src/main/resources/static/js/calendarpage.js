$(document).ready(function() {
    // 오늘 날짜를 로드하는 함수
    function loadTodaySchedule() {
        const today = new Date();
        const selectedYear = today.getFullYear();
        const selectedMonth = today.getMonth();
        const selectedDate = today.getDate();

        const formattedDate = `${selectedYear}-${String(selectedMonth + 1).padStart(2, '0')}-${String(selectedDate).padStart(2, '0')}`;
        localStorage.setItem('selectedDate', formattedDate);
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
