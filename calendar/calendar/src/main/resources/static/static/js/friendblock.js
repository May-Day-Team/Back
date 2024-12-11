document.addEventListener('DOMContentLoaded', function () {
    const friendsOnline = document.querySelector('.friends-online'); // 친구 목록 컨테이너
    const friendsGroupContainer = document.querySelector('.friends-group'); // 그룹 목록 컨테이너
    const blockListContainer = document.querySelector('.block-list'); // 차단 목록 컨테이너

    // 친구 · 그룹항목에 대한 "차단하기" 버튼 처리
    document.addEventListener('click', function (event) {
        const target = event.target;
        // "차단하기" 버튼 클릭 시 처리
        if (target.textContent === '차단하기' && target.closest('.iconbar')) {
            const friendContainer = target.closest('.friends-bar, .group-bar'); // 해당 친구 항목
            if (friendContainer) {
                // 차단 여부 확인
                const confirmBlock = confirm("정말로 차단하시겠습니까?");
                
                if (confirmBlock) {
                    // 원래 위치 정보 저장
                    const originalGroup = friendContainer.closest('.friends-online, .group-list');
                    friendContainer.dataset.originalGroup = originalGroup ? originalGroup.className : 'friends-online';

                    // 그룹 이름 저장
                    if (originalGroup && originalGroup.classList.contains('group-list')) {
                        const groupHeader = originalGroup.previousElementSibling; // 그룹 헤더
                        const groupName = groupHeader.querySelector('.friendsGawEI')?.textContent;
                        friendContainer.dataset.groupName = groupName || '';
                    }

                    // 차단 목록으로 이동
                    moveToBlockList(friendContainer);
                } else {
                    // 차단 취소
                    console.log("차단이 취소되었습니다.");
                }
            }
        }
    });

    // 친구 항목을 차단 목록으로 이동시키는 함수
    function moveToBlockList(friendContainer) {
        // 기존 위치에서 친구 항목 제거
        friendContainer.remove();

        // 차단 목록 UI에 추가
        const blockBar = document.createElement('div');
        blockBar.classList.add('block-bar');

        const blockGroup = document.createElement('div');
        blockGroup.classList.add('block-bar__group');

        const blockUser = document.createElement('div');
        blockUser.classList.add('block-bar__user');

        // 기존 데이터 복사
        const id = friendContainer.querySelector('.friends-id, .group-id')?.textContent || '';
        const phone = friendContainer.querySelector('.friends-number, .group-number')?.textContent || '';
        const status = friendContainer.querySelector('.friends-content, .group-content')?.textContent || '';

        const blockId = document.createElement('li');
        blockId.className = 'block-id';
        blockId.textContent = id;

        const blockNumber = document.createElement('li');
        blockNumber.className = 'block-number';
        blockNumber.textContent = phone;

        const blockContent = document.createElement('li');
        blockContent.className = 'block-content';
        blockContent.textContent = status;

        blockUser.appendChild(blockId);
        blockUser.appendChild(blockNumber);
        blockUser.appendChild(blockContent);

        blockGroup.appendChild(blockUser);

        // 차단 항목 옵션 아이콘
        const blockIcons = document.createElement('div');
        blockIcons.classList.add('block-material-icons');

        const moreIcon = document.createElement('div');
        moreIcon.classList.add('material-icons');
        moreIcon.textContent = 'more_vert';

        const iconBar = document.createElement('div');
        iconBar.classList.add('iconbar');
        iconBar.style.display = 'none'; // 기본적으로 숨김

        ['차단 해제', '삭제하기'].forEach(option => {
            const optionDiv = document.createElement('div');
            optionDiv.textContent = option;

            // 옵션에 따른 이벤트 처리
            optionDiv.addEventListener('click', function () {
                if (option === '차단 해제') {
                    moveToOriginalList(friendContainer, blockBar); // 차단 해제 시 원래 위치로 이동
                } else if (option === '삭제하기') {
                    const confirmDelete = confirm("정말로 삭제하시겠습니까?");
                    if (confirmDelete) {
                        blockBar.remove(); // 항목 삭제
                        console.log(`${id || phone} 삭제됨`);
                    }
                }
            });

            iconBar.appendChild(optionDiv);
        });

        moreIcon.appendChild(iconBar);
        blockIcons.appendChild(moreIcon);

        blockGroup.appendChild(blockIcons);
        blockBar.appendChild(blockGroup);

        // 차단 목록에 추가
        blockListContainer.appendChild(blockBar);

        // 차단된 사용자 메시지 출력
        console.log(`${id || phone} 차단 목록으로 이동`);

        // 아이콘 바 토글 이벤트
        moreIcon.addEventListener('click', function () {
            iconBar.style.display = iconBar.style.display === 'block' ? 'none' : 'block';
        });
    }

    /**
     * 차단 해제 시 원래 친구 목록이나 그룹 목록으로 이동하는 함수
     * @param {HTMLElement} friendContainer - 이동할 친구 항목
     * @param {HTMLElement} blockBar - 차단 목록에서의 항목
     */
    function moveToOriginalList(friendContainer, blockBar) {
        const originalGroup = friendContainer.dataset.originalGroup;
        const groupName = friendContainer.dataset.groupName;

        if (originalGroup === 'friends-online') {
            const onlineList = friendsOnline.querySelector('.online-list') || createOnlineList();
            onlineList.appendChild(friendContainer); // 친구 목록에 추가
        } else if (originalGroup === 'group-list') {
            // 그룹 이름을 바탕으로 해당 그룹을 찾기
            const groupHeader = Array.from(friendsGroupContainer.querySelectorAll('.friends-group__bar'))
                .find(header => header.querySelector('.friendsGawEI')?.textContent === groupName);

            if (groupHeader) {
                const groupList = groupHeader.nextElementSibling;
                if (groupList && groupList.classList.contains('group-list')) {
                    groupList.appendChild(friendContainer); // 그룹 목록에 추가
                }
            }
        }

        blockBar.remove(); // 차단 목록에서 제거
        console.log(`${friendContainer.querySelector('.friends-id, .group-id')?.textContent || '친구'}가 차단 해제되어 원래 목록으로 이동`);
    }

    /**
     * 온라인 리스트가 없는 경우 생성하는 함수
     * @returns {HTMLElement} 생성된 온라인 리스트
     */
    function createOnlineList() {
        const newOnlineList = document.createElement('div');
        newOnlineList.classList.add('online-list');
        friendsOnline.appendChild(newOnlineList);
        return newOnlineList;
    }
});
