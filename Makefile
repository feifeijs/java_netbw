all:
	javac cal_bw_java/Server.java
	javac cal_bw_java/Client.java
clean:
	rm -f cal_bw_java/*.class
