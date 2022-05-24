# Jacoco Cases

> everything in [JacocoDoc](https://www.jacoco.org/jacoco/trunk/doc/)

## About Jacoco

> JaCoCo is a free code coverage library for Java, which has been created by the EclEmma team based on the lessons learned from using and integration existing libraries for many years.
>
> ![Jacoco](https://www.jacoco.org/images/jacocoreport.png)

Jacoco **两种玩法**：offline 和 on-the-fly

## Play With Scripts

查看 Java 字节码的指令

```shell
javap -verbose {{TargetClassFile}}
```

使用 offline 模式指定 class 文件模拟插桩结果

```shell
java -jar jacoco-0.8.8/lib/jacococli.jar instrument {{TargetClassFile}} --dest out
```

javaagent 模式启动项目 Jar 包

```
java -jar -javaagent:jacoco-0.8.8/lib/jacocoant.jar=destfile=result.exec {{ProjectJar}}
```

使用 jacococli 解析执行结果

```
java -jar jacoco-0.8.8/lib/jacococli.jar report result.exec --classfiles {{PathToPrjectTargetClass}} --sourcefiles {{PathToProjectSource}} --html html
```

## A Tour of Jacoco

使用: JacocoPets

### 普通情况下的应用发布

1. 打包 `cd JacocoPets;mvn package;cd ..`
2. 启动 `java -jar JacocoPets/target/JacocoPets-0.0.1-SNAPSHOT.jar`
3. 对外提供 web 服务

```
curl --location --request GET 'localhost:8080/pet/search/TryBySound?sound=miao';
curl --location --request GET 'localhost:8080/pet/search/TryBySound?sound=wang';
```

### Jacoco on-the-fly

1. 打包 `cd JacocoPets;mvn package;cd ..`
2. 带 agent 参数启动 `java -jar -javaagent:jacoco-0.8.8/lib/jacocoagent.jar=destfile=result.exec JacocoPets/target/JacocoPets-0.0.1-SNAPSHOT.jar`
3. 两个测试用例

```
curl --location --request GET 'localhost:8080/pet/search/TryBySound?sound=miao';
curl --location --request GET 'localhost:8080/pet/search/TryBySound?sound=wang';
```

4. 停止应用 `ctrl + c`, 覆盖率执行结果 `result.exec`
5. 直接使用 jacococli 解析覆盖率结果:
   - classfiles 指定 class 文件
   - sourcefiles 指定源码文件
   - html 以网页的形式导出结果

```shell
java -jar jacoco-0.8.8/lib/jacococli.jar report result.exec --classfiles JacocoPets/target/classes --sourcefiles JacocoPets/src/main/java --html html
```

## From Something strange

1# `com.maoge.jacocopets.flow#CtrlIfCase3`的`exec`方法的分支数是 6，可我明明只有一个 if!!!

```java
package com.maoge.jacocopets.flow;

import org.springframework.stereotype.Component;

@Component
public class CtrlIfCase3 {
    public int exec(int input){
        int result = 0;
        if(input==0 || input==1 || input==2){
            result = 3;
        }
        return result;
    }
}
```

2# `com.maoge.jacocopets.data#Pet`的分支数是 22，但我压根没分支啊！！！

```java
package com.maoge.jacocopets.data;

import lombok.Data;

@Data
public class Pet {
    private Long id;
    private String name;
}
```

Q: 为什么会这样？

A: 因为 Jacoco 是一个基于字节码工具 ASM 实现插桩统计程序执行情况的工具。

### 朴素的想法

源代码视角下，在每一条语句后面插入一条语句 executed()，每次 executed 执行的时候就会记录下来，后续程序到底执行多少代码最终可以通过 executed 的汇总信息统计出来。

```
boolean[] executedLine;

public void executed(int lineNo){
    executedLine[lineNo] = true;
}

public void sum3(int[] array){
    int line = 0;
    int a = array[0];
    executed(line++);
    int b = array[1];
    executed(line++);
    int c = array[2];
    executed(line++);
    return a + b + c;
}
```

当然，Jacoco 是基于字节码的。但，类似的事情是能够做到的！

### 从例子看 Jacoco 插桩

#### 例子

使用 jacococli 对`com.maoge.jacocopets.flow#CtrlIfCase3`做离线插桩

```
java -jar jacoco-0.8.8/lib/jacococli.jar instrument JacocoPets/target/classes/com/maoge/jacocopets/flow/CtrlIfCase3.class --dest out
```

打开 class 文件

```
$ javap -verbose out/CtrlIfCase3.class
Classfile /home/maoge/JavaSpace/JacocoCases/out/CtrlIfCase3.class
  Last modified May 24, 2022; size 892 bytes
  MD5 checksum b3fdb00c4dad0c092cee36a36d43bc27
  Compiled from "CtrlIfCase3.java"
public class com.maoge.jacocopets.flow.CtrlIfCase3
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #2                          // com/maoge/jacocopets/flow/CtrlIfCase3
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 1, methods: 3, attributes: 2
Constant pool:
   #1 = Methodref          #3.#22         // java/lang/Object."<init>":()V
   #2 = Class              #23            // com/maoge/jacocopets/flow/CtrlIfCase3
   #3 = Class              #24            // java/lang/Object
   #4 = Utf8               <init>
   #5 = Utf8               ()V
   #6 = Utf8               Code
   #7 = Utf8               LineNumberTable
   #8 = Utf8               LocalVariableTable
   #9 = Utf8               this
  #10 = Utf8               Lcom/maoge/jacocopets/flow/CtrlIfCase3;
  #11 = Utf8               exec
  #12 = Utf8               (I)I
  #13 = Utf8               input
  #14 = Utf8               I
  #15 = Utf8               result
  #16 = Utf8               StackMapTable
  #17 = Utf8               MethodParameters
  #18 = Utf8               SourceFile
  #19 = Utf8               CtrlIfCase3.java
  #20 = Utf8               RuntimeVisibleAnnotations
  #21 = Utf8               Lorg/springframework/stereotype/Component;
  #22 = NameAndType        #4:#5          // "<init>":()V
  #23 = Utf8               com/maoge/jacocopets/flow/CtrlIfCase3
  #24 = Utf8               java/lang/Object
  #25 = Utf8               $jacocoInit
  #26 = Utf8               ()[Z
  #27 = NameAndType        #25:#26        // $jacocoInit:()[Z
  #28 = Methodref          #2.#27         // com/maoge/jacocopets/flow/CtrlIfCase3.$jacocoInit:()[Z
  #29 = Utf8               [Z
  #30 = Class              #29            // "[Z"
  #31 = Utf8               $jacocoData
  #32 = NameAndType        #31:#29        // $jacocoData:[Z
  #33 = Fieldref           #2.#32         // com/maoge/jacocopets/flow/CtrlIfCase3.$jacocoData:[Z
  #34 = Long               5587099010594019281l
  #36 = String             #23            // com/maoge/jacocopets/flow/CtrlIfCase3
  #37 = Utf8               org/jacoco/agent/rt/internal_b6258fc/Offline
  #38 = Class              #37            // org/jacoco/agent/rt/internal_b6258fc/Offline
  #39 = Utf8               getProbes
  #40 = Utf8               (JLjava/lang/String;I)[Z
  #41 = NameAndType        #39:#40        // getProbes:(JLjava/lang/String;I)[Z
  #42 = Methodref          #38.#41        // org/jacoco/agent/rt/internal_b6258fc/Offline.getProbes:(JLjava/lang/String;I)[Z
{
  public com.maoge.jacocopets.flow.CtrlIfCase3();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=4, locals=2, args_size=1
         0: invokestatic  #28                 // Method $jacocoInit:()[Z
         3: astore_1
         4: aload_0
         5: invokespecial #1                  // Method java/lang/Object."<init>":()V
         8: aload_1
         9: iconst_0
        10: iconst_1
        11: bastore
        12: return
      LineNumberTable:
        line 6: 4
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      13     0  this   Lcom/maoge/jacocopets/flow/CtrlIfCase3;

  public int exec(int);
    descriptor: (I)I
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=5, locals=4, args_size=2
         0: invokestatic  #28                 // Method $jacocoInit:()[Z
         3: astore_2
         4: iconst_0
         5: istore_3
         6: iload_1
         7: ifne          17
        10: aload_2
        11: iconst_1
        12: iconst_1
        13: bastore
        14: goto          45
        17: iload_1
        18: iconst_1
        19: if_icmpne     29
        22: aload_2
        23: iconst_2
        24: iconst_1
        25: bastore
        26: goto          45
        29: iload_1
        30: iconst_2
        31: if_icmpeq     41
        34: aload_2
        35: iconst_3
        36: iconst_1
        37: bastore
        38: goto          51
        41: aload_2
        42: iconst_4
        43: iconst_1
        44: bastore
        45: iconst_3
        46: istore_3
        47: aload_2
        48: iconst_5
        49: iconst_1
        50: bastore
        51: iload_3
        52: aload_2
        53: bipush        6
        55: iconst_1
        56: bastore
        57: ireturn
      StackMapTable: number_of_entries = 5
        frame_type = 253 /* append */
          offset_delta = 17
          locals = [ class "[Z", int ]
        frame_type = 11 /* same */
        frame_type = 11 /* same */
        frame_type = 3 /* same */
        frame_type = 5 /* same */
      LineNumberTable:
        line 8: 4
        line 9: 6
        line 10: 45
        line 12: 51
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      58     0  this   Lcom/maoge/jacocopets/flow/CtrlIfCase3;
            0      58     1 input   I
            6      52     3 result   I
    MethodParameters:
      Name                           Flags
      input
}
SourceFile: "CtrlIfCase3.java"
RuntimeVisibleAnnotations:
  0: #21()
```

对比没有插桩之前的 class 文件

```
$javap -verbose JacocoPets/target/classes/com/maoge/jacocopets/flow/CtrlIfCase3.class
Classfile /home/maoge/JavaSpace/JacocoCases/JacocoPets/target/classes/com/maoge/jacocopets/flow/CtrlIfCase3.class
  Last modified May 24, 2022; size 597 bytes
  MD5 checksum f21b72eeb3f0ab806df217b7f804b660
  Compiled from "CtrlIfCase3.java"
public class com.maoge.jacocopets.flow.CtrlIfCase3
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #2                          // com/maoge/jacocopets/flow/CtrlIfCase3
  super_class: #3                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 2, attributes: 2
Constant pool:
   #1 = Methodref          #3.#22         // java/lang/Object."<init>":()V
   #2 = Class              #23            // com/maoge/jacocopets/flow/CtrlIfCase3
   #3 = Class              #24            // java/lang/Object
   #4 = Utf8               <init>
   #5 = Utf8               ()V
   #6 = Utf8               Code
   #7 = Utf8               LineNumberTable
   #8 = Utf8               LocalVariableTable
   #9 = Utf8               this
  #10 = Utf8               Lcom/maoge/jacocopets/flow/CtrlIfCase3;
  #11 = Utf8               exec
  #12 = Utf8               (I)I
  #13 = Utf8               input
  #14 = Utf8               I
  #15 = Utf8               result
  #16 = Utf8               StackMapTable
  #17 = Utf8               MethodParameters
  #18 = Utf8               SourceFile
  #19 = Utf8               CtrlIfCase3.java
  #20 = Utf8               RuntimeVisibleAnnotations
  #21 = Utf8               Lorg/springframework/stereotype/Component;
  #22 = NameAndType        #4:#5          // "<init>":()V
  #23 = Utf8               com/maoge/jacocopets/flow/CtrlIfCase3
  #24 = Utf8               java/lang/Object
{
  public com.maoge.jacocopets.flow.CtrlIfCase3();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 6: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/maoge/jacocopets/flow/CtrlIfCase3;

  public int exec(int);
    descriptor: (I)I
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=3, args_size=2
         0: iconst_0
         1: istore_2
         2: iload_1
         3: ifeq          16
         6: iload_1
         7: iconst_1
         8: if_icmpeq     16
        11: iload_1
        12: iconst_2
        13: if_icmpne     18
        16: iconst_3
        17: istore_2
        18: iload_2
        19: ireturn
      LineNumberTable:
        line 8: 0
        line 9: 2
        line 10: 16
        line 12: 18
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      20     0  this   Lcom/maoge/jacocopets/flow/CtrlIfCase3;
            0      20     1 input   I
            2      18     2 result   I
      StackMapTable: number_of_entries = 2
        frame_type = 252 /* append */
          offset_delta = 16
          locals = [ int ]
        frame_type = 1 /* same */
    MethodParameters:
      Name                           Flags
      input
}
SourceFile: "CtrlIfCase3.java"
RuntimeVisibleAnnotations:
  0: #21()
    org.springframework.stereotype.Component
```

#### 火眼金睛

#### 1# 文件大小、MD5、文件修改时间啥的就不细说了

#### 2# class interfaces

before

```
interfaces: 0, fields: 0, methods: 2, attributes: 2
```

after

```
interfaces: 0, fields: 1, methods: 3, attributes: 2
```

#### 3# constant pool (>24#)

NEW ADDED

```
  #25 = Utf8               $jacocoInit
  #26 = Utf8               ()[Z
  #27 = NameAndType        #25:#26        // $jacocoInit:()[Z
  #28 = Methodref          #2.#27         // com/maoge/jacocopets/flow/CtrlIfCase3.$jacocoInit:()[Z
  #29 = Utf8               [Z
  #30 = Class              #29            // "[Z"
  #31 = Utf8               $jacocoData
  #32 = NameAndType        #31:#29        // $jacocoData:[Z
  #33 = Fieldref           #2.#32         // com/maoge/jacocopets/flow/CtrlIfCase3.$jacocoData:[Z
  #34 = Long               5587099010594019281l
  #36 = String             #23            // com/maoge/jacocopets/flow/CtrlIfCase3
  #37 = Utf8               org/jacoco/agent/rt/internal_b6258fc/Offline
  #38 = Class              #37            // org/jacoco/agent/rt/internal_b6258fc/Offline
  #39 = Utf8               getProbes
  #40 = Utf8               (JLjava/lang/String;I)[Z
  #41 = NameAndType        #39:#40        // getProbes:(JLjava/lang/String;I)[Z
  #42 = Methodref          #38.#41        //
```

#### 4# public int exec(int);

1. 由原本的 19 行增加到了 57 行

```
 0: invokestatic  #28                 // Method $jacocoInit:()[Z
 3: astore_2
...
10: aload_2
11: iconst_1
12: iconst_1
13: bastore
...
22: aload_2
23: iconst_2
24: iconst_1
25: bastore
...
34: aload_2
35: iconst_3
36: iconst_1
37: bastore
    ...
```

2. if 反转了

before

```
 2: iload_1
 3: ifeq          16
 6: iload_1
 7: iconst_1
 8: if_icmpeq     16
11: iload_1
12: iconst_2
13: if_icmpne     18
16: iconst_3
17: istore_2
18: iload_2
```

after

```
 6: iload_1
 7: ifne          17
14: goto          45
17: iload_1
18: iconst_1
19: if_icmpne     29
26: goto          45
29: iload_1
30: iconst_2
31: if_icmpeq     41
38: goto          51
41: aload_2
42: iconst_4
43: iconst_1
44: bastore
45: iconst_3
46: istore_3
51: iload_3
52: aload_2
53: bipush        6
55: iconst_1
56: bastore
57: ireturn
```

一个发现，修改后的 exec 方法有六个分支符号：

- ifne
- goto
- if_icmpne
- goto
- if_icmpeq
- goto

### 回到权威 (i.e. [官方文档](https://www.jacoco.org/jacoco/trunk/doc/flow.html))

这里我只简单总结一下

- 不需要每一行指令后面都跟着一条 probe，对于没有分支的指令序列，只要知道最后一条执行过，就可以向前返推
- 分支跳转分为两种`GOTO`和其他`ifXXX`，两种有各自的插桩策略
- TryCatch 体中的代码存在隐式异常跳转（并非分支跳转），所以所有可能抛出异常的两个代码行之间都会加探针

加一点佐证

```java
@Override
public void visitJumpInsnWithProbe(final int opcode, final Label label,
        final int probeId, final IFrame frame) {
    // goto 前插
    if (opcode == Opcodes.GOTO) {
        probeInserter.insertProbe(probeId);
        mv.visitJumpInsn(Opcodes.GOTO, label);
    }
    // 其他情况反转IF
    else {
        final Label intermediate = new Label();
        mv.visitJumpInsn(getInverted(opcode), intermediate);
        probeInserter.insertProbe(probeId);
        mv.visitJumpInsn(Opcodes.GOTO, label);
        mv.visitLabel(intermediate);
        frame.accept(mv);
    }
}
```

## 一点思考

最后还是主要讨论一下分支覆盖率这件事。

Jacoco 对于分支的统计有一点点的反直觉。

```
if (...) {
    // ...
}else{
    // ...
}
```

一般情况下我们会认为,上面这段代码的分支数是 2，要么 if，要么 else。这种认知是非常符合直觉的，但其实上面这段代码的分支在 Jacoco 这里根本没办法统计，因为 if 里面的判断条件是空的！而 Jacoco 对于分支的最终计算与 If 中的判断条件强相关。

所以对于分支覆盖率，我们的关注点应该更加细致。
