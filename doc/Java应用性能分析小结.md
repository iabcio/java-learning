## Java应用性能分析小结

#### 一些常用工具

* uptime
* vmstat
* mpstat
* iostat
* pidstat
* free
* top
* jps
* jinfo
* jstat
* jmap
* jstack
* lsof
* sar
* jvisualvm
* HeapAnalyze

#### 准备工作

1、在用户主目录下新建jdk.sh

`测试环境`

```
JAVA_HOME="/usr/local/jdk_1.8"
export JAVA_HOME

export CLASSPATH=.:$JAVA_HOME
export PATH=$PATH:$JAVA_HOME/bin:$JAVA_HOME/jre/bin
export CLASSPATH=.:$JAVA_HOME/lib:$CLASSPATH
```

`线上环境`

```
JAVA_HOME="/usr/local/env/jdk1.8"
export JAVA_HOME

export CLASSPATH=.:$JAVA_HOME
export PATH=$PATH:$JAVA_HOME/bin:$JAVA_HOME/jre/bin
export CLASSPATH=.:$JAVA_HOME/lib:$CLASSPATH
```


2、修改~/.bashrc,添加

```
if [ -f ~/jdk.sh ]; then
        . ~/jdk.sh
fi
```

#### 常规操作

##### 查看负载 `uptime`

```
[dubbomm@wecloud-test-docker ~]$ uptime
 10:16:44 up 273 days, 21:36,  1 user,  load average: 2.93, 2.72, 2.56
 
```

最右的三个数值分别是1分钟、5分钟、15分钟系统负载的移动平均值。它们共同展现了负载随时间变动的情况


##### 查看虚拟内存状态(进程、内存、I/O等系统整体运行状态)

每秒刷新1次，一共刷新10次

```
[dubbomm@wecloud-test-docker ~]$ vmstat  1 10
procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 2  0      0 181008      0 404272    0    0     2  1603    0    0 12  3 84  1  1
10  0      0 180544      0 404352    0    0     0     0 4965 6784  5  2 89  0  4
 0  1      0 181736      0 403252    0    0     0 17276 8221 10876 26  5 60  7  2
 4  1      0 181692      0 403184    0    0     0     0 6668 9596  7  3 65 24  2
 5  0      0 188164      0 396804    0    0     0  4114 9601 14071 12  6 76  3  3
 0  1      0 186692      0 398208    0    0     0  8212 6928 9400 16  4 60 13  7
11  0      0 184132      0 401964    0    0     0  8224 6768 8901 13  4 61 16  5
 2  0      0 181336      0 404228    0    0     0 20740 8588 11349 27  5 61  5  2
 4  0      0 185744      0 401056    0    0     0 20542 7279 9389 26  5 61  3  5
 2  0      0 180836      0 404148    0    0     0 16416 6553 7274 24  5 62  3  7

```


r: 运行队列中进程数量，这个值也可以判断是否需要增加CPU。（长期大于1）
b: 等待IO的进程数量。
swpd: 使用虚拟内存大小，如果swpd的值不为0，但是SI，SO的值长期为0，这种情况不会影响系统性能。
free: 空闲物理内存大小。
buff: 用作缓冲的内存大小。
cache: 用作缓存的内存大小，如果cache的值大的时候，说明cache处的文件数多，如果频繁访问到的文件都能被cache处，那么磁盘的读IO bi会非常小。
si: 每秒从交换区写到内存的大小，由磁盘调入内存。
so: 每秒写入交换区的内存大小，由内存调入磁盘。
bi: 每秒读取的块数
bo: 每秒写入的块数
in: 每秒中断数，包括时钟中断。
cs: 每秒上下文切换数。
us: 用户进程执行时间百分比(user time)
us的值比较高时，说明用户进程消耗的CPU时间多，但是如果长期超50%的使用，那么我们就该考虑优化程序算法或者进行加速。
sy: 内核系统进程执行时间百分比(system time)
sy的值高时，说明系统内核消耗的CPU资源多，这并不是良性表现，我们应该检查原因。
wa: IO等待时间百分比
wa的值高时，说明IO等待比较严重，这可能由于磁盘大量作随机访问造成，也有可能磁盘出现瓶颈（块操作）。
id: 空闲时间百分比

##### 查看各个CPU状态信息

每秒统计1次，一共统计2次

