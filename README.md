# passpoint_android
 Мобильная часть системы программного сервиса верификации согласия посетителей с правилами посещения ЦОД КРОК, отвечающая за сбор
подписей.
## Activities
### MainActivity
Данная активити отвечает за ознакомление с правилами посещения ЦОД КРОК, на ней расположены:
1. TextView в ScrollView для вывода правил посещения
2. ImageView с onClick для смены языка
3. Button для перехода на SignActivity 
### SignActivity
Данная активти отвечает за сбор и отправку подписи на сервер, на ней расположены:
1. DrawingView для получения имени
2. DrawingView для получения подписи
3. Button отправки подписи на сервер