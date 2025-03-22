#!/bin/bash

find $1 -type f \( -name "*.java" -o -name "*.kt" \) > source.txt
