# Passive Traffic Analysis Tool

This tool allows to comprehend passive traffic on devices running Android. It is intended for scientific use an can help correlate background traffic to the state of the network.

# Features

* Runs tcpdump and capture background traffic
* Allows to set a threshold in kbytes for background traffic upon which a speed benchmark (using iperf)
* Allows to configure said threshold to make up for slow start
* Allow to configure time safe-guards to determine when a flow a traffic has finished in order to start the benchmark
* Stores results of benchmarks in a user-accesible sqlite database
* Program systematic downloads to generate a configurable amount of traffic at a fixed configurable rate

## Requirements
1. An ARM device running Android 4.0+
2. Root access on said device
3. ARM compilation of tcpdump with name 'tcpdump' located in /system/xbin/


