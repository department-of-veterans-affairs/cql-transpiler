cd %~dp0elm
start /d %~dp0common cmd /C scalaxb quick-datatypes.xsd -d output --no-runtime
start /d %~dp0elm cmd /C scalaxb library.xsd -d output --no-runtime
start /d %~dp0model cmd /C scalaxb modelinfo.xsd -d output --no-runtime
@REM cd ..
@REM cd %~dp0model
@REM scalaxb modelinfo.xsd -d output --no-runtime
@REM cd ..
@REM cd %~dp0common
@REM scalaxb quick-datatypes.xsd -d output --no-runtime
@REM echo "DONE"
@REM cd ..