echo "Compiling Java files"
javac -d build src/*.java
echo "Running Main.java..."
java -cp build/ tp.common.execute
