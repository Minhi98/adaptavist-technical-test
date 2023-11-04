# Technical Test

Developed using Java 21 and IntelliJ IDEA Community. Standard libraries are used so older editions of Java should be compatible.

The CLI program assumes one argument: the file path of the input `java Main filepath.txt`

Of the two example txt files, example2 is a shuffled version of example1 (different order of words and same frequencies).

# Instructions

The instructions below assume bash, but the .idea folder contains the IntelliJ environment with run configurations.

The below shell commands can be copied and pasted from the root of the repository folder (`adaptavist-technical-test/`) depending on your environment.

Linux (Bash)

```shell
javac src/Main.java
mv src/*.class .

java Main example1.txt
java Main example2.txt
```

Windows (Powershell)

```powershell
javac src/Main.java
Move-Item -Path .\src\*.class -Destination .\

java Main example1.txt
java Main example2.txt
```
