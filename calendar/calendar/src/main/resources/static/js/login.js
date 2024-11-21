async function handleLogin(event) {
    event.preventDefault(); // 기본 폼 제출 방지
    
    const userId = document.getElementById('user_id').value;
    const userPw = document.getElementById('user_pw').value;

    const loginRequest = {
      user_id: userId,
      user_pw: userPw
    }
    console.log(loginRequest);

    try {
      const response = await fetch('/open-api/user/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({body: loginRequest})
      });

      if (response.ok) {
        const userData = await response.json();
        // 로그인 성공 후 처리
        console.log('Login successful:', userData);
        // 예를 들어, 메인 페이지로 리다이렉트
        window.location.href = '/mainpage/mayday.html';
      } else {
        const errorMessage = await response.text();
        alert('로그인 실패: ' + errorMessage);
      }
    } catch (error) {
      console.error('Error during login:', error);
      alert('로그인 중 오류가 발생했습니다.');
    }
  }