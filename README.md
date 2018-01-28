# Slack-Calc-Bot
### I solve your problem.

## 1. Runtime Dependencies

## JDK 8

### Install
Use either a package manager or download from
[Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

### Verification
Run java -version at the command line, output should look something like this (versions might differ).

    $ java -version
    java version "1.8.0_121"
    Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
    Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)


Also verify that the JAVA_HOME environment variable is setup correctly. (Actual value will be dependent on your platform).

    $ echo $JAVA_HOME
    C:\Program Files\Java\jdk1.8.0_121


## Gradle

### Install
Follow the instructions on the Gradle site for your platform.
[Gradle Install](https://gradle.org/install)

### Verification
Run the following.  The important thing to note are the Gradle version and the JVM version (versions might differ).

    $ gradle -version

    ------------------------------------------------------------
    Gradle 2.14.1
    ------------------------------------------------------------

    Build time:   2016-07-18 06:38:37 UTC
    Revision:     d9e2113d9fb05a5caabba61798bdb8dfdca83719

    Groovy:       2.4.4
    Ant:          Apache Ant(TM) version 1.9.6 compiled on June 29 2015
    JVM:          1.8.0_121 (Oracle Corporation 25.121-b13)
    OS:           Windows 7 6.1 amd64
    
## 2. Creat a custom bot
Create a custom bot with name "calcbot" at [Slack Bot Creation Site](https://my.slack.com/services/new/bot).

## 3. Clone and build
## Source Code
Now that we have our runtime dependencies installed and configured, clone this repository into the local machine.

    # Clone the project
    $ git clone https://github.com/huschlen/Slack-Calc-Bot.git
    # Build
    $ gradle build

Windows users may need to use `gradle -Dfile.encoding=UTF-8 build` due to UTF support issues.

## 4. Run
Run the following command from the project root directory.

    $ gradle bootrun
    
Now calcbot should be online.  He will solve your problem!
