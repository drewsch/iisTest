# iisTest

### prerequisites:
* java version : OpenJDK 21
* pgsql version : 16

для создания таблицы и наполнения стартовыми данными использовать: ```src/main/resources/databaseInitScript.sql```

далее необходимо сконфигурировать путь до базы данных в файле конфига ```src/main/resources/app.properties```

для использования программы выполнить ```test.sh {command} {fileName}```

примеры для linux:   
* ```./test.sh export output.xml```
* ```./test.sh sync output.xml```

примеры для windows:   
* ```test.bat export output.xml```
* ```test.bat sync output.xml```