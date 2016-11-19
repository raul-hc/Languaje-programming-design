cd tools\byaccj
yacc.exe -J -v -Jpackage=sintactico -Jsemantic=Object "../../src/sintactico/sintactico.y"
move Parser.java ../../src/sintactico
move y.output ../../src/sintactico
pause