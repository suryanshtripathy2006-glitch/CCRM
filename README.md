# Campus Course & Records Manager (CCRM)

## Project Overview & How to Run
Console Java app for campus management. JDK 21+.
- Compile: `javac -d bin src/edu/ccrm/**/*.java` (or specify packages on Windows: `javac -d bin src\edu\ccrm\cli\*.java src\edu\ccrm\config\*.java src\edu\ccrm\domain\*.java src\edu\ccrm\io\*.java src\edu\ccrm\service\*.java src\edu\ccrm\util\*.java`)
- Run: `java -ea -cp bin edu.ccrm.cli.CCRMMain`

## Evolution of Java
- 1996: JDK 1.0
- 2004: Java 5 (generics, enums)
- 2014: Java 8 (lambdas, streams)
- 2021: Java 17 (LTS)
- 2023: Java 21 (LTS)

## Java ME vs SE vs EE
| Edition | Description |
|---------|-------------|
| ME | Embedded/mobile, limited APIs |
| SE | Standard desktop, core libs |
| EE | Enterprise servers, web/distributed |

## JDK/JRE/JVM
JVM: Executes bytecode. JRE: JVM + runtime libs. JDK: JRE + dev tools. JDK compiles source to bytecode; JRE runs it.

## Windows Install Steps
1. Download JDK from oracle.com/downloads.
2. Run installer.
3. Add bin to PATH.
4. Verify: `java -version`.
![JDK Verification](screenshots/jdk-verification.png)

## Eclipse Setup Steps
1. Download Eclipse for Java.
2. New Java Project "CCRM".
3. Add src files.
4. Run CCRMMain.
![Eclipse Setup](screenshots/eclipse-setup.png)
![Program Running](screenshots/program-running.png)

## Mapping Table: Syllabus Topic → File/Class/Method
| Topic | Location |
|-------|----------|
| Encapsulation | domain/* (private fields + getters/setters) |
| Inheritance | domain/Student.java extends Person |
| Abstraction | domain/Person.java abstract class/method |
| Polymorphism | service/TranscriptServiceImpl.java getRole() |
| Interfaces | service/*Service.java |
| Diamond Problem | service/TranscriptServiceImpl.java Demo class |
| Interface vs Class | Interfaces for services (coupling); classes for domain (state) |
| Lambdas | service/CourseServiceImpl.java filter |
| Anonymous Inner | cli/CCRMMain.java demoAnonymous() |
| Enums | domain/Grade.java, Semester.java |
| Singleton | config/AppConfig.java |
| Builder | domain/Course.java |
| Exceptions | domain/*Exception.java, try/catch in cli/CCRMMain.java |
| Assertions | domain/Student.java, Course.java |
| File I/O NIO.2 | io/ImportExportService.java, BackupService.java |
| Streams | service/CourseServiceImpl.java search, cli/CCRMMain.java reports |
| Date/Time | domain/Student.java, Enrollment.java |
| Recursion | util/RecursionUtil.java |
| Loops/Jumps | cli/CCRMMain.java while/do-while/for |
| Arrays/Strings | cli/CCRMMain.java main() |
| Operator Precedence | int x = 2 + 3 * 4; (14, * before +) |

## Notes on Enabling Assertions
Run with `-ea`. Sample: `java -ea -cp bin edu.ccrm.cli.CCRMMain`

