This framework has only one prameterized test case. Test case is to login to naukri.com and update user profile.To login to naukri.com it reads data from xls sheet.
In the xls sheet user can define test case name, user name ,password , browser and run mode. Run mode is used to specify whether user want to run the test case or not.
You can see same automation script is being used with different parameters, so basically this design is based on data driven approach.Selenium WebDriver has been used 

to interact with webelements.Core Java has been used as language to support test case and to maintain framework's component inter communication.TestNg with data 

provider and listener has been used to parameterized test case and report multiple failures in single test case. XSLT reporting hasbeen generated at the end of 

execution.And to make aware the product owner about the build status, mail is automatically fired with report as an attachment.These all different functionalities 
has been put together in Ant's build.xml file as a separate target.These all targets are mentioned in single batch file.User need to run this batch file to get all 

these things working together. 
