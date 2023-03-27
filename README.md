This project has moved to GitLab: https://gitlab.com/madphysicist/bofh. Please download the latest code and submit issues there rather than GitHub, which will no longer be monitored until this stale clone is deleted.

BOFH
====

Java implementation of the BOFH excuse generator. The excuses are customizable, but the originals are taken from http://bofh.ntk.net/BOFH/bastard-excuse-board.php. The original site no longer exists, and the new site (below) does not have the excuse generator. Per his request, do not send this program to Simon!

This program showcases some of the classes of the JTools project. In particular, it plays an audio clip when the wrong password is entered, and uses multiple CasinoSpinners.

What is BOFH?
-------------

See http://www.bofharchive.com/BOFH.html for the author's site.

Dependencies
------------


This project depends on the Mad Physicist JTools project. The JTools jar file must be on the classpath when building and running the BOFH project.

BOFH test code uses TestNG. No specific version is required, although a version in which the annotation `org.testng.annotations.NoInjection` (e.g. 5.13 and higher) is recommended.

