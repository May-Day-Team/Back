const daysTag = document.querySelector(".days"),
      currentDate = document.querySelector(".current-date"),
      currentYear = document.querySelector(".current-year"),
      prevNextIcon = document.querySelectorAll(".icons span"),
      viewButtons = document.querySelectorAll(".view-buttons button");

let date = new Date(),
    currYear = date.getFullYear(),
    currMonth = date.getMonth(),
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
        let isToday = i === today.getDate() && currMonth === today.getMonth() && currYear === today.getFullYear() ? "active" : "";
        liTag += `<li class="${isToday}" data-month="${currMonth}" data-year="${currYear}" data-day="${i}">${i}</li>`;
    }

    // 다음 달의 빈 날짜
    for (let i = lastDayofMonth; i < 6; i++) {
        let nextMonth = currMonth + 1 > 11 ? 0 : currMonth + 1;
        let nextYear = currMonth + 1 > 11 ? currYear + 1 : currYear;
        liTag += `<li class="inactive" data-month="${nextMonth}" data-year="${nextYear}" data-day="${i - lastDayofMonth + 1}">${i - lastDayofMonth + 1}</li>`;
    }

    // 캘린더 렌더링
    daysTag.innerHTML = liTag;
    currentDate.innerHTML = `${months[currMonth]} ${today.getDate()}<sup>${getOrdinalSuffix(today.getDate())}</sup>`;
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
            currentDate.innerHTML = `${months[clickedMonth]} ${clickedDay}<sup>${getOrdinalSuffix(clickedDay)}</sup>`;

            // 선택된 날짜를 localStorage에 저장
            const formattedDate = `${clickedYear}-${String(clickedMonth + 1).padStart(2, "0")}-${String(clickedDay).padStart(2, "0")}`;
            console.log("Selected Date:", formattedDate); // 로그 출력
            localStorage.setItem("selectedDate", formattedDate);

            // subcalendar.html 로드
            $("#subcalendar").load("../Calendar/subcalendar.html", function (response, status, xhr) {
                if (status === "error") {
                    console.error("An error occurred:", xhr.status, xhr.statusText);
                } else {
                    console.log("Sub Calendar page loaded successfully.");
                }
            });
        });
    });
};


// 주별 캘린더 렌더링
const renderWeekView = () => {
    const startOfWeek = new Date(currYear, currMonth, date.getDate() - date.getDay()); // 주의 첫날
    const endOfWeek = new Date(currYear, currMonth, startOfWeek.getDate() + 6); // 주의 마지막날

    currentDate.innerHTML = `${months[currMonth]} ${startOfWeek.getDate()}<sup>${getOrdinalSuffix(startOfWeek.getDate())}</sup> ~ ${endOfWeek.getDate()}<sup>${getOrdinalSuffix(endOfWeek.getDate())}</sup>`;
    currentYear.innerText = currYear;

    let liTag = "";
    for (let i = 0; i < 7; i++) {
        const day = new Date(startOfWeek);
        day.setDate(startOfWeek.getDate() + i);
        liTag += `<li>${day.getDate()}</li>`;
    }

    daysTag.innerHTML = liTag;
}

// 일별 캘린더 렌더링
const renderDayView = () => {
    const today = new Date();
    currentDate.innerHTML = `${months[currMonth]} ${today.getDate()}<sup>${getOrdinalSuffix(today.getDate())}</sup>`;
    currentYear.innerText = currYear;

    daysTag.innerHTML = `<li>${today.getDate()}</li>`;
}

// 뷰 변경 함수
const changeView = (view) => {
    currentView = view;
    if (view === "month") {
        renderCalendar();
    } else if (view === "week") {
        renderWeekView();
    } else if (view === "day") {
        renderDayView();
    }
}

// 월, 주, 일 버튼 클릭 시 해당 뷰로 전환
viewButtons.forEach(button => {
    button.addEventListener("click", () => {
        changeView(button.id.replace("-view", ""));
    });
});

const resetDate = () => {
    date = new Date(); // 현재 날짜로 초기화
    currYear = date.getFullYear();
    currMonth = date.getMonth();
    renderCalendar(); // 캘린더 재렌더링
}

// 이전, 다음 버튼 클릭 시 월 전환
prevNextIcon.forEach(icon => {
    icon.addEventListener("click", () => {
        currMonth = icon.id === "prev" ? currMonth - 1 : currMonth + 1;
        if (currMonth < 0 || currMonth > 11) {
            date = new Date(currYear, currMonth, new Date().getDate());
            currYear = date.getFullYear();
            currMonth = date.getMonth();
        } else {
            date = new Date();
        }
        if (currentView === "month") {
            renderCalendar();
        } else if (currentView === "week") {
            renderWeekView();
        } else if (currentView === "day") {
            renderDayView();
        }
    });
});

// 초기 렌더링
renderCalendar();
