To assemble a jar file for your project, run the "jar" gradle task, either through IntelliJ or by doing
`gradle jar` on a terminal. Gradle will automatically download all dependencies needed to compile your jar file,
which will be stored in the build/libs folder.

Make sure to edit the main class attribute the build.gradle file, you'll need to change it in order to obtain
a working jar file.

# Important Files
## build.gradle / gradle.properties
This is the gradle configuration file. Modify this file to add dependencies to your project. In
 general you should only modify the depedencies section of this file, however there are a few
  modification you will need to make when you begin the project
  
  - `mainClassName`
    - Modifiy this variable to point to your main class. By default it is `Main
    `, but once you update your teamname package to your team letter you will need to update this
     path.
     
  - jaCoCo
    - jaCoCo is a JAva COde COverage checker that enforces testing. By default the rules are 25
    % line coverage and 25% branch coverage, but if you would like to be more successful you
     should raise these numbers higher to enforce team members to write more tests. Simply modify
      the `minimum` values to enforce stricter tests (but do not change to below .25, as that is
       the required minimum for this class)
  - spotless
    - spotless is a style guider checker/formatter that will automatically detect if your code
     adheres to an agreed style guide. For this starter code I have defaulted to Google's style
     guide, as it is well known and well liked. You can find more documentation for spotless 
     [here](https://github.com/diffplug/spotless). If you would like to disable the spotless
      checks, comment out the spotless plugin as well as the spotless configuration at the bottom
       of the file

## .travis.yml
This is the Travis-CI configuration file. **Only modify this file if you are sure you know what you are doing**

## lombox.config
This is the configuration for [Lombok](https://projectlombok.org/), a very useful java library
 that makes 'enterprise' coding a breeze. **Only modify this file if you are sure you know what you are doing**

## .gitignore
This file tells git which files to ignore in the repo. It should be correctly configured already
**Only modify this file if you are sure you know what you are doing**

## .hooks/
This directory contains a useful git hook that will force your teammates to run tests before
pushing to github. This `pre-push` hook will run gradle tests to make sure code passes.

To install these hooks, simply run `git config core.hookspath .hooks` from the root directory

## config/
Config contains styleguide config files both for checkstyle (another optional plugin for gradle
) and for intellij. Teams should determine a style guide to follow to make 
although it is not a requirement for this class.

To install the styleguide scheme into IntelliJ, `Preferences -> Editor -> Code Style -> Scheme
 -> ... -> Import Scheme -> IntelliJ IDEA code style XML`, then select `config/intellij-java
 -google-style.xml` from the project's root directory 


# Adding Jars to SceneBuilder
## Step 1: Download and run SceneBuilder
Open up SceneBuilder. Everyone should have version 17.0.0. If you do not have version 17.0.0, 
or you have the latest version (18.0.0), this will cause errors for everyone when you use certain features.

## Step 2: Adding Jars
There are 2 ways to add jar files. The first, everyone should have done when installing the required jars
during the "Setting Up Software Dev Env." The second, is through the SceneBuilder GUI.

### First, Navigate to the Library Bar at the top left of the screen.
It should be just below the menu bar and above all SceneBuilder icons

### Next, click the gear icon at the end of the search bar.
It is a small gear just to the right of the search bar.

### Next, click "JAR/FXML Manager." A pop-up should appear

### Under Actions, click Search Repositories

### Finally, search the required Jar files.
We are using MaterialFX and ControlsFX. Type MaterialFX into the search repositories and be sure
to download the repository named "io.github.palexdev:materialfx". Next, click Add Jar. Another pop-up
should have shown up. Make sure every box is checked (it should be by default), and click "Import Contents".
Now everything should be added. Follow the same process for ControlsFX. Search ControlsFx.
In Search Results, click on the repository named "org.controlsfx:controlsfx". There are **multiple**
versions of ControlsFX and MaterialFX. For Controls FX, some will not work properly without having 
the main controlsfx jar downloaded. **Do not download these.** After adding every jar, SceneBuilder should have
all of the icons for JFoenix, MaterialFX, and ControlsFX under custom, which is right under the library bar.
JFoenix items all start with JFX and MaterialFX items all start with MFX. ControlsFX icons do not start with anything.

