#!/bin/bash

##
## install.sh
##
## Mad Physicist BOFH Excuse Generator Project
##
## The MIT License (MIT)
##
## Copyright (c) 2014 by Joseph Fox-Rabinovitz
##
## Permission is hereby granted, free of charge, to any person obtaining a copy
## of this software and associated documentation files (the "Software"), to deal
## in the Software without restriction, including without limitation the rights
## to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
## copies of the Software, and to permit persons to whom the Software is
## furnished to do so, subject to the following conditions:
##
## The above copyright notice and this permission notice shall be included in
## all copies or substantial portions of the Software.
##
## THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
## IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
## FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
## AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
## LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
## OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
## THE SOFTWARE.
##

##
## This script is a Linux Mint/Ubuntu/Arch pseudo-install script. In its current
## incarnation it creates symlinks to the key parts of JTools. To copy instead
## of symlinking, change PROG from "ln -s" to "cp".
##
## Author:   Joseph Fox-Rabinovitz
## Version:  1.0.0, 03 Feb 2014: Joseph Fox-Rabinovitz: Created.
## Version:  1.0.1, 12 Jun 2015: Joseph Fox-Rabinovitz: Added exec support.
##

PROG="ln -s"

ISRC=/usr/local/src
IDOC=/usr/local/share/doc
IJAR=/usr/local/share/java
IBIN=/usr/local/bin

EXE=bofh
BASE=madphysicist-BOFH
JEXT=.jar

# Do not export!
function inst() {
    INSTALL_DIR="$1"
    FROM_FILE="$2"
    FILE_NAME="$(basename "${FROM_FILE}")"
    TO_FILE="${INSTALL_DIR}/${FILE_NAME}"
    if [ -f "${TO_FILE}" ]
    then
        echo "Removing previous version from \"${TO_FILE}\""
        rm "${TO_FILE}"
    fi
    echo "Installing \"${FROM_FILE}\" into \"${INSTALL_DIR}\""
    mkdir -p "${INSTALL_DIR}"
    ${PROG} "$(pwd)/${FROM_FILE}" "${INSTALL_DIR}"
}

function inst_jar() {
    INSTALL_DIR="$1"
    FILE_SUFFIX="$2"
    FILE_NAME="dist/${BASE}${FILE_SUFFIX}${JEXT}"
    inst "${INSTALL_DIR}" "${FILE_NAME}"
}

function uinst() {
    INSTALL_DIR="$1"
    FILE_NAME="$2"
    TO_FILE="${INSTALL_DIR}/${FILE_NAME}"
    if [ -f "${TO_FILE}" ]
    then
        echo "Uninstalling from \"${TO_FILE}\""
        rm "${TO_FILE}"
    else
        echo "\"${FILE_NAME}\" not installed"
    fi
}

function uinst_jar() {
    INSTALL_DIR="$1"
    FILE_SUFFIX="$2"
    FILE_NAME="${BASE}${FILE_SUFFIX}${JEXT}"
    uinst "${INSTALL_DIR}" "${FILE_NAME}"
}

if [ ${#} -eq 1 -a "${1}" == "-u" ]
then    
    uinst_jar "${ISRC}" "-sources"
    uinst_jar "${IDOC}" "-javadoc"
    uinst_jar "${IJAR}" ""
    uinst "${IBIN}" "${EXE}"
elif [ ${#} -ne 0 ]
then
    echo "Usage: ${0} [-u]"
    echo "    no argument installs ${BASE}"
    echo "    -u uninstalls ${BASE}"
    exit 1
else
    inst_jar "${ISRC}" "-sources"
    inst_jar "${IDOC}" "-javadoc"
    inst_jar "${IJAR}" ""
    inst "${IBIN}" "${EXE}"
fi

