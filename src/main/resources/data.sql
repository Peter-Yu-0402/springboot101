/* h2 콘솔에 접속해 쿼리를 입력하여 데이터가 제대로 저장되었는지 확인해보자.
스프링 부트 서버를 실행할 때마다 SQL문으로 아래 데이터를 넣는다.
created_at, updated_at 추가 */
INSERT INTO article (title, content, author, created_at, updated_at) VALUES ('title 1', 'content 1', '글쓴이 1', NOW(), NOW())
INSERT INTO article (title, content, author, created_at, updated_at) VALUES ('title 2', 'content 2', '글쓴이 2', NOW(), NOW())
INSERT INTO article (title, content, author, created_at, updated_at) VALUES ('title 3', 'content 3', '글쓴이 3', NOW(), NOW())