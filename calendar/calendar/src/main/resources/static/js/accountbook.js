$(document).ready(function() {
    const today = new Date().toISOString().split('T')[0]; // 현재 날짜를 YYYY-MM-DD 형식으로 가져오기

    let storedData = {};

    // 행 추가 버튼 클릭 이벤트
    $('.add-row-btn').click(function() {
        const newRow = `
            <tr class="added-row">
                <td><input type="date" class="date-input" value="${today}"/></td>
                <td contenteditable="true"></td>
                <td>
                    <select class="amount-type">
                        <option value="+">+</option>
                        <option value="-">-</option>
                    </select>
                    <input type="text" class="amount-input" />
                    <span>원</span>
                </td>
                <td><button class="delete-btn">Delete</button></td>
            </tr>
        `;
        $('#accountTable tbody').append(newRow);
    });

    // 행 삭제 버튼 클릭 이벤트
    $(document).on('click', '.delete-btn', function() {
        $(this).closest('tr').remove();
    });

    // 금액 입력 시 3자리마다 쉼표 추가
    $(document).on('input', '.amount-input', function() {
        const value = $(this).val().replace(/,/g, '');
        const formattedValue = Number(value).toLocaleString('ko-KR');
        $(this).val(formattedValue);
    });

    // 날짜별 리스트 업데이트 함수
    function updateDateList() {
        const rows = $('#accountTable tbody tr.added-row').get();
        const dateEntries = {};
        const totals = {};

        rows.forEach(row => {
            const date = $(row).find('.date-input').val();
            const place = $(row).find('td:nth-child(2)').text();
            const amountType = $(row).find('.amount-type').val();
            const amount = $(row).find('.amount-input').val();
            const parsedAmount = parseFloat(amount.replace(/,/g, '')) || 0;
            const finalAmount = amountType === "-" ? -parsedAmount : parsedAmount;

            if (date) {
                if (!dateEntries[date]) {
                    dateEntries[date] = [];
                }
                dateEntries[date].push({ place, amount: finalAmount });

                if (!totals[date]) {
                    totals[date] = 0;
                }
                totals[date] += finalAmount;
            }
        });

        Object.keys(dateEntries).forEach(date => {
            if (!storedData[date]) {
                storedData[date] = [];
            }
            dateEntries[date].forEach(entry => {
                // 중복 확인 후 추가
                if (!storedData[date].some(e => e.place === entry.place && e.amount === entry.amount)) {
                    storedData[date].push(entry);
                }
            });
        });

        const dateEntriesHtml = Object.keys(storedData).map(date => `
            <li data-date="${date}">
                <strong>${date}</strong>
                <ul>
                    ${storedData[date].map(entry => `<li>${entry.place} ${entry.amount.toLocaleString('ko-KR')} 원</li>`).join('')}
                </ul>
                <div><strong>Total: ${storedData[date].reduce((sum, entry) => sum + entry.amount, 0).toLocaleString('ko-KR')} 원</strong></div>
            </li>
        `).join('');
        $('#dateEntries').html(dateEntriesHtml);
    }

    // 날짜별 항목 클릭 시 로드
    $(document).on('click', '#dateEntries li', function() {
        const date = $(this).data('date');

        // 오른쪽 테이블 초기화
        $('#accountTable tbody').empty();

        // 선택된 날짜의 데이터를 테이블에 추가
        const rows = storedData[date].map(entry => `
            <tr class="loaded-row">
                <td><input type="date" class="date-input" value="${date}"/></td>
                <td contenteditable="true">${entry.place}</td>
                <td>
                    <select class="amount-type">
                        <option value="+" ${entry.amount >= 0 ? 'selected' : ''}>+</option>
                        <option value="-" ${entry.amount < 0 ? 'selected' : ''}>-</option>
                    </select>
                    <input type="text" class="amount-input" value="${Math.abs(entry.amount).toLocaleString('ko-KR')}" />
                    <span>원</span>
                </td>
                <td><button class="delete-btn">Delete</button></td>
            </tr>
        `).join('');

        $('#accountTable tbody').append(rows);
    });

    // 저장 버튼 클릭 이벤트
    $(document).on('click', '.save-btn', async function() {
        const rows = $('#accountTable tbody tr').not('.loaded-row').get(); // 불러온 데이터는 제외하고 새로운 데이터만 저장

        // 저장할 데이터가 없는 경우 리턴
        if (rows.length === 0) {
            return;
        } 

        rows.forEach(row => {
            const date = $(row).find('.date-input').val();
            const place = $(row).find('td:nth-child(2)').text();
            const amountType = $(row).find('.amount-type').val();
            const amount = $(row).find('.amount-input').val();
            const parsedAmount = parseFloat(amount.replace(/,/g, '')) || 0;
            const finalAmount = amountType === "-" ? -parsedAmount : parsedAmount;

            if (date) {
                if (!storedData[date]) {
                    storedData[date] = [];
                }
                storedData[date].push({ place, amount: finalAmount });
            }
        });

        const accountRequest = {
            data: storedData
        };

        try {
            const response = await fetch('/accountbook/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(accountRequest)
            });

            const data = await response.json();
            if (data.result.result_code == 200) {
                console.log('Data saved successfully:', data.result.result_description);
            } else {
                alert('데이터 저장 실패: ' + data.result.result_description);
            }  
        } catch (error) {
            console.error('Error during data saving:', error);
            alert('데이터 저장 중 오류가 발생했습니다.');
        }

        updateDateList();
        $('#accountTable tbody').empty(); // 오른쪽 내용 초기화
    });

    // 새로하기 버튼 클릭 이벤트
    $(document).on('click', '.clear-btn', function() {
        $('#accountTable tbody').empty(); // 오른쪽 내용 초기화
    });

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

});
