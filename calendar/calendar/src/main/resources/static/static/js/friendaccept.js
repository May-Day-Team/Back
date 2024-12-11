// 친구 요청 부분
$(document).ready(function() {
    // 더미 데이터 (친구 요청)
    const friendRequests = [
        { id: 'user1', name: 'John Doe', phone: '010-1234-5678', status: "Hello, let's be friends!" },
        { id: 'user2', name: 'Jane Smith', phone: '010-2345-6789', status: "Hi! I'd like to add you as a friend." },
        { id: 'user3', name: 'Tom Lee', phone: '010-3456-7890', status: "Good to meet you!" },
        { id: 'user4', name: 'Sara Kim', phone: '010-4567-8901', status: "Let's connect!" },
    ];

    let currentIndex = 0; // 현재 친구 요청의 인덱스

    // 친구 요청이 없다면 friends-waiting을 숨김
    if (friendRequests.length === 0) {
        $('.friends-waiting').hide();
    }

    // 친구 요청 리스트 렌더링 함수
    function renderFriendRequests() {
        const listContainer = $('#friends-waiting-list');
        listContainer.empty(); // 기존 리스트를 비움

        // 친구 요청이 없다면 friends-waiting을 숨김
        if (friendRequests.length === 0) {
            $('.friends-waiting').hide(); // 친구 요청이 없으면 영역 숨김
            return;
        } else {
            $('.friends-waiting').css('display', 'flex'); // 친구 요청이 있을 경우 display를 flex로 설정
        }

        // 현재 인덱스에 해당하는 친구 요청 가져오기
        const currentRequest = friendRequests[currentIndex];

        // 친구 요청 항목 생성
        const requestItem = `
            <div class="friends-waiting__bar" id="request-${currentRequest.id}">
                <div class="friends-waiting-id"> ${currentRequest.id}</div>
                <div class="friends-waiting__btn">
                    <div class="friends-waiting-accept" onclick="acceptRequest('${currentRequest.id}')">수락</div>
                    <div class="friends-waiting-refuse" onclick="refuseRequest('${currentRequest.id}')">거절</div>
                </div>
            </div>
        `;
        listContainer.append(requestItem); // 리스트에 항목 추가
    }
    

    // 친구 요청 수락 처리 함수
    window.acceptRequest = function(userId) {
        const friendRequest = friendRequests.filter(it => it.id === userId)[0];

        if (friendRequest) {
            // 친구 목록에 추가할 친구 항목 생성
            const newFriendContainer = document.createElement('div');
            newFriendContainer.classList.add('friends-bar');

            const newFriendGroup = document.createElement('div');
            newFriendGroup.classList.add('friends-bar__group');

            const newFriendUser = document.createElement('div');
            newFriendUser.classList.add('friends-bar__user');

            // 친구 정보 항목 (ID, 전화번호, 상태 메시지)
            const newFriendId = document.createElement('li');
            newFriendId.classList.add('friends-id'); // 클래스 'friends-id'
            const newFriendNumber = document.createElement('li');
            newFriendNumber.classList.add('friends-number'); // 클래스 'friends-number'
            const newFriendContent = document.createElement('li');
            newFriendContent.classList.add('friends-content'); // 클래스 'friends-content'

            // 친구의 ID와 전화번호 및 상태 메시지 삽입
            newFriendId.textContent = friendRequest.id; // 친구 ID
            newFriendNumber.textContent = friendRequest.phone; // 친구 전화번호
            newFriendContent.textContent = friendRequest.status; // 친구의 상태 메시지

            newFriendUser.appendChild(newFriendId);
            newFriendUser.appendChild(newFriendNumber);
            newFriendUser.appendChild(newFriendContent);

            // friends-material-icons 추가 (friends-bar__group 안에 추가)
            const friendsMaterialIcons = document.createElement('div');
            friendsMaterialIcons.classList.add('friends-material-icons');

            // friends-material-icons 안에 material-icons 추가
            const moreIcon = document.createElement('div');
            moreIcon.classList.add('material-icons');
            moreIcon.textContent = 'more_vert'; // 친구 아이콘 (more_vert 사용)
            moreIcon.setAttribute('data-type', 'friend'); // data-type="friend" 속성 추가

            // 아이콘 바
            const iconBar = document.createElement('div');
            iconBar.classList.add('iconbar');

            const iconGroupAdd = document.createElement('div');
            iconGroupAdd.textContent = '그룹 추가하기';
            const iconBlock = document.createElement('div');
            iconBlock.textContent = '차단하기';
            // iconBlock.addEventListener('click', function() {
            //     const confirmBlock= confirm("정말로 이 친구를 차단하시겠습니까?");
            //     if (confirmBlock) {
            //         newFriendContainer.block();
            //         console.log("친구가 차단되었습니다.");
            //     }
            // });

            const iconDelete = document.createElement('div');
            iconDelete.textContent = '삭제하기';
            // 삭제 버튼 클릭 시 확인창
            iconDelete.addEventListener('click', function() {
                const confirmDelete = confirm("정말로 이 친구를 삭제하시겠습니까?");
                if (confirmDelete) {
                    newFriendContainer.remove(); // 친구 항목 삭제
                    console.log("친구가 삭제되었습니다.");
                }
            });
            

            iconBar.appendChild(iconGroupAdd);
            iconBar.appendChild(iconBlock);
            iconBar.appendChild(iconDelete);

            friendsMaterialIcons.appendChild(moreIcon);
            friendsMaterialIcons.appendChild(iconBar);

            newFriendGroup.appendChild(newFriendUser);
            newFriendGroup.appendChild(friendsMaterialIcons);

            newFriendContainer.appendChild(newFriendGroup);

            // #friends-online에 친구 추가
            const onlineList = $('.online-list');
            if (onlineList.length) {
                onlineList.append(newFriendContainer); // 기존 리스트에 추가
            } else {
                const newOnlineList = document.createElement('div');
                newOnlineList.classList.add('online-list');
                newOnlineList.appendChild(newFriendContainer);
                $('#friends-online').append(newOnlineList);
            }

            console.log('New friend added:', newFriendContainer); // 디버깅용 출력

            // 친구 요청 항목 삭제 후 다음 요청으로 이동
            removeFriendRequest(userId, true); // 여기서 true를 보내어 `nextRequest()`를 자동으로 호출
        }
    }

    // 거절 처리 함수
    window.refuseRequest = function(userId) {
        alert(userId + '의 친구 요청을 거절했습니다.');
        removeFriendRequest(userId, false); // 거절 후 자동으로 다음 요청으로 이동
    }

    // 친구 요청 삭제 처리
    function removeFriendRequest(userId, isAccept) {
        // 친구 요청 항목 삭제
        $(`#request-${userId}`).remove();
        // 배열에서 친구 요청 삭제
        const index = friendRequests.findIndex(request => request.id === userId);
        if (index != -1) {
            friendRequests.splice(index, 1);
        }

        // 삭제 후 currentIndex가 배열 범위를 벗어나지 않도록 조정
        if (currentIndex >= friendRequests.length) {
            currentIndex = friendRequests.length - 1; // 배열의 마지막 요소로 이동
        }

        // 친구 요청이 남아 있다면
        if (friendRequests.length > 0) {
            renderFriendRequests(); // 친구 요청이 남아있으면 다시 렌더링
        } else {
            // 친구 요청이 없으면 `friends-waiting`을 숨김
            $('.friends-waiting').hide();
        }
    }


    // 이전 버튼 클릭 이벤트
    $('#prev').click(function() {
        prevRequest();
    });

    // 다음 버튼 클릭 이벤트
    $('#next').click(function() {
        nextRequest();
    });

    // 이전 친구 요청으로 이동 (순환)
    function prevRequest() {
        if (friendRequests.length > 0) {
            if (currentIndex > 0) {
                currentIndex--;  // currentIndex가 0보다 크면 한 칸 뒤로 이동
            } else {
                currentIndex = friendRequests.length - 1; // 첫 번째 친구 요청에서 이전으로 가면 마지막으로 순환
            }
            renderFriendRequests(); // 이전 요청 렌더링
        }
    }

    // 다음 친구 요청으로 이동 (순환)
    function nextRequest() {
        if (friendRequests.length > 0) {
            if (currentIndex < friendRequests.length - 1) {
                currentIndex++;  // currentIndex가 마지막보다 작으면 한 칸 앞으로 이동
            } else {
                currentIndex = 0; // 마지막 친구 요청에서 다음으로 가면 첫 번째로 순환
            }
            renderFriendRequests(); // 다음 요청 렌더링
        }
    }
    renderFriendRequests();
});

// -------------------------------------------------------------------