```
[dubbomm@wecloud-test-docker ~]$ mpstat -P ALL 1 2
Linux 3.10.0-327.el7.x86_64 (wecloud-test-docker.novalocal) 	2018年05月15日 	_x86_64_	(4 CPU)

10时58分17秒  CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
10时58分18秒  all   12.33    0.00    4.56   19.30    0.00    1.61    0.80    0.00    0.00   61.39
10时58分18秒    0   17.39    0.00    3.26   78.26    0.00    0.00    1.09    0.00    0.00    0.00
10时58分18秒    1   23.26    0.00    9.30    0.00    0.00    5.81    1.16    0.00    0.00   60.47
10时58分18秒    2    6.32    0.00    3.16    0.00    0.00    0.00    0.00    0.00    0.00   90.53
10时58分18秒    3    4.17    0.00    2.08    0.00    0.00    0.00    0.00    0.00    0.00   93.75

10时58分18秒  CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
10时58分19秒  all    8.12    0.00    2.62   22.77    0.00    1.05    0.26    0.00    0.00   65.18
10时58分19秒    0    6.32    0.00    2.11   91.58    0.00    0.00    0.00    0.00    0.00    0.00
10时58分19秒    1   21.74    0.00    7.61    0.00    0.00    4.35    0.00    0.00    0.00   66.30
10时58分19秒    2    2.00    0.00    1.00    0.00    0.00    0.00    1.00    0.00    0.00   96.00
10时58分19秒    3    3.06    0.00    1.02    0.00    0.00    0.00    1.02    0.00    0.00   94.90

平均时间:  CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
平均时间:  all   10.20    0.00    3.58   21.06    0.00    1.32    0.53    0.00    0.00   63.31
平均时间:    0   11.76    0.00    2.67   85.03    0.00    0.00    0.53    0.00    0.00    0.00
平均时间:    1   22.47    0.00    8.43    0.00    0.00    5.06    0.56    0.00    0.00   63.48
平均时间:    2    4.10    0.00    2.05    0.00    0.00    0.00    0.51    0.00    0.00   93.33
平均时间:    3    3.61    0.00    1.55    0.00    0.00    0.00    0.52    0.00    0.00   94.33

```

##### 查看IO

每秒统计1次，一共统计2次

```
[dubbomm@wecloud-test-docker ~]$ iostat -xz 1 2
Linux 3.10.0-327.el7.x86_64 (wecloud-test-docker.novalocal) 	2018年05月15日 	_x86_64_	(4 CPU)

avg-cpu:  %user   %nice %system %iowait  %steal   %idle
          11.84    0.00    2.78    0.61    0.83   83.94

Device:         rrqm/s   wrqm/s     r/s     w/s    rkB/s    wkB/s avgrq-sz avgqu-sz   await r_await w_await  svctm  %util
vda               0.00     0.07    0.09   13.07     7.92  6228.80   947.44     0.00    0.06   25.65   13.76   9.90  13.03
vdb               0.00     0.00    0.00    0.00     0.00     0.00    16.22     0.00    0.12    0.12    0.00   0.11   0.00

avg-cpu:  %user   %nice %system %iowait  %steal   %idle
           9.30    0.00    5.43   21.71    0.26   63.31

Device:         rrqm/s   wrqm/s     r/s     w/s    rkB/s    wkB/s avgrq-sz avgqu-sz   await r_await w_await  svctm  %util
vda               0.00     0.00    0.00    4.00     0.00   916.00   458.00     4.53   59.00    0.00   59.00 197.00  78.80

```

其它常用命令:
单纯查看CPU状态: iostat -c 1 2       
单纯查看IO状态 : iostat -d -m 1 2

rrqm/s	每秒需要读取需求的数量
wrqm/s	每秒需要写入需求的数量
r/s 	每秒实际读取需求的数量
w/s	每秒实际写入需求的数量
rsec/s	每秒读取区段的数量
wsec/s	每秒写入区段的数量
rkB/s	每秒实际读取的大小，单位为KB
wkB/s	每秒实际写入的大小，单位为KB
avgrq-sz	需求的平均大小区段
avgqu-sz	需求的平均队列长度
await	等待I/O平均的时间（milliseconds）
svctm	I/O需求完成的平均时间
%util	被I/O需求消耗的CPU百分比

##### 查看内存

```
[dubbomm@wecloud-test-docker ~]$ free -m
              total        used        free      shared  buff/cache   available
Mem:           3791        3295         112         200         382          86
Swap:             0           0           0
```

##### 查看网络负载

