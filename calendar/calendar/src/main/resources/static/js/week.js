const daysTag = document.querySelector(".days"),
      currentDate = document.querySelector(".current-date"),
      currentYear = document.querySelector(".current-year"),
      prevNextIcon = document.querySelectorAll(".icons span"),
      viewButtons = document.querySelectorAll(".view-buttons button");

let date = new Date(),
    currYear = date.getFullYear(),
    currMonth = date.getMonth(),
    currentView = "week"; // 기본 뷰는 주로 설정

const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

// 주별 캘린더 렌더링 (단순히 현재 주를 표시)
const renderWeekView = () => {
    const startOfWeek = new Date(currYear, currMonth, date.getDate() - date.getDay()); // 주의 첫날 (일요일)
    const endOfWeek = new Date(currYear, currMonth, startOfWeek.getDate() + 6); // 주의 마지막날 (토요일)

    currentDate.innerText = `${months[currMonth]} ${startOfWeek.getDate()} ~ ${endOfWeek.getDate()}`;
    currentYear.innerText = currYear;

    // 날짜를 배열로 저장
    const weekDays = [];
    for (let i = 0; i < 7; i++) {
        const day = new Date(startOfWeek);
        day.setDate(startOfWeek.getDate() + i);
        weekDays.push(day.getDate());
    }

    // 각 요일에 날짜를 동적으로 추가
    const dodwElements = document.querySelectorAll(".week .datehead .dodw");
    dodwElements.forEach((element, index) => {
        element.innerText = weekDays[index];
    });
}

// 이전/다음 주로 이동하는 함수
const changeWeek = (direction) => {
    // 현재 주의 시작일을 기준으로 이동
    date.setDate(date.getDate() + direction * 7); // 7일씩 더하거나 빼기 (다음 주/저번 주)

    // 새로운 년도, 월 계산
    currYear = date.getFullYear();
    currMonth = date.getMonth();
    
    renderWeekView(); // 변경된 주 렌더링
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

// 좌우 버튼 클릭 시 이전/다음 주로 이동
prevNextIcon.forEach(icon => {
    icon.addEventListener("click", () => {
        if (icon.id === "prev") {
            changeWeek(-1); // 저번 주로 이동
        } else if (icon.id === "next") {
            changeWeek(1); // 다음 주로 이동
        }
    });
});

// 초기 캘린더 렌더링
renderWeekView();
