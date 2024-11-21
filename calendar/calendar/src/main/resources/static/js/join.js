async function handleJoin(event) {
    event.preventDefault(); // 기본 폼 제출 방지
    
    const userId = document.getElementById('user_id').value;
    const userPw = document.getElementById('user_pw').value;
    const userName = document.getElementById('user_name').value;
    const userBirthdate = document.getElementById('user_birthdate').value;
    const userEmail = document.getElementById('user_email').value;
    const userHpn = document.getElementById('user_hpn').value;


    const joinRequest = {
      user_id: userId,
      user_pw: userPw,
      user_name: userName,
      user_birthdate: userBirthdate,
      user_email: userEmail,
      user_hpn: userHpn
    };

    try {
      const response = await fetch('/user/join', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(joinRequest)
      });

      if (response.ok) {
        const userData = await response.json();
        // 회원가입 성공 후 처리
        console.log('Join successful:', userData);
        // 예를 들어, 메인 페이지로 리다이렉트
        // window.location.href = '/main';
      } else {
        const errorMessage = await response.text();
        alert('회원가입 실패: ' + errorMessage);
      }
    } catch (error) {
      console.error('Error during Join:', error);
      alert('로그인 중 오류가 발생했습니다.');
    }
  }