```
[dubbomm@wecloud-test-docker ~]$ sar -n DEV 1 2
Linux 3.10.0-327.el7.x86_64 (wecloud-test-docker.novalocal) 	2018年05月15日 	_x86_64_	(4 CPU)

11时21分24秒     IFACE   rxpck/s   txpck/s    rxkB/s    txkB/s   rxcmp/s   txcmp/s  rxmcst/s
11时21分25秒      eth0   4328.00   4281.00   1171.92    863.77      0.00      0.00      0.00
11时21分25秒        lo      0.00      0.00      0.00      0.00      0.00      0.00      0.00
11时21分25秒   docker0      0.00      0.00      0.00      0.00      0.00      0.00      0.00

11时21分25秒     IFACE   rxpck/s   txpck/s    rxkB/s    txkB/s   rxcmp/s   txcmp/s  rxmcst/s
11时21分26秒      eth0   4255.00   4316.00   2326.88    939.08      0.00      0.00      0.00
11时21分26秒        lo      0.00      0.00      0.00      0.00      0.00      0.00      0.00
11时21分26秒   docker0      0.00      0.00      0.00      0.00      0.00      0.00      0.00

平均时间:     IFACE   rxpck/s   txpck/s    rxkB/s    txkB/s   rxcmp/s   txcmp/s  rxmcst/s
平均时间:      eth0   4291.50   4298.50   1749.40    901.42      0.00      0.00      0.00
平均时间:        lo      0.00      0.00      0.00      0.00      0.00      0.00      0.00
平均时间:   docker0      0.00      0.00      0.00      0.00      0.00      0.00      0.00
```

IFACE:网卡
rxpck/s:每秒收到的包
txpck/s:每秒发送的包
rxkB/s:每秒收到的KB
txkB/s:每秒发送的KB
rxcmp/s:每秒收到的压缩包
txcmp/s:每秒发送的压缩包


##### 查看TCP和ETCP统计信息

```
[dubbomm@wecloud-test-docker ~]$ sar -n TCP,ETCP 1 2
Linux 3.10.0-327.el7.x86_64 (wecloud-test-docker.novalocal) 	2018年05月15日 	_x86_64_	(4 CPU)

12时34分44秒  active/s passive/s    iseg/s    oseg/s
12时34分45秒    384.00      3.00   3676.00   3769.00

12时34分44秒  atmptf/s  estres/s retrans/s isegerr/s   orsts/s
12时34分45秒      0.00    127.00      8.00      0.00     10.00

12时34分45秒  active/s passive/s    iseg/s    oseg/s
12时34分46秒    369.00      1.00   3219.00   3387.00

12时34分45秒  atmptf/s  estres/s retrans/s isegerr/s   orsts/s
12时34分46秒      0.00    100.00      3.00      0.00     10.00

平均时间:  active/s passive/s    iseg/s    oseg/s
平均时间:    376.50      2.00   3447.50   3578.00

平均时间:  atmptf/s  estres/s retrans/s isegerr/s   orsts/s
平均时间:      0.00    113.50      5.50      0.00     10.00
```

active/s：本地每秒创建的 TCP 连接数（比如 concept() 创建的）
passive/s：远程每秒创建的 TCP 连接数（比如 accept() 创建的）
retrans/s：每秒 TCP 重传次数
atmptf/s:每秒连接失败次数
estres/s:每秒(收到)连接重置次数
retrans/s:每秒重传次数
isegerr/s:每秒收到的错误TCP报文数目
orsts/s:每秒(发出)连接重置次数


##### 查看java进程

```
[dubbomm@wecloud-test-docker ~]$ jps
18192 Jps
18370 start.jar

[dubbomm@wecloud-test-docker ~]$ jps -l
18212 sun.tools.jps.Jps
18370 /home/dubbomm/.jetty/start.jar
```

##### 查看整体负载

```
[dubbomm@wecloud-test-docker logs]$ top -d 2
top - 13:44:12 up 274 days,  1:03,  1 user,  load average: 1.76, 2.45, 4.09
Tasks: 134 total,   1 running, 129 sleeping,   4 stopped,   0 zombie
%Cpu(s): 11.8 us,  2.4 sy,  0.0 ni, 83.9 id,  0.6 wa,  0.0 hi,  0.4 si,  0.8 st
KiB Mem :  3882056 total,   189508 free,  3050628 used,   641920 buff/cache
KiB Swap:        0 total,        0 free,        0 used.   376228 avail Mem

  PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND
18370 dubbomm   20   0 7888352 2.736g  21560 S 126.7 73.9  18:51.31 java
18985 dubbomm   20   0  146184   2032   1408 R   6.7  0.1   0:00.01 top
    1 root      20   0  190792   2580   1172 S   0.0  0.1  10:44.97 systemd
    2 root      20   0       0      0      0 S   0.0  0.0   0:02.92 kthreadd
    3 root      20   0       0      0      0 S   0.0  0.0   5:51.58 ksoftirqd/0
    5 root       0 -20       0      0      0 S   0.0  0.0   0:00.00 kworker/0:0H
    7 root      rt   0       0      0      0 S   0.0  0.0   0:37.71 migration/0
    8 root      20   0       0      0      0 S   0.0  0.0   0:00.00 rcu_bh
    9 root      20   0       0      0      0 S   0.0  0.0   0:00.00 rcuob/0
   10 root      20   0       0      0      0 S   0.0  0.0   0:00.00 rcuob/1
   11 root      20   0       0      0      0 S   0.0  0.0   0:00.00 rcuob/2
   12 root      20   0       0      0      0 S   0.0  0.0   0:00.00 rcuob/3
   13 root      20   0       0      0      0 S   0.0  0.0 644:48.31 rcu_sched
   14 root      20   0       0      0      0 S   0.0  0.0 100:52.87 rcuos/0
   15 root      20   0       0      0      0 S   0.0  0.0 382:11.92 rcuos/1
```

