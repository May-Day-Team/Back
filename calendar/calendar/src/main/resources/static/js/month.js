const daysTag = document.querySelector(".days"),
      currentDate = document.querySelector(".current-date"),
      currentYear = document.querySelector(".current-year"),
      prevNextIcon = document.querySelectorAll(".icons span"),
      viewButtons = document.querySelectorAll(".view-buttons button"),
      resetDateButton = document.querySelector(".reset-date-button");

let date = new Date(),
    currYear = date.getFullYear(),
    currMonth = date.getMonth(),
    selectedDay = date.getDate(),
    currentView = "month"; // 기본 뷰는 월로 설정

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
}

// 월별 캘린더 렌더링
const renderCalendar = () => {
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
        let scheduleColors = dummySchedules[formattedDate] || [];
        let scheduleColorDivs = scheduleColors.map(schedule => `<div class="schedule-color" style="background-color: ${schedule.color};"></div>`).join("");
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
    currentYear.innerText = currYear;

    // 중복 이벤트 제거 후 새 이벤트 등록
    document.querySelectorAll(".days li").forEach(day => {
        day.replaceWith(day.cloneNode(true)); // 기존 노드를 복제하여 이벤트 제거
    });

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
            currentDate.innerHTML = `${months[clickedMonth]} ${clickedDay}<sup>${getOrdinalSuffix(clickedDay)}</sup>`;

            // 선택된 날짜를 localStorage에 저장
            const formattedDate = `${clickedYear}-${String(clickedMonth + 1).padStart(2, "0")}-${String(clickedDay).padStart(2, "0")}`;
            console.log("Selected Date:", formattedDate); // 로그 출력
            localStorage.setItem("selectedDate", formattedDate);

            // subcalendar 업데이트 함수 호출
            if (window.updateSubCalendar) {
                window.updateSubCalendar(formattedDate);
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
};
// Reset 버튼 이벤트 추가
if (resetDateButton) {
    resetDateButton.addEventListener("click", resetDate);
}

// 이전, 다음 버튼 클릭 시 월 전환
prevNextIcon.forEach(icon => {
    icon.addEventListener("click", () => {
        currMonth = icon.id === "prev" ? currMonth - 1 : currMonth + 1;
        if (currMonth < 0 || currMonth > 11) {
            currYear = icon.id === "prev" ? currYear - 1 : currYear + 1;
            currMonth = icon.id === "prev" ? 11 : 0;
        }
        selectedDay = 1; // 월 변경 시 첫째 날로 설정
        renderCalendar();

        // 다음달로 넘어갈 때 자동으로 1일로 설정 후 calendarpage.html에 반영
        const formattedDate = `${currYear}-${String(currMonth + 1).padStart(2, "0")}-${String(selectedDay).padStart(2, "0")}`;
        localStorage.setItem("selectedDate", formattedDate);
        if (window.updateSubCalendar) {
            window.updateSubCalendar(formattedDate);
        }
    });
});

// 초기 렌더링
renderCalendar();
