document.getElementById('join').addEventListener('submit', handleJoin);

async function handleJoin(event) {
    event.preventDefault(); // 폼 기본 제출 방지

    // 폼 값 가져오기
    const userId = document.getElementById('user_id').value;
    const userPw = document.getElementById('user_pw').value;
    const userName = document.getElementById('user_name').value;
    const userBirthdate = document.getElementById('user_birthdate').value;
    const userEmail = document.getElementById('user_email').value;
    const userPhoneNumber = document.getElementById('user_phone_number').value;

    // 객체로 데이터 준비
    const joinRequest = {
        user_id: userId,
        password: userPw,
        name: userName,
        birthdate: userBirthdate,
        email: userEmail,
        phone_number: userPhoneNumber
    };

    try {
        const response = await fetch('/open-api/user/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({body: joinRequest}) // JSON 형식으로 변환하여 전송
        });
        console.log(response);

        if (response.ok) {
            // 성공적으로 처리된 경우
            const result = await response.json();
            console.log('성공:', result);
        } else {
            // 오류가 발생한 경우
            const errorText = await response.text();
            console.error('오류 발생:', errorText);
        }
    } catch (error) {
        console.error('요청 실패:', error);
    }
}
