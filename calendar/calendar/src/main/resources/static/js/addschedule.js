$(document).ready(function() {
    // 로컬 스토리지에서 날짜 데이터를 불러오기
    const selectedDate = localStorage.getItem('selectedDate');

    if (selectedDate) {
        $('#c_startdate').val(selectedDate);
        $('#c_enddate').val(selectedDate);  // 필요시 변경 가능
        console.log(`Loaded Start Date: ${selectedDate}`); // 디버깅용 로그
    } else {
        console.log("No date selected in localStorage.");
    }

    // 모달 닫기 버튼 클릭 시 모달 숨기기
    $('#close-modal, #cancel-schedule').click(function() {
        // mainleft에 month.html 로드
        $('#mainleft').load('../Calendar/month.html', function(response, status, xhr) {
            if (status == "error") {
                console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
            }
        });
    });

    // 일정 저장 버튼 클릭 시 일정을 추가하는 로직
    $('#save-schedule').click(async function() {
        // 필드 값 가져오기
        const c_startdate = $('#c_startdate').val();
        const c_enddate = $('#c_enddate').val();
        const c_title = $('#c_title').val();
        const c_content = $('#c_content').val();
        const c_place = $('#c_place').val();
        const c_repeat = $('#c_repeat').val();
        const c_starttime = $('#c_starttime').val();
        const c_endtime = $('#c_endtime').val();
        const c_alarm = $('#c_alarm').val();
        const c_block = $('#c_block').val();
        const c_color = $('#c_color').val();

        const scheduleRequest = {
            start_date: c_startdate,
            end_date: c_enddate,
            title: c_title,
            content: c_content,
            place: c_place,
            repeat_yn: c_repeat,
            start_time: c_starttime,
            end_time: c_endtime,
            ring_at: c_alarm,
            block_yn: c_block,
            color: c_color
        };
        console.log(scheduleRequest);
        try {
            const response = await fetch('/schedule/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(scheduleRequest)
            });

            if (response.ok) {
                console.log('Schedule added successfully:', await response.json());
                $('#mainleft').load('../Calendar/month.html', function(response, status, xhr) {
                    if (status == "error") {
                        console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
                    }
                });
            } else {
                const errorMessage = await response.text();
                alert('일정 추가 실패: ' + errorMessage);
            }
        } catch (error) {
            console.error('Error during schedule addition:', error);
            alert('일정 추가 중 오류가 발생했습니다.');
        }
    });

    // Color 옵션에 네모난 색상 박스를 추가하는 로직
    $('#c_color').on('change', function() {
        const color = $(this).val();
        $('#color-box').css('background-color', color);
    }).trigger('change'); // 초기 로드 시에도 박스 색상 설정

    // 시작 시간을 알람 시간에 기본값으로 설정
    $('#c_starttime').on('input', function() {
        $('#c_alarm').val($(this).val());
    });

    // 페이지 로드 시 시작 시간을 알람 시간에 기본값으로 설정
    $('#c_alarm').val($('#c_starttime').val());
});
