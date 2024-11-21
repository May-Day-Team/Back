const daysTag = document.querySelector(".days"),
      currentDate = document.querySelector(".current-date"),
      currentYear = document.querySelector(".current-year"), // 년도 출력용 요소
      prevNextIcon = document.querySelectorAll(".icons span");
let date = new Date(),
    currYear = date.getFullYear(),
    currMonth = date.getMonth();
const months = ["January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"];

const renderCalendar = () => {
    let firstDayofMonth = new Date(currYear, currMonth, 1).getDay(), 
        lastDateofMonth = new Date(currYear, currMonth + 1, 0).getDate(),
        lastDayofMonth = new Date(currYear, currMonth, lastDateofMonth).getDay(),
        lastDateofLastMonth = new Date(currYear, currMonth, 0).getDate();
    let liTag = "";
    
    // 이전 달의 빈 날짜
    for (let i = firstDayofMonth; i > 0; i--) {
        liTag += `<li class="inactive">${lastDateofLastMonth - i + 1}</li>`;
    }
    
    // 현재 달의 날짜
    for (let i = 1; i <= lastDateofMonth; i++) {
        let isToday = i === date.getDate() && currMonth === new Date().getMonth() && currYear === new Date().getFullYear() ? "active" : "";
        liTag += `<li class="${isToday}" data-day="${i}">${i}</li>`; // data-day 속성 추가
    }
    
    // 다음 달의 빈 날짜
    for (let i = lastDayofMonth; i < 6; i++) {
        liTag += `<li class="inactive">${i - lastDayofMonth + 1}</li>`;
    }
    
    // 년도와 달을 각각의 요소에 출력
    currentDate.innerText = months[currMonth]; // 달 출력
    currentYear.innerText = currYear; // 년도 출력
    daysTag.innerHTML = liTag;

    // 각 날짜에 클릭 이벤트 추가
    document.querySelectorAll(".days li").forEach(day => {
        day.addEventListener("click", function() {
            const active = daysTag.querySelector('.active');
            if (active) {
                active.classList.remove('active'); // 기존 active 클래스 제거
            }
            this.classList.add('active'); // 클릭한 날짜에 active 클래스 추가
        });
    });
}

const resetDate = () => {
    date = new Date(); // 현재 날짜로 초기화
    currYear = date.getFullYear();
    currMonth = date.getMonth();
    renderCalendar(); // 캘린더 재렌더링
}

renderCalendar();

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
        renderCalendar();
    });
});
