echo "Run script start"
echo "Compiling"
javac -sourcepath src -cp .:lib/* **/*.java;
echo "Running"
java -cp .:lib/*:./src/ -Djava.library.path=native/linux core/Game;