##### 查看进程的线程负载

```
[dubbomm@wecloud-test-docker logs]$ top -Hp 18370
top - 14:31:38 up 274 days,  1:51,  1 user,  load average: 1.64, 1.87, 1.91
Threads: 284 total,   0 running, 284 sleeping,   0 stopped,   0 zombie
%Cpu(s): 16.7 us,  3.1 sy,  0.0 ni, 67.7 id, 11.2 wa,  0.0 hi,  0.3 si,  1.0 st
KiB Mem :  3882056 total,   145880 free,  3233236 used,   502940 buff/cache
KiB Swap:        0 total,        0 free,        0 used.   194516 avail Mem

  PID USER      PR  NI    VIRT    RES    SHR S %CPU %MEM     TIME+ COMMAND
18462 dubbomm   20   0 7899764 2.905g  17000 S 45.2 78.5  38:00.55 java
18504 dubbomm   20   0 7899764 2.905g  17000 S  5.6 78.5   1:57.87 java
18459 dubbomm   20   0 7899764 2.905g  17000 S  4.0 78.5   4:29.63 java
18567 dubbomm   20   0 7899764 2.905g  17000 S  3.7 78.5   0:59.48 java
18599 dubbomm   20   0 7899764 2.905g  17000 S  3.0 78.5   0:33.66 java
18565 dubbomm   20   0 7899764 2.905g  17000 S  2.3 78.5   0:43.77 java
18527 dubbomm   20   0 7899764 2.905g  17000 S  1.7 78.5   1:41.33 java
18526 dubbomm   20   0 7899764 2.905g  17000 S  1.3 78.5   1:41.94 java
18528 dubbomm   20   0 7899764 2.905g  17000 S  1.3 78.5   1:42.72 java
18529 dubbomm   20   0 7899764 2.905g  17000 S  1.3 78.5   1:43.36 java
18530 dubbomm   20   0 7899764 2.905g  17000 S  1.3 78.5   1:40.87 java
```

换算进程ID：

```
[dubbomm@wecloud-test-docker logs]$ printf "%x\n" 18462
481e
```

##### 查看进程IO

```
[dubbomm@wecloud-test-docker logs]$ pidstat -d -p 18370 2 3
Linux 3.10.0-327.el7.x86_64 (wecloud-test-docker.novalocal) 	2018年05月15日 	_x86_64_	(4 CPU)

14时06分52秒   UID       PID   kB_rd/s   kB_wr/s kB_ccwr/s  Command
14时06分54秒  1001     18370      0.00  34846.00  21690.00  java
14时06分56秒  1001     18370      0.00  25274.00  15482.00  java
14时06分58秒  1001     18370      0.00  30350.00  21678.00  java
平均时间:  1001     18370      0.00  30156.67  19616.67  java
```

##### 查看进程启动时间

```
[dubbomm@wecloud-test-docker ~]$ ps -p 18370 -o start
 STARTED
20:00:25
[dubbomm@wecloud-test-docker ~]$ ps -p 18370 -o lstart
                 STARTED
Tue May 22 20:00:25 2018
```

##### 查看进程CPU信息

```
[dubbomm@wecloud-test-docker logs]$ pidstat -u -p 18370 2 3
Linux 3.10.0-327.el7.x86_64 (wecloud-test-docker.novalocal) 	2018年05月15日 	_x86_64_	(4 CPU)

14时08分14秒   UID       PID    %usr %system  %guest    %CPU   CPU  Command
14时08分16秒  1001     18370  100.00   12.50    0.00  100.00     0  java
14时08分18秒  1001     18370   77.00   22.50    0.00   99.50     0  java
14时08分20秒  1001     18370  100.00   24.50    0.00  100.00     0  java
平均时间:  1001     18370  100.00   19.83    0.00  100.00     -  java
```


