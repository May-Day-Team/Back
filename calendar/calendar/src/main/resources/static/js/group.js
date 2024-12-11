// 초기 로드 시 캘린더 로드
$('#groupcalendar').load('../Calendar/index.html', function(response, status, xhr) {
    if (status === "error") {
        console.log("An error occurred: " + xhr.status + " " + xhr.statusText);
    } else {
        // 페이지 로드 시 오늘 날짜의 일정을 자동으로 로드
        loadTodaySchedule();

        // 날짜 클릭 이벤트 추가
        $(document).on('click', '.days li', function() {
            $('.days li').removeClass('active');
            $(this).addClass('active');
            const selectedDate = $(this).data('day');
            const selectedMonth = $(this).data('month');
            const selectedYear = $(this).data('year');
            console.log(`Selected Date: ${selectedYear}-${selectedMonth}-${selectedDate}`); // 디버깅용 로그

            // 로컬 스토리지에 선택된 날짜 저장
            const formattedDate = `${selectedYear}-${String(selectedMonth + 1).padStart(2, '0')}-${String(selectedDate).padStart(2, '0')}`;
            localStorage.setItem('selectedDate', formattedDate);
            loadSchedule(formattedDate); // 선택된 날짜의 일정을 로드
        });
    }
});
$(document).ready(function() {
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
// ---------------------------------------------------------------------------
//
//
// 그룹 생성 부분
$(document).ready(function() {
    // 그룹 생성 클릭 시 모달 띄우기
    $('.create-group').click(function() {
        $('#create-group-modal').fadeIn();
    });

    // 모달 바깥 부분 클릭 시 모달 닫기
    $('#create-group-modal').click(function(e) {
        if ($(e.target).is('#create-group-modal')) {
            $('#create-group-modal').fadeOut();
        }
    });

    // 모달 내에서 '닫기' 버튼 클릭 시 모달 닫기
    $(document).on('click', '#create-group-modal .friends-window__bg', function() {
        $('#create-group-modal').fadeOut();
    });
});
// ---------------------------------------------------------------------------
//
//
// 그룹 프로필 설정 부분
document.addEventListener("DOMContentLoaded", () => {
  const profileChangeButton = document.getElementById('profile-change-button');
  const profileImageInput = document.getElementById('profile-image-input');
  const profileImage = document.getElementById('profile-image');
  const outprofileImage = document.getElementById('out-profile-image');
  const defaultImage = "../image/img.gif"; // 기본 이미지 경로

  // 기본 이미지 설정
  profileImage.src = defaultImage;
  outprofileImage.src = defaultImage;

  // 이벤트 리스너가 중복으로 등록되지 않도록 한 번만 설정
  if (profileChangeButton) {
      profileChangeButton.removeEventListener('click', handleProfileChangeClick); // 이전 리스너 제거
      profileChangeButton.addEventListener('click', handleProfileChangeClick); // 새 리스너 추가
  }

  // 프로필 이미지 변경 버튼 클릭 시 파일 선택 창 열기
  function handleProfileChangeClick() {
      // 클릭했을 때만 파일 입력창이 뜨게 한다
      if (profileImageInput) {
          profileImageInput.click();
      }
  }

  // 파일 선택 시 이미지 업데이트
  profileImageInput.addEventListener('change', (event) => {
      const file = event.target.files[0];
      if (file) {
          const reader = new FileReader();
          reader.onload = (e) => {
              profileImage.src = e.target.result; // 선택한 이미지 표시
          };
          reader.readAsDataURL(file);
      } else {
          profileImage.src = defaultImage; // 파일 선택 취소 시 기본 이미지 복원
      }
  });
});

// ---------------------------------------------------------------------------
//
//
// 그룹 생성 버튼 클릭 시 작동 부분
document.addEventListener("DOMContentLoaded", () => {
  const makeGroupButton = document.getElementById("make-group");
  const groupNameInput = document.querySelector(".group-name-input");
  const profileImageInput = document.getElementById('profile-image-input');
  const profileImage = document.getElementById('profile-image');
  const outProfileImage = document.getElementById('out-profile-image');
  const groupHeader = document.getElementById('groupheader');

  // 그룹 생성 버튼 클릭 시
  makeGroupButton.addEventListener("click", (event) => {
      const groupName = groupNameInput.value.trim(); // 입력값에서 공백 제거
      if (!groupName) {
          alert("그룹 이름을 작성해주세요"); // 경고 메시지 표시
          event.preventDefault(); // 버튼 기본 동작(예: 폼 제출) 방지
          return;
      }

      // 그룹 이름과 이미지 반영
      groupHeader.textContent = groupName;  // groupheader에 그룹 이름 추가
      profileImage.src = outProfileImage.src;  // 그룹 이미지 동기화
      alert(`그룹 "${groupName}"이(가) 생성되었습니다.`);

      // 새로운 HTML 페이지로 이동
      const newGroupPage = `group_${groupName}.html`;  // 그룹 이름을 파일명으로 사용
      window.location.href = newGroupPage; // 새로운 그룹 페이지로 이동
  });

  // 이미지 변경 시 동기화
  profileImageInput.addEventListener('change', (event) => {
      const file = event.target.files[0];
      if (file) {
          const reader = new FileReader();
          reader.onload = (e) => {
              outProfileImage.src = e.target.result;  // 그룹 이미지 업데이트
          };
          reader.readAsDataURL(file);
      }
  });

  // Enter 키로 그룹 생성 시 확인 대화상자 띄우기
  groupNameInput.addEventListener("keydown", (event) => {
      if (event.key === "Enter") {  // Enter 키를 눌렀을 때
          const groupName = groupNameInput.value.trim(); // 입력값에서 공백 제거
          if (!groupName) {
              alert("그룹 이름을 작성해주세요"); // 그룹 이름이 없으면 경고 메시지
              return;
          }

          // 그룹 생성 확인 대화상자
          const confirmation = confirm(`그룹 "${groupName}"을(를) 생성하시겠습니까?`);
          if (confirmation) {
              // 그룹 이름과 이미지 반영
              groupHeader.textContent = groupName;  // groupheader에 그룹 이름 추가
              profileImage.src = outProfileImage.src;  // 그룹 이미지 동기화
              alert(`그룹 "${groupName}"이(가) 생성되었습니다.`);

              // 새로운 HTML 페이지로 이동
              const newGroupPage = `group_${groupName}.html`;  // 그룹 이름을 파일명으로 사용
              window.location.href = newGroupPage; // 새로운 그룹 페이지로 이동
          } else {
              groupNameInput.value = ""; // 그룹 이름 입력 초기화
          }
      }
  });
});
// ---------------------------------------------------------------------------
//
//
//
document.addEventListener("DOMContentLoaded", () => {
  // 더미 데이터 (DB에서 가져오는 데이터 대신 사용)
  const dummyData = [
    { user_id: "user1", user_hpn: "01012345678" },
    { user_id: "user2", user_hpn: "01098765432" },
    { user_id: "user3", user_hpn: "01011112222" },
    { user_id: "user4", user_hpn: "01022223333" }
  ];

  // 내 아이디와 내 휴대폰 번호
  const myUserId = "user1";  // 실제로는 로그인된 사용자 아이디로 대체될 수 있음
  const myUserPhone = "01012345678";  // 본인의 휴대폰 번호 추가

  // 초대 메세지를 이미 보낸 유저를 추적하기 위한 변수
  const invitedUsers = new Set();

  // 내 아이디를 동적으로 추가하는 함수
  function displayMyId() {
    const myIdElement = document.getElementById('group-list__me');
    const listItem = document.createElement('li');
    listItem.classList.add('group-id');
    listItem.textContent = `${myUserId} (본인 아이디)`; // 내 아이디에 "본인 아이디" 표시
    myIdElement.appendChild(listItem);
  }

  // 초대 함수
  function sendGroupInvite(user) {
    if (invitedUsers.has(user.user_id)) {
      alert(`${user.user_id}에게 이미 초대가 전송되었습니다.`);
    } else {
      alert(`그룹 초대가 ${user.user_id}에게 전송되었습니다.`);
      addUserToGroup(user); // 그룹에 사용자를 추가
      invitedUsers.add(user.user_id); // 초대한 유저 추가
    }
  }

  // 그룹에 사용자를 추가하는 함수
  function addUserToGroup(user) {
    const groupList = document.getElementById('group-list__me');  // 그룹에 추가할 위치 (내 그룹)
    const listItem = document.createElement('li');
    listItem.classList.add('group-id');
    listItem.textContent = user.user_id;

    groupList.appendChild(listItem);
  }

  // 사용자 검색 함수 (내 아이디 제외)
  function searchUser(query) {
    // 내 아이디 제외하고 검색, 아이디 또는 휴대폰 번호가 정확히 일치하는 경우만 검색
    return dummyData.filter(user =>
      (user.user_id === query || user.user_hpn === query) && user.user_id !== myUserId
    );
  }

  // 검색창 이벤트 처리 (엔터키 입력 시)
  document.querySelector('.search-text-input').addEventListener('keypress', (event) => {
    // 엔터키 입력 시
    if (event.key === 'Enter') {
      const query = event.target.value.trim();
      if (query) {
        // 본인 아이디나 본인 휴대폰 번호가 검색되는지 확인
        if (query === myUserId || query === myUserPhone) {
          alert("해당 본인의 정보입니다."); // 본인 아이디 또는 휴대폰 검색 시 경고 표시
          return;  // 본인 아이디 또는 휴대폰 번호일 경우 추가적인 검색을 하지 않음
        }

        const results = searchUser(query); // 사용자 검색
        if (results.length > 0) {
          // 해당 유저가 존재하면 초대 메세지 보내기
          sendGroupInvite(results[0]); // 첫 번째 결과만 처리
        } else {
          alert('존재하지 않는 유저입니다.');
        }
      } else {
        alert('검색어를 입력해주세요.');
      }
    }
  });

  // 페이지 로드 시 내 아이디를 표시
  displayMyId();
});
// ---------------------------------------------------------------------------
//
//
// 그룹 생성 후 프로필 설정 부분
document.addEventListener("DOMContentLoaded", function() {
  // 프로필 이미지 변경 버튼과 파일 입력 요소
  const profileChangeButton = document.getElementById("out-profile-change-button");
  const profileImageInput = document.getElementById("out-profile-image-input");
  const profileImage = document.getElementById("out-profile-image");

  // 프로필 이미지 변경 버튼 클릭 시, 파일 입력 창 열기
  profileChangeButton.addEventListener("click", function() {
      profileImageInput.click();
  });

  // 파일이 선택되었을 때 프로필 이미지를 업데이트
  profileImageInput.addEventListener("change", function(event) {
      const file = event.target.files[0];

      if (file) {
          // 선택된 파일을 URL로 변환하여 이미지에 반영
          const reader = new FileReader();

          reader.onload = function(e) {
              // 파일이 로드되면, 이미지 src를 해당 파일로 변경
              profileImage.src = e.target.result;
          };

          reader.readAsDataURL(file); // 파일을 데이터 URL로 읽어들임
      }
  });
});
// ---------------------------------------------------------------------------
//
//
// 그룹 생성 버튼 활성화
document.addEventListener("DOMContentLoaded", () => {
  const groupNameInput = document.querySelector(".group-name-input"); // 그룹 이름 입력 필드
  const createGroupButton = document.querySelector("#make-group"); // 그룹 생성 버튼
  const groupListContainer = document.querySelector("#group-list__other-users"); // 추가된 그룹원 리스트 표시 영역

  if (!groupNameInput || !createGroupButton || !groupListContainer) {
      console.error("그룹 생성 관련 요소를 찾을 수 없습니다.");
      return;
  }

  // 그룹 생성 처리 함수
  const createGroup = async () => {
      const groupName = groupNameInput.value.trim(); // 그룹 이름 입력값
      const profileImage = document.querySelector("#profile-image").src; // 그룹 프로필 이미지

      if (!groupName) {
          alert("그룹 이름을 입력해주세요.");
          return;
      }

      // 그룹 데이터 생성
      const groupData = {
          groupName, // 그룹 이름
          groupProfile: profileImage || "../image/img.gif", // 기본 이미지 경로 설정
          members: ["user1"], // 기본 그룹 멤버 (본인 ID)
      };

      try {
          console.log("그룹 생성 요청 중...");

          // 비동기 POST 요청
          const response = await fetch('/api/create-group', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify({ body: groupData }), // 요청 데이터 구조 변경
          });

          if (response.ok) {
              const result = await response.json();
              if (result.success) {
                  console.log("그룹 생성 성공:", result);

                  // 새로운 창에 그룹 정보를 띄우기
                  const newWindow = window.open("", "_blank", "width=600,height=400");
                  if (newWindow) {
                      newWindow.document.write(`
                          <!DOCTYPE html>
                          <html lang="ko">
                          <head>
                              <meta charset="UTF-8">
                              <meta name="viewport" content="width=device-width, initial-scale=1.0">
                              <title>그룹 생성 완료</title>
                          </head>
                          <body>
                              <h1>${groupName} 그룹 생성 완료!</h1>
                              <p>그룹 ID: ${result.groupId}</p>
                              <p>그룹 프로필: <img src="${groupData.groupProfile}" alt="그룹 이미지" width="100" /></p>
                              <p>그룹원: ${groupData.members.join(", ")}</p>
                          </body>
                          </html>
                      `);
                      newWindow.document.close();
                  }
              } else {
                  console.error("그룹 생성 실패:", result.message);
                  alert("그룹 생성 실패: " + result.message);
              }
          } else {
              console.error("서버 응답 오류");
              alert("그룹 생성 요청 실패");
          }
      } catch (error) {
          console.error("그룹 생성 중 오류 발생:", error);
          alert("그룹 생성 중 오류가 발생했습니다.");
      }
  };

  // 그룹 생성 버튼 클릭 이벤트
  createGroupButton.addEventListener("click", createGroup);

  // 프로필 이미지 변경 처리
  const profileImageInput = document.querySelector("#profile-image-input");
  const profileChangeButton = document.querySelector("#profile-change-button");
  profileChangeButton.addEventListener("click", () => {
      profileImageInput.click();
  });
  profileImageInput.addEventListener("change", (event) => {
      const file = event.target.files[0];
      if (file) {
          const reader = new FileReader();
          reader.onload = (e) => {
              document.querySelector("#profile-image").src = e.target.result; // 이미지 미리보기
          };
          reader.readAsDataURL(file);
      }
  });
});
// ---------------------------------------------------------------------------
//
//
// 그룹 나가기 버튼 활성화
document.addEventListener("DOMContentLoaded", () => {
  const exitGroupButton = document.querySelector(".exit-group"); // 그룹 나가기 버튼
  const groupListContainer = document.querySelector(".grouplist-body__scroll"); // 그룹 리스트 표시 영역
  const userId = "user1"; // 현재 로그인한 사용자 ID (실제 구현에서는 서버에서 세션으로 받아와야 함)

  if (!exitGroupButton || !groupListContainer) {
      console.error("그룹 나가기 관련 요소를 찾을 수 없습니다.");
      return;
  }

  // 그룹 나가기 처리 함수
  const exitGroup = async () => {
      const groupName = prompt("그룹 이름을 입력해주세요:");
      if (!groupName) {
          alert("그룹 이름을 입력하지 않았습니다.");
          return;
      }

      // 그룹이 리스트에 있는지 확인
      const groupElement = document.querySelector(`[data-group-name="${groupName}"]`);
      if (!groupElement) {
          alert("없는 그룹 이름입니다.");
          return;
      }

      // 사용자 확인 창
      const confirmation = confirm(`정말 '${groupName}' 그룹에서 나가겠습니까?`);
      if (!confirmation) {
          return;
      }

      try {
          console.log(`'${groupName}' 그룹 나가기 요청 중...`);

          // 비동기 DELETE 요청
          const response = await fetch('/api/exit-group', {
              method: 'DELETE',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify({ userId, groupName }), // 그룹 나가기 요청 데이터
          });

          if (response.ok) {
              const result = await response.json();
              if (result.success) {
                  console.log(`그룹 '${groupName}' 나가기 성공:`, result);

                  // 그룹 리스트에서 제거
                  groupElement.remove();
                  alert(`그룹 '${groupName}'에서 나갔습니다.`);
              } else {
                  console.error("그룹 나가기 실패:", result.message);
                  alert("그룹 나가기 실패: " + result.message);
              }
          } else {
              console.error("서버 응답 오류");
              alert("그룹 나가기 요청 실패");
          }
      } catch (error) {
          console.error("그룹 나가기 중 오류 발생:", error);
          alert("그룹 나가기 중 오류가 발생했습니다.");
      }
  };

  // 그룹 나가기 버튼 클릭 이벤트
  exitGroupButton.addEventListener("click", exitGroup);
});