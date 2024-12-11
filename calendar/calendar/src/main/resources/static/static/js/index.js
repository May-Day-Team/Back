const daysTag = document.querySelector(".days"),
      currentDate = document.querySelector(".current-date"),
      currentYear = document.querySelector(".current-year"),
      prevNextIcon = document.querySelectorAll(".icons span"),
      resetDateButton = document.querySelector(".reset-date-button");

let date = new Date(),
    currYear = date.getFullYear(),
    currMonth = date.getMonth(),
    selectedDay = date.getDate();

const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

// 서수를 반환하는 함수
const getOrdinalSuffix = (day) => {
    if (day > 3 && day < 21) return 'th'; // 4 ~ 20
    switch (day % 10) {
        case 1: return "st";
        case 2: return "nd";
        case 3: return "rd";
        default: return "th";
    }
};

// 월별 캘린더 렌더링
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
        let formattedDate = `${currYear}-${String(currMonth + 1).padStart(2, "0")}-${String(i).padStart(2, "0")}`;
        let scheduleColors = scheduleData[formattedDate] || [];  // dummySchedules → scheduleData
        let limitedScheduleColors = scheduleColors.slice(0, 4); // 최대 4개로 제한
        let scheduleColorDivs = limitedScheduleColors.map(schedule => `<div class="schedule-color" style="background-color: ${schedule.color};"></div>`).join("");
        liTag += `<li class="${isSelected}" data-month="${currMonth}" data-year="${currYear}" data-day="${i}">
                    ${i}
                    ${scheduleColorDivs}
                  </li>`;
    }

    // 다음 달의 빈 날짜
    for (let i = lastDayofMonth; i < 6; i++) {
        let nextMonth = currMonth + 1 > 11 ? 0 : currMonth + 1;
        let nextYear = currMonth + 1 > 11 ? currYear + 1 : currYear;
        liTag += `<li class="inactive" data-month="${nextMonth}" data-year="${nextYear}" data-day="${i - lastDayofMonth + 1}">${i - lastDayofMonth + 1}</li>`;
    }

    // 캘린더 렌더링
    daysTag.innerHTML = liTag;
    currentDate.innerHTML = `${months[currMonth]} ${selectedDay}<sup>${getOrdinalSuffix(selectedDay)}</sup>`;
    currentYear.innerHTML = currYear.toString();

    document.querySelectorAll(".days li").forEach(day => {
        day.addEventListener("click", function () {
            const active = daysTag.querySelector(".active");
            if (active) active.classList.remove("active");
            this.classList.add("active");

            const clickedDay = this.getAttribute("data-day");
            const clickedMonth = parseInt(this.getAttribute("data-month")); // 문자열을 정수로 변환
            const clickedYear = this.getAttribute("data-year");
            
            selectedDay = parseInt(clickedDay);
            currMonth = clickedMonth;
            currYear = clickedYear;
            currentDate.innerHTML = `${months[clickedMonth]} ${selectedDay}<sup>${getOrdinalSuffix(selectedDay)}</sup>`;
            currentYear.innerHTML = currYear.toString();

            const formattedDate = `${clickedYear}-${String(clickedMonth + 1).padStart(2, "0")}-${String(clickedDay).padStart(2, "0")}`;
            localStorage.setItem("selectedDate", formattedDate);

            if (window.updateSubCalendar) {
                window.updateSubCalendar(formattedDate);  // 메인 페이지의 updateSubCalendar 호출
            }
        });
    });
};

const resetDate = () => {
    date = new Date(); // 현재 날짜로 초기화
    currYear = date.getFullYear();
    currMonth = date.getMonth();
    selectedDay = date.getDate();
    const todayFormattedDate = `${currYear}-${String(currMonth + 1).padStart(2, "0")}-${String(selectedDay).padStart(2, "0")}`;
    localStorage.setItem("selectedDate", todayFormattedDate);
    renderCalendar(); // 캘린더 재렌더링

    // 오늘 날짜로 subaccount 업데이트 함수 호출
    if (window.updateSubCalendar) {
        window.updateSubCalendar(todayFormattedDate);  // 메인 페이지의 updateSubCalendar 호출
    }
};

renderCalendar();

// Reset 버튼 이벤트 추가
if (resetDateButton) {
    resetDateButton.addEventListener("click", resetDate);
}

// 월 변경 시 1일로 고정
prevNextIcon.forEach(icon => {
    icon.addEventListener("click", () => {
        if (icon.id === "prev") {
            currMonth--;
            if (currMonth < 0) { // 1월에서 전월 버튼 클릭 시
                currMonth = 11;
                currYear--; // 연도 감소
            }
        } else {
            currMonth++;
            if (currMonth > 11) { // 12월에서 다음월 버튼 클릭 시
                currMonth = 0;
                currYear++; // 연도 증가
            }
        }
        selectedDay = 1; // 월 변경 시 1일로 고정
        renderCalendar();

        // 선택된 날짜를 localStorage에 저장 및 subcalendar 업데이트
        const formattedDate = `${currYear}-${String(currMonth + 1).padStart(2, "0")}-01`;
        localStorage.setItem("selectedDate", formattedDate);
        if (window.updateSubCalendar) {
            window.updateSubCalendar(formattedDate);  // 메인 페이지의 updateSubCalendar 호출
        }
    });
});
