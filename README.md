BOFH
====

Java implementation of the BOFH excuse generator. The excuses are customizable, but the originals are taken from http://bofh.ntk.net/BOFH/bastard-excuse-board.php. Do not send this program to Simon!

This program showcases some of the classes of the JTools project. In particular, it plays an audio clip when the wrong password is entered, and uses multiple CasinoSpinners.

What is BOFH?
-------------

See http://bofh.ntk.net/BOFH/ for the official site.

Dependencies
------------


This project depends on the Mad Physicist JTools project. The JTools jar file must be on the classpath when building and running the BOFH project.

BOFH test code uses TestNG. No specific version is required, although a version in which the annotation `org.testng.annotations.NoInjection` (e.g. 5.13 and higher) is recommended.

