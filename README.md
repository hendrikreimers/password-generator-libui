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

    [Defaults]
    
    ; usage of special chars
    ;specialChars = $*#
    
    ; num of passwords to be generated
    passwordCount = 1
    
    ; password length
    passwordLength = 25
    
    ; only in optimized possible
    percentSpecialChars = 30
    
    ; optimized, normal, or random
    mode = optimized



