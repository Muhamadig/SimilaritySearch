Final Project - Search for Similar Texts in Professional Databases

0)open ProjectJars folder.

1) There is two servers that you should run before you lunch the application.

the first one is in WordNetServer folder, you should run it in the cmd.

cd yourpath/WordNetServer

java -jar WordNetServer.jar [port number] /* if you do not enter a port number , the default port number is 8000*/

the second on is in in folder ProjectJars , this server will connect to mysql (you should create a scheme called ssdb)
cd yourpath/ApplicationServer

java -jar AppServer.jar [port number] jdbc:mysql://localhost/ssdb [mysql password] /* if you do not enter a port number , the default port number is 10000*/

2) Now run the database pre-processing jar.

the database texts located in HTMLs folder.

you can run this jar by clicking on it or by cmd (like before) 

2.1) after running the database pre-processing jar you will create your own workspace 

2.2) now you will browse your html files (database texts) and start the pre-processing.

2.3) this step will create the global vector and the find the threshold 

2.4) Now clustering the database texts, first you should prepare the texts to be clusterd and then press the clustering button.

2.5) finally, you should update the database with the new clustering.

3) Now run the searching jar located in SimilaritySearchApplication folder.
you can run this jar by clicking on it or by cmd (like before) 

3.1) browse your text (it should be pdf,doc,docx or html file only).

3.2) after the table is filled you can press on one text and press view text button to open it.