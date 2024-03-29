Процесс разработки нового функционала

Тестирование происходит на всех этапах жизненного цикла программного обеспечения:
1. Обсуждение нового функционала: На основании черновой версии спецификации планируется провести дискуссионную встречу. 
   После совещания, отдел тестирования начинает составлять checklist на новый функционал.
2. Проверка требований: после завершения заполнения checklist, наполняются testcase, сверяется с описанием задач.
3. Разработка. По результатам разработки программного обеспечения происходит обновление и добавление testcase.
4. Демонстрация. Проводится демонстрация работающего сервиса или системы проводится для всей команды. 
5. Запуск unit тестирования. Тестировщик запускает unit тесты в среде DEV.
6. Функциональное тестирование: Прохождение теста по подготовленным сценариям и описание найденных ошибок в трекере (первый цикл тестирования).
7. Реализации автотестов: Создание автоматизированных сценариев на основе описанных testcase.
8. Исправление ошибок: Проверка ошибок, обнаруженных в результате первого цикла тестирования. 
   Второй цикл тестирования должен включать исправление всех критических проблем и подготовку минимально жизнеспособного продукта (MVP).
9. Верификация процесса автоматизированного регрессионного тестирования.
10. Release Protocol: Подготовка релизного протокола, подтверждающего готовность продукта к внедрению.
11. Release notes и документация: Подготовка Release notes и документации для новой функции, включая руководства пользователя, техническую документацию и все соответствующие тестовые примеры.
12. Составление плана отката: план необходим для отмены изменений в случае возникновения непредвиденных проблем после развертывания в производственной среде.
13. Выпуск в производство: Подписи всех ответственных на Release Protocol и развертывание в производство, проверка того, что необходимая версия развернута в производство.
14. Пользовательское приемочное тестирование (UAT). Сбор обратной связи от пользователей и добавление необходимых требований в следующий релиз.