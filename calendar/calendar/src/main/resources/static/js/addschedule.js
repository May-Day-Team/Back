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
        // mainleft에 month.html 로드 (단, 이미 로드된 상태에서는 다시 로드하지 않음)
        if ($('#mainleft').children().length === 0) {
            $('#mainleft').load('../Calendar/month.html', function(response, status, xhr) {
                if (status == "error") {
                    console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
                }
            });
        }
    });

    // 일정 저장 버튼 클릭 시 일정을 추가하는 로직
    $('#save-schedule').click(async function() {
        // 사용자 아이디를 임의로 "test"로 설정
        const userId = "test";

        // 필드 값 가져오기
        const c_startdate = $('#c_startdate').val();
        const c_enddate = $('#c_enddate').val();
        const c_title = $('#c_title').val();
        const c_content = $('#c_content').val();
        const c_place = $('#c_place').val();
        const c_repeat = $('input[name="c_repeat"]:checked').map(function() {
            return this.value;
        }).get();  // 선택된 요일들을 배열로 가져옴
        const c_starttime = $('#c_starttime').val();
        const c_endtime = $('#c_endtime').val();
        const c_alarm = $('#c_alarm').val();
        const c_color = $('#c_color').val();

        const scheduleRequest = {
            user_id: userId,  // 사용자 아이디 추가
            start_date: c_startdate,
            end_date: c_enddate,
            title: c_title,
            content: c_content,
            place: c_place,
            repeat_yn: c_repeat,  // 선택된 요일 배열 추가
            start_time: c_starttime,
            end_time: c_endtime,
            ring_at: c_alarm,
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

            const data = await response.json();
            if (data.result.result_code == 200) {
                console.log('Schedule added successfully:', data.result.result_description);
                $('#mainleft').load('../Calendar/month.html', function(response, status, xhr) {
                    if (status == "error") {
                        console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
                    }
                });
            } else {
                alert('일정 추가 실패: ' + data.result.result_description);
            }
        } catch (error) {
            console.error('Error during schedule addition:', error);
            alert('일정 추가 중 오류가 발생했습니다.');
        }
    });

    // 반복 여부 선택 이벤트 처리
    $('input[name="c_repeat"]').change(function() {
        const selectedValue = $(this).val();

        // '없음' 선택 시 다른 옵션 초기화
        if (selectedValue === 'none' && $(this).prop('checked')) {
            $('input[name="c_repeat"]').not(this).prop('checked', false).prop('disabled', false); // 다른 옵션들 체크 해제 및 활성화
        }

        // 다른 요일 선택 시 '없음' 초기화
        else if (selectedValue !== 'none' && $(this).prop('checked')) {
            $('input[name="c_repeat"][value="none"]').prop('checked', false); // '없음' 체크 해제
        } 
        
        // 다른 요일 선택 해제 시 '없음' 비활성화 해제
        let anyChecked = false;
        $('input[name="c_repeat"]').not('[value="none"]').each(function() {
            if ($(this).prop('checked')) {
                anyChecked = true;
            }
        });
        if (!anyChecked) {
            $('input[name="c_repeat"][value="none"]').prop('disabled', false);
        }
    });

    // Color 옵션에 네모난 색상 박스를 추가하는 로직
    $('#c_color').on('change', function() {
        const color = $(this).val();
        $('#color-box').css('background-color', color);
    }).trigger('change'); // 초기 로드 시에도 박스 색상 설정

    // 시작 시간을 알람 시간에 기본값으로 설정
    $('#c_starttime').on('input', function() {
        const startTime = $(this).val();
        $('#c_endtime').val(startTime);  // 종료 시간을 시작 시간과 동일하게 설정
        $('#c_endtime').attr('min', startTime);  // 종료 시간의 최소값을 시작 시간으로 설정
        $('#c_alarm').val(startTime);  // 알람 시간을 시작 시간에 맞추어 설정
    });

    // 페이지 로드 시 시작 시간을 알람 시간에 기본값으로 설정
    $('#c_alarm').val($('#c_starttime').val());
});
