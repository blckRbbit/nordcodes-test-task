Приложение генерирует короткие ссылки по полным, по типу goo.gle.

1. Клонируйте репозиторий;
2. установите docker;
3. перейдите в консоли в папку docker (она в корне проекта);
4. Выполните в консоли команду sudo docker-compose up -d;
5. Запустите приложение (файл nordCodes/src/main/java/com/blck_rbbt/nordCodes/NordCodesApplication);
6. По умолчанию в бд уже созданы 2 пользователя: {
        id: 1, signature: $2a$12$g31U1GzT2o9SEUZc4l3tnu4IYCOQflrHEUnIGqQPuuOpvIuuCqHVu
    },
    {
        id: 2, signature: $2a$12$g31U1GzT2o9SEUZc4luuOpvIYCOQflrHEUnIGqQPuuOpvIuuCqHVu
    }
7. Приложение запустится по адресу localhost:9009/api/v1/
8. Роутинг приложения:
   - localhost:9009/api/v1/ (создать короткую ссылку);
   - localhost:9009/api/v1/{hash} (hash - это уже созданная короткая ссылка, если ввести этот путь в браузере, 
                                    пользователь будет переведен на ссылку-родтеля);
   - localhost:9009/api/v1/urls/statistic (вывод статистики по всем коротким ссылкам);
   - localhost:9009/api/v1/lifetime/{hash}/{time} (установит время жизни time для короткой ссылки hash);
   - localhost:9009/api/v1/urls/delete/{hash} (удалит короткую ссылку);
9. Экспорт примеров запросов для постман находтся в корне проекта (файл NordCode.postman_collection.json)
