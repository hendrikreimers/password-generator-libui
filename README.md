Password Generator
==================

Simple Password generator build in kotlin native, using kotlin-libui.

## Source of kotlin-libui ##
* https://github.com/msink/kotlin-libui/
* https://github.com/msink/hello-libui/

## Screenshot ##

![Screenshot](./screenshot.png?raw=true "")

## Final Build ##
https://github.com/hendrikreimers/password-generator-libui/blob/main/build/bin/libui/releaseExecutable/password-generator-libui.exe?raw=true

### INI Configuration File Example ###

Filename must be: ***password-generator.ini***

    ; the following line is useless and optional
    [passwordGeneratorLibui]
    
    ;specialChars = "$*#"
    
    passwordCount = 1
    passwordLength = 25
    
    percentSpecialChars = 30
    
    ; Hidden Option to randomize it completely and ignoring the percent of specialChars and no special chars at the beginning and at the end of password
    ;fullRandom = 1
