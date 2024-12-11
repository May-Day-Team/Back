document.addEventListener('DOMContentLoaded', function() {
    const plus = document.querySelector('.friends-plus .material-icons');
    const search = document.querySelector('.friends-search .material-icons');
    const friendsPlusInputContainer = document.querySelector('.friends-plus-input-container');
    const friendsPlusInput = document.querySelector('.friends-plus__text');
    const friendsSearchInputContainer = document.querySelector('.friends-search-input-container');
    const friendsSearchInput = document.querySelector('.friends-search__text');
    const friendsOnline = document.querySelector('.friends-online');
    const expansionIcons = document.querySelectorAll('#expandsion');
    
    const friendsGroupContainer = document.querySelector('.friends-group');

    const groupAddButton = document.querySelector('.iconbar div:nth-child(4)'); // '그룹 추가하기' 버튼
    const friendsWindowBody = document.querySelector('.friends-window__body');

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

    // 'close' 아이콘이 포함된 .window-close 클릭 시 모달 닫기
    $(document).on('click', '#friends-modal-overlay .window-close .material-icons', function() {
        $('#friends-modal-overlay').fadeOut();
    });

    // 친구 추가 입력란 토글
    function togglePlusInput() {
        if (friendsPlusInputContainer.style.height === '0px' || friendsPlusInputContainer.style.height === '') {
            friendsPlusInputContainer.style.height = 'auto';
            friendsPlusInput.style.display = 'block';
            friendsPlusInput.focus();

            friendsSearchInputContainer.style.height = '0';
            friendsSearchInput.style.display = 'none';
            friendsSearchInput.value = '';
        } else {
            friendsPlusInputContainer.style.height = '0';
            friendsPlusInput.style.display = 'none';
            friendsPlusInput.value = ''; // 입력 필드를 숨길 때 입력된 값을 초기화
        }
    }

    // 검색 입력란 토글
    function toggleSearchInput() {
        if (friendsSearchInputContainer.style.height === '0px' || friendsSearchInputContainer.style.height === '') {
            friendsSearchInputContainer.style.height = 'auto';
            friendsSearchInput.style.display = 'block';
            friendsSearchInput.focus();

            friendsPlusInputContainer.style.height = '0';
            friendsPlusInput.style.display = 'none';
            friendsPlusInput.value = '';
        } else {
            friendsSearchInputContainer.style.height = '0';
            friendsSearchInput.style.display = 'none';
            friendsSearchInput.value = ''; // 입력 필드를 숨길 때 입력된 값을 초기화
        }
    }

    plus.addEventListener('click', togglePlusInput);
    search.addEventListener('click', toggleSearchInput);

    friendsPlusInput.style.display = 'none';
    friendsSearchInput.style.display = 'none';
    // -------------------------------------------------------------------




   // 친구 목록 펼치기/접기
    expansionIcons.forEach(icon => {
        icon.addEventListener('click', function() {
            const parent = icon.closest('.friends-online, .friends-group, .head-group, .head-block');
            const lists = parent.querySelectorAll('.online-list, .friends-group, .block-list');
            
            lists.forEach(list => {
                const currentDisplay = window.getComputedStyle(list).display;

                if (currentDisplay === 'none') {
                    list.style.display = 'block'; // 펼쳤을 때
                    icon.textContent = 'keyboard_arrow_down'; // 아이콘 변경
                } else {
                    list.style.display = 'none'; // 접었을 때
                    icon.textContent = 'keyboard_arrow_left'; // 아이콘 변경
                }
            });

            // .friends-group이 펼쳐질 때만 .group-list도 펼쳐지게 하기
            if (parent.classList.contains('friends-group')) {
                const groupList = parent.querySelector('.group-list');
                const groupIcon = parent.querySelector('#expandsion'); // .friends-group 내의 아이콘을 명시적으로 선택
                
                if (groupList) {
                    const currentGroupListDisplay = window.getComputedStyle(groupList).display;
                    if (currentGroupListDisplay === 'none') {
                        groupList.style.display = 'block'; // .group-list 펼치기
                    } else {
                        groupList.style.display = 'none'; // .group-list 접기
                    }
                }
                
                // .friends-group 아이콘 변경
                if (groupIcon) {
                    const currentDisplay = window.getComputedStyle(groupList).display;
                    groupIcon.textContent = (currentDisplay === 'none') ? 'keyboard_arrow_left' : 'keyboard_arrow_down';
                }
            }
        });
    });
    // -------------------------------------------------------------------




    // 엔터 키를 눌렀을 때 친구 추가
    friendsPlusInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            const inputValue = friendsPlusInput.value.trim();
    
            // 휴대폰 번호 형식 검사
            const phoneRegex = /^010-\d{4}-\d{4}$/; // 휴대폰 번호 형식: 010-0000-0000
            const isValidPhone = phoneRegex.test(inputValue);
    
            // 유효성 검사
            if (!isValidPhone && inputValue.length === 0) {
                alert('입력값이 비어있습니다. 아이디를 입력하거나 올바른 휴대폰 번호(010-0000-0000)를 입력해주세요.');
                return;
            }
    
            // 친구 데이터 객체 생성
            const newFriendData = {
                id: '',
                name: '', // 이름은 나중에 추가 가능
                phone: '',
                status: '상태 메시지', // 기본 상태 메시지
            };
    
            if (isValidPhone) {
                newFriendData.phone = inputValue; // 휴대폰 번호 저장
            } else {
                newFriendData.id = inputValue; // 아이디 저장
            }
    
            // 쿠키에 입력 받은 유저 데이터 저장
            document.cookie = `user=${JSON.stringify(newFriendData)}`;
            console.log("Added user to cookie:", newFriendData);
    
            // 친구 항목 DOM 생성
            const newFriendContainer = document.createElement('div');
            newFriendContainer.classList.add('friends-bar');
    
            const newFriendGroup = document.createElement('div');
            newFriendGroup.classList.add('friends-bar__group');
    
            const newFriendUser = document.createElement('div');
            newFriendUser.classList.add('friends-bar__user');
    
            const newFriendId = document.createElement('li');
            newFriendId.className = 'friends-id';
            const newFriendNumber = document.createElement('li');
            newFriendNumber.className = 'friends-number';
            const newFriendContent = document.createElement('li');
            newFriendContent.className = 'friends-content';
    
            newFriendId.textContent = newFriendData.id;
            newFriendNumber.textContent = newFriendData.phone;
            newFriendContent.textContent = newFriendData.status;
    
            newFriendUser.appendChild(newFriendId);
            newFriendUser.appendChild(newFriendNumber);
            newFriendUser.appendChild(newFriendContent);
    
            // friends-material-icons 추가
            const friendsMaterialIcons = document.createElement('div');
            friendsMaterialIcons.classList.add('friends-material-icons');
    
            // 친구 관리 아이콘
            const moreIcon = document.createElement('div');
            moreIcon.classList.add('material-icons');
            moreIcon.textContent = 'more_vert'; // 더보기 아이콘
            moreIcon.setAttribute('data-type', 'friend');
    
            // 아이콘 바
            const iconBar = document.createElement('div');
            iconBar.classList.add('iconbar');
    

            // 그룹 추가하기 / 차단 / 삭제 버튼 동작
            ['그룹 추가하기', '차단하기', '삭제하기'].forEach(option => {
                const optionDiv = document.createElement('div');
                optionDiv.textContent = option;
    
                optionDiv.addEventListener('click', () => {
                    if (option === '그룹 추가하기') {
                        const confirmGroupAdd = confirm("해당 그룹에 초대하시겠습니까?");
                        if (confirmGroupAdd) {
                            console.log("그룹에 추가 완료");
                        }
                    } else if (option === '삭제하기') {
                        const confirmDelete = confirm("정말로 이 친구를 삭제하시겠습니까?");
                        if (confirmDelete) {
                            newFriendContainer.remove();
                            console.log("친구가 삭제되었습니다.");
                        }
                    }
                });
    
                iconBar.appendChild(optionDiv);
            });
    

            friendsMaterialIcons.appendChild(moreIcon);
            friendsMaterialIcons.appendChild(iconBar);
    
            // 친구 항목 추가
            newFriendGroup.appendChild(newFriendUser);
            newFriendGroup.appendChild(friendsMaterialIcons);
            newFriendContainer.appendChild(newFriendGroup);
    
            // friends-online에 친구 추가
            const onlineList = friendsOnline.querySelector('.online-list');
            if (onlineList) {
                onlineList.appendChild(newFriendContainer);
            } else {
                const newOnlineList = document.createElement('div');
                newOnlineList.classList.add('online-list');
                newOnlineList.appendChild(newFriendContainer);
                friendsOnline.appendChild(newOnlineList);
            }
    
            console.log('New friend added:', newFriendData); // 디버깅용 출력
    
            // 입력 필드 초기화
            friendsPlusInput.value = '';
        }
    });
    
    // -------------------------------------------------------------------




    //친구 · 그룹 찾기 부분
    friendsSearchInput.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            const inputValue = friendsSearchInput.value.trim();
    
            if (inputValue) {
                let found = false;
    
                // 친구 목록에서 검색
                const friendsList = friendsOnline.querySelectorAll('.friends-bar');
                friendsList.forEach(friend => {
                    const friendId = friend.querySelector('.friends-id');
                    const friendNumber = friend.querySelector('.friends-number');
    
                    if (
                        (friendId && friendId.textContent.trim().includes(inputValue)) ||
                        (friendNumber && friendNumber.textContent.trim().includes(inputValue))
                    ) {
                        found = true;
    
                        // 친구 목록이 접혀 있다면 열기
                        const friendsListParent = friend.closest('.friends-online');
                        const expandIcon = friendsListParent.querySelector('.material-icons');
                        const onlineList = friendsListParent.querySelector('.online-list');
    
                        if (expandIcon && onlineList && window.getComputedStyle(onlineList).display === 'none') {
                            expandIcon.textContent = 'keyboard_arrow_down'; // 아이콘 변경
                            onlineList.style.display = 'block'; // 친구 목록 펼치기
                        }
    
                        // 친구 항목 강조 및 스크롤
                        setTimeout(() => {
                            friend.scrollIntoView({ behavior: 'smooth', block: 'center' });
                            friend.style.backgroundColor = '#f0f0f0'; // 강조 표시
                            setTimeout(() => {
                                friend.style.backgroundColor = ''; // 강조 해제
                            }, 2000);
                        }, 0);
                    }
                });
    
                
    
                // 결과가 없을 때 경고 메시지
                if (!found) {
                    alert('검색 결과가 없습니다.');
                }
            }
        }
    });
    // -------------------------------------------------------------------



    
    // 그룹 데이터 처리 및 UI 생성
    const groupData = [
        {
            id: 'school',
            name: '학교',
            count: 0,
            members: [
                { id: 'user1', name: 'John Doe', phone: '010-1234-5678', status: 'Hello!' },
                { id: 'user2', name: 'Jane Doe', phone: '010-2345-6789', status: 'Busy' },
            ],
        },
        {
            id: 'club',
            name: '동아리',
            count: 0,
            members: [
                { id: 'club1', name: 'Sara Kim', phone: '010-1111-2222', status: 'Ready' },
                { id: 'club2', name: 'Mike Park', phone: '010-2222-3333', status: 'Studying' },
            ],
        },
    ];
    

    // 그룹 데이터를 기반으로 UI 생성
    groupData.forEach(group => {
        // 그룹 헤더
        const groupHeader = document.createElement('div');
        groupHeader.classList.add('friends-group__bar');
        groupHeader.id = `group-header-${group.id}`; // 그룹별 고유 ID 부여
        

        // const groupCount = document.createElement('div');
        // groupCount.classList.add('friends-group__count');
        // groupCount.id = `group-count-${group.id}`; // 멤버 수 ID
        // groupCount.textContent = `${group.members.length}`;

        const groupName = document.createElement('li');
        groupName.classList.add('friendsGawEI');
        groupName.textContent = group.name;

        const expandIcon = document.createElement('div');
        expandIcon.classList.add('material-icons');
        expandIcon.id = `expand-icon-${group.id}`;
        expandIcon.textContent = 'keyboard_arrow_left';

        // groupHeader.appendChild(groupCount);
        groupHeader.appendChild(groupName);
        groupHeader.appendChild(expandIcon);

        // 멤버 리스트
        const groupList = document.createElement('div');
        groupList.classList.add('group-list');
        groupList.id = `group-list-${group.id}`; // 그룹별 고유 ID
        groupList.style.display = 'none'; // 기본적으로 숨김

        group.members.forEach(member => {
            const memberContainer = document.createElement('div');
            memberContainer.classList.add('group-bar');
            memberContainer.dataset.groupId = group.id; // 멤버에 그룹 ID 저장

            const memberGroup = document.createElement('div');
            memberGroup.classList.add('group-bar__group');

            const memberUser = document.createElement('div');
            memberUser.classList.add('group-bar__user');

            const memberId = document.createElement('li');
            memberId.className = 'group-id';
            memberId.textContent = member.id;

            const memberPhone = document.createElement('li');
            memberPhone.className = 'group-number';
            memberPhone.textContent = member.phone;

            const memberStatus = document.createElement('li');
            memberStatus.className = 'group-content';
            memberStatus.textContent = member.status;

            memberUser.appendChild(memberId);
            memberUser.appendChild(memberPhone);
            memberUser.appendChild(memberStatus);

            memberGroup.appendChild(memberUser);

            // 멤버 옵션 아이콘
            const memberIcons = document.createElement('div');
            memberIcons.classList.add('group-material-icons');

            const moreIcon = document.createElement('div');
            moreIcon.classList.add('material-icons');
            moreIcon.textContent = 'more_vert';

            const iconBar = document.createElement('div');
            iconBar.classList.add('iconbar');
            iconBar.style.display = 'none'; // 기본적으로 숨김

            // 차단/삭제 옵션
            ['차단하기', '삭제하기'].forEach(option => {
                const optionDiv = document.createElement('div');
                optionDiv.textContent = option;

                optionDiv.addEventListener('click', () => {
                    // if (option === '차단하기') {
                    //     const confirmBlock = confirm("정말로 이 멤버를 차단하시겠습니까?");
                    //     if (confirmBlock) {
                    //         // moveToBlockList(member, group, memberContainer, groupCount);
                    //         console.log("멤버 차단 완료");
                    //         moveToBlockList(memberContainer);  // 친구 목록에서 차단 목록으로 이동
                    //     } else {
                    //         // 취소 시 차단하지 않음
                    //         console.log("차단 취소");
                    //     }
                    // } 
                    if (option === '삭제하기') {
                        const confirmDelete = confirm("정말로 이 멤버를 삭제하시겠습니까?");
                        if (confirmDelete) {
                            deleteMember(member, group, memberContainer);
                        }
                    }
                });

                iconBar.appendChild(optionDiv);
            });

            memberIcons.appendChild(moreIcon);
            memberIcons.appendChild(iconBar);

            memberGroup.appendChild(memberIcons);
            memberContainer.appendChild(memberGroup);
            groupList.appendChild(memberContainer);

            // moreIcon 클릭 시 아이콘 바 토글
            moreIcon.addEventListener('click', function () {
                iconBar.style.display = iconBar.style.display === 'block' ? 'none' : 'block';
            });
        });

        friendsGroupContainer.appendChild(groupHeader);
        friendsGroupContainer.appendChild(groupList);

        // 그룹 펼치기/접기 이벤트
        expandIcon.addEventListener('click', function () {
            const isVisible = groupList.style.display === 'block';
            groupList.style.display = isVisible ? 'none' : 'block';
            expandIcon.textContent = isVisible ? 'keyboard_arrow_left' : 'keyboard_arrow_down';
        });
    });


    // -------------------------------------------------------------------

    



    // 친구 항목에 대한 아이콘 바 표시/숨기기
    document.addEventListener('click', function(event) {
        const icon = event.target;

        // 친구 아이콘 클릭 시
        if (icon.classList.contains('material-icons') && icon.textContent === 'more_vert') {
            // 클릭된 아이콘의 부모 요소인 .friends-material-icons 내에서 .iconbar 찾기
            let iconBar = icon.closest('.friends-material-icons').querySelector('.iconbar');

            // 이미 열린 iconbar가 있는지 확인하고, 열려있으면 닫음
            const openIconbars = document.querySelectorAll('.iconbar');
            openIconbars.forEach(function(openIconbar) {
                // 현재 클릭한 iconbar 외에는 모두 닫기
                if (openIconbar !== iconBar) {
                    openIconbar.style.display = 'none';
                }
            });

            // 클릭한 아이콘의 iconbar를 토글 (열기/닫기)
            if (iconBar.style.display === 'block') {
                iconBar.style.display = 'none'; // 열려있으면 닫음
            } else {
                iconBar.style.display = 'block'; // 닫혀있으면 열음
            }
        }

        // iconbar 외의 영역을 클릭하면 iconbar 닫기
        if (!icon.closest('.material-icons')) {
            const openIconbars = document.querySelectorAll('.iconbar');
            openIconbars.forEach(iconbar => {
                if (!iconbar.contains(event.target)) {
                    iconbar.style.display = 'none'; // 다른 곳을 클릭하면 iconbar 닫기
                }
            });
        }
    });
    // -------------------------------------------------------------------




});
