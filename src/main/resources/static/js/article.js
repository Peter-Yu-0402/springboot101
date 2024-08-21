// 삭제 기능
const deleteButton = document.getElementById('delete-btn'); // 삭제 버튼 id로 특정한다.

if (deleteButton) {
// 클릭 이벤트 발생 → fetch() 메서드로 요청 보내기 →
// fetch() 잘 실행되면 연이어 then() 메서드 실행 →
// then() 메서드 실행 시점에 alert() 메서드로 팝업
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/articles'); // 유저 웹 브라우저 화면을 현재 주소로 옮기기
            });
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn'); // 수정 버튼 id로 특정한다.

if (modifyButton) {
    // 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('수정이 완료되었습니다.');
                location.replace(`/articles/${id}`);
            });
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        fetch('/api/articles', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('등록 완료되었습니다.');
                location.replace('/articles');
            });
    });
}