document.addEventListener('DOMContentLoaded', function() {
    // 더미 데이터
    const dummyData = {
        '2024-11-26': [
            { place: 'Restaurant', amount: 25000 },
            { place: 'Cafe', amount: 8000 }
        ],
        '2024-11-25': [
            { place: 'Supermarket', amount: 15000 },
            { place: 'Bookstore', amount: 12000 }
        ],
        '2024-11-27': [
            { place: 'Gym', amount: 20000 },
            { place: 'Pharmacy', amount: 5000 }
        ]
    };

    // 선택된 날짜를 'YYYY-MM-DD' 형식으로 가져오는 함수
    function getSelectedDate() {
        return localStorage.getItem("selectedDate") || getCurrentDate();
    }

    const selectedDate = getSelectedDate();
    console.log(`Selected Date: ${selectedDate}`); // 디버깅용 로그

    // 날짜별 데이터 업데이트 함수
    function updateAccountBook(date) {
        const accountList = document.getElementById('account-list');
        accountList.innerHTML = '';

        console.log(`Updating account book for date: ${date}`); // 디버깅용 로그
        if (dummyData[date]) {
            console.log(`Found data for date: ${date}`); // 디버깅용 로그
            dummyData[date].forEach(entry => {
                const listItem = document.createElement('li');
                listItem.className = 'account-item';

                const place = document.createElement('span');
                place.className = 'account-place';
                place.textContent = entry.place;

                const amount = document.createElement('span');
                amount.className = 'account-amount';
                amount.textContent = `${entry.amount.toLocaleString('ko-KR')} 원`;

                listItem.appendChild(place);
                listItem.appendChild(amount);
                accountList.appendChild(listItem);
            });
        } else {
            console.log(`No data found for date: ${date}`); // 디버깅용 로그
            accountList.innerHTML = '<li>No entries for this date.</li>';
        }
    }

    // 현재 날짜를 'YYYY-MM-DD' 형식으로 반환하는 함수
    function getCurrentDate() {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    // 초기 로드 시 데이터 업데이트
    updateAccountBook(selectedDate);

    // 외부에서 호출할 수 있도록 updateAccountBook 함수를 전역으로 설정
    window.updateAccountBook = updateAccountBook;
});

document.addEventListener('updateAccountBook', function(e) {
    const date = e.detail;
    updateAccountBook(date);
});
