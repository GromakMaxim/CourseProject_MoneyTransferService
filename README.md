# Курсовой проект "Сервис перевода денег"

front: https://serp-ya.github.io/card-transfer/

Address: http://localhost:5500/transfer

Start:
1) docker-compose build
2) docker-compose up

Legal request example:

Method:post;

Body:{"cardFromNumber":"5610591081018250","cardToNumber":"5019717010103742","cardFromCVV":"123","cardFromValidTill":"11/25","amount":{"currency":"RUR","value":5000}};
