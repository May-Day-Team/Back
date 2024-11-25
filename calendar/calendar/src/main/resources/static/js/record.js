// 일기장 데이터를 저장하는 배열
let journals = [];

// 페이지 로드 시 저장된 일기 목록을 불러오는 함수
async function loadJournals() {
    try {
        const response = await fetch('/journal/load', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();
        if (data.result.result_code == 200) {
            journals = data.result.journals;
            updateJournalList();
        } else {
            const errorMessage = await response.text();
            alert('일기장 불러오기 실패: ' + errorMessage);
        }
    } catch (error) {
        console.error('Error during journal loading:', error);
        alert('일기장 불러오는 중 오류가 발생했습니다.');
    }
}

// 일기장 항목을 저장하거나 업데이트하는 함수
async function saveOrUpdateJournal() {
    const journalTitle = document.getElementById('journalTitle').value;
    const journalDate = document.getElementById('journalDate').value;
    const journalContent = document.getElementById('journalContent').value;
    if (journalTitle.trim() && journalDate && journalContent.trim()) {
        const currentIndex = document.getElementById('journalForm').dataset.currentIndex;
        if (currentIndex !== undefined && currentIndex !== '') {
            // 기존 일기장 업데이트
            journals[currentIndex] = { title: journalTitle, date: journalDate, content: journalContent };
        } else {
            // 새로운 일기장 저장
            journals.push({ title: journalTitle, date: journalDate, content: journalContent });
        }
        updateJournalList();
        clearForm();

        const journalRequest = {
            journals: journals
        };

        try {
            const response = await fetch('/journal/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(journalRequest)
            });

            const data = await response.json();
            if (data.result.result_code == 200) {
                console.log('Journal saved successfully:', data.result.result_description);
            } else {
                alert('일기장 저장 실패: ' + data.result.result_description);
            }
        } catch (error) {
            console.error('Error during journal saving:', error);
            alert('일기장 저장 중 오류가 발생했습니다.');
        }
    }
}

// 일기장 항목을 삭제하는 함수
async function deleteJournal() {
    const currentIndex = document.getElementById('journalForm').dataset.currentIndex;
    if (currentIndex !== undefined && currentIndex !== '') {
        journals.splice(currentIndex, 1);
        updateJournalList();
        clearForm();

        const journalRequest = {
            journals: journals
        };

        try {
            const response = await fetch('/journal/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(journalRequest)
            });

            const data = await response.json();
            if (data.result.result_code == 200) {
                console.log('Journal deleted successfully:', data.result.result_description);
            } else {
                alert('일기장 삭제 실패: ' + data.result.result_description);
            }
        } catch (error) {
            console.error('Error during journal deletion:', error);
            alert('일기장 삭제 중 오류가 발생했습니다.');
        }
    }
}

// 일기장 목록을 업데이트하는 함수
function updateJournalList() {
    const entries = document.getElementById('entries');
    entries.innerHTML = '';
    journals.forEach((entry, index) => {
        const listItem = document.createElement('li');
        listItem.textContent = `${entry.title} (${entry.date})`;
        listItem.onclick = () => loadJournal(index);
        entries.appendChild(listItem);
    });
}

// 일기장 항목을 편집기에 불러오는 함수
function loadJournal(index) {
    document.getElementById('journalTitle').value = journals[index].title;
    document.getElementById('journalDate').value = journals[index].date;
    document.getElementById('journalContent').value = journals[index].content;
    document.getElementById('journalForm').dataset.currentIndex = index;
}

// 폼을 초기화하는 함수
function clearForm() {
    document.getElementById('journalTitle').value = '';
    document.getElementById('journalDate').value = new Date().toISOString().split('T')[0];
    document.getElementById('journalContent').value = '';
    document.getElementById('journalForm').dataset.currentIndex = '';
}

// 로드 시 오늘의 날짜 설정 및 저장된 일기 목록 불러오기
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('journalDate').value = new Date().toISOString().split('T')[0];
    loadJournals(); // 저장된 일기 목록 불러오기
});
