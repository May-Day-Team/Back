document.addEventListener('DOMContentLoaded', function() {
    const plus = document.querySelector('.friends-plus .material-icons');
    const search = document.querySelector('.friends-search .material-icons');
    const friendsPlusInputContainer = document.querySelector('.friends-plus-input-container');
    const friendsPlusInput = document.querySelector('.friends-plus__text');
    const friendsSearchInputContainer = document.querySelector('.friends-search-input-container');
    const friendsSearchInput = document.querySelector('.friends-search__text');
    const friendsOnline = document.querySelector('.friends-online');
    const expansionIcons = document.querySelectorAll('#expandsion');

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

    // 엔터 키를 눌렀을 때 입력 값을 임시로 저장하고 목록에 추가하는 함수
    friendsPlusInput.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            const inputValue = friendsPlusInput.value.trim();
            if (inputValue) {
                // friends-bar__group 형식으로 새로운 친구 항목 생성
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

                // 입력값이 이메일일 경우 id로 처리, 전화번호일 경우 number로 처리
                if (inputValue.includes('@')) {
                    newFriendId.textContent = inputValue; // 이메일을 아이디로 저장
                    newFriendNumber.textContent = ''; // 전화번호 없으므로 비워두기
                } else {
                    newFriendId.textContent = ''; // 전화번호에는 아이디 없음
                    newFriendNumber.textContent = inputValue; // 전화번호를 번호로 저장
                }
                newFriendContent.textContent = '상태 메시지'; // 임시 상태 메시지

                // friends-bar__user에 id, number, content를 추가
                newFriendUser.appendChild(newFriendId);
                newFriendUser.appendChild(newFriendNumber);
                newFriendUser.appendChild(newFriendContent);

                // friends-bar__group에 user 추가
                newFriendGroup.appendChild(newFriendUser);

                // friends-bar에 친구 그룹을 추가
                newFriendContainer.appendChild(newFriendGroup);

                // 친구 아이콘 추가
                const moreIcon = document.createElement('div');
                moreIcon.classList.add('material-icons');
                moreIcon.textContent = 'more_vert'; // 친구 아이콘 (more_vert 아이콘 사용)

                // friends-bar__group에 아이콘 추가
                newFriendGroup.appendChild(moreIcon);

                // friends-bar에 친구 그룹을 추가
                newFriendContainer.appendChild(newFriendGroup);

                // friends-online 내부의 online-list에 추가
                const onlineList = friendsOnline.querySelector('.online-list');
                if (onlineList) {
                    onlineList.appendChild(newFriendContainer); // 온라인 리스트에 추가
                } else {
                    // 만약 .online-list가 없다면, 새로 생성해서 추가
                    const newOnlineList = document.createElement('div');
                    newOnlineList.classList.add('online-list');
                    newOnlineList.appendChild(newFriendContainer);
                    friendsOnline.appendChild(newOnlineList);
                }

                console.log('New friend added:', newFriendContainer); // 디버깅을 위해 콘솔에 출력

                // 입력 필드 초기화
                friendsPlusInput.value = '';
            }
        }
    });

    // 친구 목록 펼치기/접기 기능
    expansionIcons.forEach(icon => {
        icon.addEventListener('click', function() {
            const parent = icon.closest('.friends-online, .friends-group');
            const list = parent.querySelector('.online-list, .group-list');
            if (list.style.display === 'none' || list.style.display === '') {
                list.style.display = 'block';
                icon.textContent = 'keyboard_arrow_down';
            } else {
                list.style.display = 'none';
                icon.textContent = 'keyboard_arrow_left';
            }
        });
    });


    // 친구 항목을 저장할 변수 (친구 항목이 생성된 후 이를 그룹에 추가하기 위해 사용)
    let selectedFriend = null;
    
    // `more_vert` 아이콘 클릭 시 아이콘 바 표시
    document.addEventListener('click', function(event) {
        const icon = event.target;
        if (icon.classList.contains('material-icons') && icon.textContent === 'more_vert') {
            let iconBar = icon.closest('.friends-bar__group').querySelector('.iconbar');
            if (!iconBar) {
                iconBar = document.createElement('div');
                iconBar.classList.add('iconbar');

                // Iconbar 옵션들 추가
                const options = ['그룹 추가하기', '차단하기', '삭제하기'];
                options.forEach(option => {
                    const optionDiv = document.createElement('div');
                    optionDiv.textContent = option;
                    iconBar.appendChild(optionDiv);

                    // 그룹 추가하기 클릭 시 처리
                    if (option === '그룹 추가하기') {
                        optionDiv.addEventListener('click', function() {
                            // 그룹이 없으면 새로 생성
                            const friendsWindowBody = document.querySelector('.friends-window__body');
                            let newGroup = friendsWindowBody.querySelector('.friends-group');
                            if (!newGroup) {
                                newGroup = document.createElement('div');
                                newGroup.classList.add('friends-group');
                                
                                const groupBar = document.createElement('div');
                                groupBar.classList.add('friends-group__bar');
                                
                                const groupCount = document.createElement('div');
                                groupCount.classList.add('friends-group__count');
                                
                                const groupName = document.createElement('li');
                                groupName.classList.add('friendsGawEI');
                                groupName.textContent = '그룹 목록';
                                
                                const expansionIcon = document.createElement('div');
                                expansionIcon.id = 'expandsion';
                                expansionIcon.classList.add('material-icons');
                                expansionIcon.textContent = 'keyboard_arrow_left';
                                
                                groupBar.appendChild(groupCount);
                                groupBar.appendChild(groupName);
                                groupBar.appendChild(expansionIcon);
                                
                                const groupList = document.createElement('div');
                                groupList.classList.add('group-list');
                                
                                newGroup.appendChild(groupBar);
                                newGroup.appendChild(groupList);
                                
                                friendsWindowBody.appendChild(newGroup);

                                // 아이콘 클릭 시 그룹 펼치기/접기 기능 추가
                                expansionIcon.addEventListener('click', function() {
                                    const groupList = newGroup.querySelector('.group-list');
                                    if (groupList.style.display === 'none' || groupList.style.display === '') {
                                        groupList.style.display = 'block';
                                        expansionIcon.textContent = 'keyboard_arrow_down';
                                    } else {
                                        groupList.style.display = 'none';
                                        expansionIcon.textContent = 'keyboard_arrow_left';
                                    }
                                });
                            }

                            // 친구 항목을 생성하여 그룹에 추가
                            const newFriendContainer = icon.closest('.friends-bar');

                            // 친구 항목을 생성
                            const newFriendGroup = newFriendContainer.cloneNode(true); // 친구 항목 복제

                            // 해당 친구 항목을 그룹의 group-list에 추가
                            const groupList = newGroup.querySelector('.group-list');
                            groupList.appendChild(newFriendGroup);

                            console.log('New friend added to group:', newFriendGroup); // 디버깅을 위한 출력
                        });
                    }
                });

                // 아이콘 바를 친구 항목에 추가
                icon.closest('.friends-bar__group').appendChild(iconBar);
            }

            // 아이콘 바를 토글
            iconBar.style.display = iconBar.style.display === 'block' ? 'none' : 'block';
        } else {
            // 다른 곳을 클릭하면 iconbar를 닫기
            const openIconbars = document.querySelectorAll('.iconbar');
            openIconbars.forEach(iconbar => {
                if (!iconbar.contains(event.target)) {
                    iconbar.style.display = 'none';
                }
            });
        }
    });
});
