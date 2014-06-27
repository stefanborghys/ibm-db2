ibm-db2
=======

IBM db2 tryout

Download db2: [http://www-01.ibm.com/software/data/db2/linux-unix-windows/downloads.html](http://www-01.ibm.com/software/data/db2/linux-unix-windows/downloads.html)  

# Installation
## Mac OS X (not yet working)
A copy of db2 Express-C v10.1 for Mac OS X can be found in: db2/express-c/db2_express-c_v10.1_osx  

**Setup**  
1. execute db2setup (db2/express-c/db2_express-c_v10.1_osx/expc/db2setup)  

For some reason i was not capable of getting the db2 to install using the normal setup.  
After launching it i got following error output:  

<code>DBI1190I  db2setup is preparing the DB2 Setup wizard which will guide you through the program setup process.</code>   <code>Please wait.</code>  
<code>.../ibm-db2/db2/express-c/db2_express-c_v10.1_osx/expc/db2/macos/install/db2setup: line 606:</code>   <code>/tmp/db2_seriousbusiness.tmp.48706/db2/macos/install/../java/jre/bin/java:</code>  
<code>No such file or directory</code>  
<code>DBI1160I  Non-root install is being performed.</code>  

<code>You are using a Java(TM) Runtime Environment that has not been officially tested for use with DB2.</code>  
<code>This command will continue to execute, however if you experience problems,</code>  
<code>refer to the DB2 installation documentation for a list of supported environments.</code>

I tried to change my java version to 1.6 instead of 1.7 but it didn't help.

Find the Java Home of a java version:  
terminal command: /usr/libexec/java_home -v version-number  
example: /usr/libexec/java_home -v 1.6  
example: /usr/libexec/java_home -v 1.7  

**Alternative**  
1. execute db2_install (default install folder: /opt/IBM/db2/V10.1)  

# E-Books
## Getting Started with DB2 Express-C
- Find out what DB2 Express-C is all about
- Understand DB2 architecture, tools, security
- Learn how to administer DB2 databases
- Write SQL, XQuery, stored procedures
- Develop database applications for DB2
- Practice using hands-on exercises

This ebook is ideal for developers, consultants, ISVs, DBAs, students, or anyone who wants to get started with DB2. While this ebook focuses on DB2 Express-C  , the free database edition of DB2, the concepts and content are equally applicable to other DB2 editions on Linux, UNIX, and Windows.

source: [https://www.ibm.com/developerworks/community/wikis/home?lang=en#!/wiki/Big%20Data%20University/page/FREE%20eBook%20-%20Getting%20Started%20with%20DB2%20Express-C](https://www.ibm.com/developerworks/community/wikis/home?lang=en#!/wiki/Big%20Data%20University/page/FREE%20eBook%20-%20Getting%20Started%20with%20DB2%20Express-C)  
repository: [doc/Getting_Started_with_DB2_Express_v9.7_p4](https://github.com/stefanborghys/ibm-db2/blob/master/doc/Getting_Started_with_DB2_Express_v9.7_p4.pdf)
## Getting Started with DB2 Application Development
- Discover DB2® application development using DB2 Express-C
- Write SQL, XQuery, and understand pureXML® technology
- Learn how to develop DB2 stored procedures, functions and data Web services
- Work with DB2 and Java, C/C++, .NET, PHP, Ruby on Rails, Perl, and Python
- Troubleshoot DB2 database-related problems
- Practice using hands-on exercises

DB2 Express-C from IBM is the no-charge edition of DB2 data server for managing relational and XML data with ease. DB2 Express-C runs on Windows®, Linux®, Solaris, and Mac OS X systems, and provides application drivers for a variety of programming languages and frameworks including C/C++, Java, .NET, Ruby on Rails, PHP, Perl, and Python. Review this book and get started with DB2 application development!

source: [https://www.ibm.com/developerworks/community/wikis/home?lang=en#!/wiki/Big%20Data%20University/page/FREE%20ebook%20-%20Getting%20Started%20with%20DB2%20Application%20Development](https://www.ibm.com/developerworks/community/wikis/home?lang=en#!/wiki/Big%20Data%20University/page/FREE%20ebook%20-%20Getting%20Started%20with%20DB2%20Application%20Development)  
repository: [doc/Getting_Started_with_DB2_App_Dev_p2.pdf](https://github.com/stefanborghys/ibm-db2/blob/master/doc/Getting_Started_with_DB2_App_Dev_p2.pdf)  

## Getting Started with IBM Data Studio for DB2
- Find out what IBM Data Studio can do for you
- Learn everyday data management tasks
- Write SQL scripts and schedule them as jobs
- Back up and recover DB2 databases
- Tune queries and use Visual Explain
- Write and debug SQL stored procedures and routines
- Convert existing SQL or stored procedures to Web services
- Practice using hands-on exercises

IBM Data Studio is replacing the DB2® Control Center and other tools for DB2. In conjunction with DB2 Express-C, the no-charge edition of DB2, Data Studio is ideal for DBAs, developers, students, ISVs, or consultants because it's easy and free to use. IBM Data Studio can also be used with other data servers, and you can extend Data Studio with additional robust management and development capabilities from IBM to help accelerate solution delivery, optimize performance, protect data privacy, manage data growth, and more.

source: [https://www.ibm.com/developerworks/community/wikis/home?lang=en#!/wiki/Big%20Data%20University/page/FREE%20ebook%20-%20Getting%20Started%20with%20IBM%20Data%20Studio%20for%20DB2](https://www.ibm.com/developerworks/community/wikis/home?lang=en#!/wiki/Big%20Data%20University/page/FREE%20ebook%20-%20Getting%20Started%20with%20IBM%20Data%20Studio%20for%20DB2)  
repository: [doc/Getting_Started_with_IBM_Data_Studio_v311.pdf](https://github.com/stefanborghys/ibm-db2/blob/master/doc/Getting_Started_with_IBM_Data_Studio_v311.pdf)


