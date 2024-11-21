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
            storedData[date] = dateEntries[date]; // 기존 데이터를 지우고 새로운 데이터로 동기화
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
        const rows = storedData[date].map(entry => `
            <tr class="added-row">
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

        $('#accountTable tbody').empty().append(rows);
    });

    // 저장 버튼 클릭 이벤트
    $(document).on('click', '.save-btn', function() {
        updateDateList();
        $('#accountTable tbody').empty(); // 오른쪽 내용 초기화
    });

    // 새로하기 버튼 클릭 이벤트
    $(document).on('click', '.clear-btn', function() {
        $('#accountTable tbody').empty(); // 오른쪽 내용 초기화
    });
});
