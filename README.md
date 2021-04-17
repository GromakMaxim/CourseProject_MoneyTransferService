# Курсовой проект "Сервис перевода денег"

front: https://serp-ya.github.io/card-transfer/

Address: http://localhost:5500/transfer

Start:
1) docker-compose build
2) docker-compose up

Legal request example:

Method:post;

Body:{"cardFromNumber":"1111111111111111","cardToNumber":"2222222222222222","cardFromCVV":"123","cardFromValidTill":"11/24","amount":{"currency":"RUR","value":142524500}};
