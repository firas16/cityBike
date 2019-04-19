CityBike

This repository contains a program that reads a json file into a dataframe then does clustering on the coordinates in order to create groups of stations. Finally, it writes the result into a CSV file.

In order to run the job:

Requirements:
- Spark 2.1
- Java

You need to create teh Jar file by running sbt assembly inside your IDE. This step is not necessary if you have already the Jar file.
You run this command line inside your linux shell:
- spark-submit --class ClusterMain cityBike-assembly-1.0.jar  inputPath=Brisbane_CityBike.json outputPath=D:/test/result

Where
- inputPath is the path of your input file
- outputPath is where you want to store the result which is a csv file