##### 查看进程JVM信息

```
jinfo 18370 > jinfo.txt
```


##### jstat使用

jstat [option vmid [interval[s|ms] [count]]]

本地时vmid即为LVMID
远程时[protocol:][//]vmid[@hostname[:port]/servername]

主要选项
-class 
-gc
-gccapacity
-gcutil
-gccause
-gcnew
-gcnewcapacity
-gcold
-gcoldcapacity
-gcpermcapacity
-gcmetacapacity（JDK8+）
-compiler
-printcompilation



查看线程数：

```
[dubbomm@wecloud-test-docker logs]$ jstat -J-Djstat.showUnsupported=true -snap 18370 | grep java.threads
java.threads.daemon=140
java.threads.live=255
java.threads.livePeak=297
java.threads.started=883
```

查看GC信息：

```
[dubbomm@wecloud-test-docker logs]$ jstat -gc 18370
 S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
78592.0 78592.0 44561.3 11338.5 629248.0 629248.0 1310720.0   777084.7  89984.0 87162.0 10624.0 10063.0   1371   52.758   6      0.822   53.580

```

```
[dubbomm@wecloud-test-docker logs]$ jstat -gccause 18370
  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT    LGCC                 GCC
  0.00  15.71  74.79  61.49  97.01  94.86   1403   53.479     6    0.822   54.300 Allocation Failure   No GC
```

GC相关一些术语：

         S0C：年轻代中第一个survivor（幸存区）的容量 (字节)
         S1C：年轻代中第二个survivor（幸存区）的容量 (字节)
         S0U：年轻代中第一个survivor（幸存区）目前已使用空间 (字节)
         S1U：年轻代中第二个survivor（幸存区）目前已使用空间 (字节)
         EC：年轻代中Eden（伊甸园）的容量 (字节)
         EU：年轻代中Eden（伊甸园）目前已使用空间 (字节)
         OC：Old代的容量 (字节)
         OU：Old代目前已使用空间 (字节)
         PC：Perm(持久代)的容量 (字节)
         PU：Perm(持久代)目前已使用空间 (字节)
         YGC：从应用程序启动到采样时年轻代中gc次数
         YGCT：从应用程序启动到采样时年轻代中gc所用时间(s)
         FGC：从应用程序启动到采样时old代(全gc)gc次
         FGCT：从应用程序启动到采样时old代(全gc)gc所用时间(s)
         GCT：从应用程序启动到采样时gc用的总时间(s)
         NGCMN：年轻代(young)中初始化(最小)的大小 (字节)
         NGCMX：年轻代(young)的最大容量 (字节)
         NGC：年轻代(young)中当前的容量 (字节)
         OGCMN：old代中初始化(最小)的大小 (字节) 
         OGCMX：old代的最大容量 (字节)
         OGC：old代当前新生成的容量 (字节)
         PGCMN：perm代中初始化(最小)的大小 (字节) 
         PGCMX：perm代的最大容量 (字节)  
         PGC：perm代当前新生成的容量 (字节)
         S0：年轻代中第一个survivor（幸存区）已使用的占当前容量百分比
         S1：年轻代中第二个survivor（幸存区）已使用的占当前容量百分比
         E：年轻代中Eden（伊甸园）已使用的占当前容量百分比
         O：old代已使用的占当前容量百分比
         P：perm代已使用的占当前容量百分比
         S0CMX：年轻代中第一个survivor（幸存区）的最大容量 (字节)
         S1CMX ：年轻代中第二个survivor（幸存区）的最大容量 (字节)
         ECMX：年轻代中Eden（伊甸园）的最大容量 (字节)
         DSS：当前需要survivor（幸存区）的容量 (字节)（Eden区已满）
         TT：持有次数限制
         MTT ：最大持有次数限制

##### jmap使用
##### jmap使用

jmap [option] vmid
jmap -dump:[live,]format=b,file=<filename>

主要选项
-dump 
-finalizerinfo
-heap
-histo
-permstat
-F

```
jmap -histo:live  18370 | head -7
jmap -dump:format=b,file=heap.hprof 18370
jmap -dump:live,format=b,file=heap.hprof 18370
jmap -heap 18370

```


##### jstack使用

```
jstack -l 18370 > jstack.txt
```

##### 查看进程打开哪些文件

```
lsof -p 18370
```

##### IBM HeapAnalyze

```
java -Xmx4g -jar $software/ha456.jar
```


​         
​         
​         













