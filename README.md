# ESGF JDownloader

JDownloader with built-in support to download from ESGF.

## JDownloader development set up

Follow the instructions in https://beta.jdownloader.org/developmentquicktutorial.

Checkout the following revisions:

- AppWorkUtils: 3629
- JDBrowser: 45302
- JDownloader: 45328
- MyJDownloaderClient: 44189

Copy the following files:

- JDownloader/src/jd/plugins/hoster/ESGF.java
- JDownloader/build/newBuild/build.xml
- JDownloader/build/newBuild/build\_standalone.xml
- AppWorkUtils/build/build.xml

Run the build\_standalone.xml from Eclipse.

## Package

- http://www.devcompack.com/setupmaker/
- http://launch4j.sourceforge.net/


Go to the directory where Ant generated the JARs (`JDownloader/build/newBuild/dist/standalone/dist`) and generate a ZIP file with all the contents:

`zip -r esgf-downloader.zip *`

Use launch4j to create a Windows executable from JDownloader.jar.

Use DCP SETUP MAKER to create a JAR that contains esgf-downloader.zip and the executable generated with launch4j.

Generate another Windows executable with launch4j using the last generated JAR.
