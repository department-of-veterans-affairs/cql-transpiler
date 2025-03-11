@echo on

REM Run the Transpiler class with some default arguments
java -cp transpiler/build/libs/transpiler-1.0-SNAPSHOT.jar gov.va.transpiler.jinja.Transpiler ^
    librarySource=./resources/cql/ ^
    jinjaTarget=./resources/jinja/ ^
    targetLanguage=sparksql ^
    printFunctions=false