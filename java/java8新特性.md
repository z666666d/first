#一、Java语言的新特性

##1. Lambda表达式和函数式接口

###1.1Lambda表达式语法

```text
Lambda表达式完整写法：
(Type1 param1, Type2 param2, ..., TypeN paramN) -> {
  statment1;
  statment2;
  //.............
  return statmentM;
}

Lambda表达式格式：
（parameters） -> expression 或 （parameters） -> {expression}
注：
	1.当参数parameter只有一个是，括号可以省略
	2.expression只有一行可以不用花括号，多行代码用花括号括起来
	3.parameters的参数类型可以省略，java8会进行自动类型判断
```

###1.2 Lambda表达式和内部类代码比较

1.Lambda表达式实现

```java
public class test {

	public static void main(String[] args) {
		Demo demo = (a, b) -> System.out.println("参数1:" + a + "参数2" + b);
		demo.operation("aaa", "bbb");
	}
}

// 必须为函数式接口
interface Demo {
	void operation(String str, String str2);
}
```



2.内部类实现

```java
public class test {

	public static void main(String[] args) {
		Demo demo = new Demo() {

			@Override
			public void operation(String str, String str2) {
				System.out.println("参数1:" + str + "参数2" + str2);
			}
		};

		demo.operation("aaa", "bbb");
	}
}

interface Demo {
	void operation(String str, String str2);
}
```

对比可以看出，Lambda表达式代码比内部类代码简洁很多，可读性也更高



###1.3 Lambda表达式简化

1.在绝大多数情况下，编译器可以从上下文中推断出Lambda表达式的参数类型，所以参数类型可以省略：

```text
(param1, param2, ..., paramN) -> {
  statment1;
  statment2;
  //.............
  return statmentM;
}
```

2.当Lambda表达式只有一个参数时，小括号可以省略：

```text
param1 -> {
  statment1;
  statment2;
  //.............
  return statmentM;
}
```

3.当Lambda表达式只有一条语句时，可以省略大括号、return和语句结尾的分号：

```text
param -> statment
```

4.使用Method Reference

当Lambda表达式的语句仅仅是调用一个已有方法的时候，可以使用Method Reference来进一步简化代码



###1.4 Lambda表达式访问变量

示例代码：

```java
String waibu = "lambda :";
List<String> proStrs = Arrays.asList(new String[]{"Ni","Hao","Lambda"});
List<String>execStrs = proStrs.stream().map(chuandi -> {
  Long zidingyi = System.currentTimeMillis();
  return waibu + chuandi + " -----:" + zidingyi;
}).collect(Collectors.toList());
execStrs.forEach(System.out::println);
```

对于上面这段代码的Lambda表达式来说：

waibu---外部的变量			chuandi---传递的变量			zidingyi---内部定义的变量

对于Lambda表达式来说，这三类变量都能够访问到。但是对于外部变量，在Lambda表达式中是不可变的，默认为final类型。如果在Lambda表达式中试图修改外部变量，那么在编译时会报错编译失败。在Java内部类中，引用外部变量必须用final修饰，Java8对这个限制做了优化，可以不用显示的用final修饰，但是编译器隐式的将其作为final变量处理。Lambda表达式的本质是一个语法糖，所以与内部类一样，引用的外部变量默认作为final处理。



###1.5 函数式接口

函数式接口：指只有一个函数的接口。

函数式接口概念的产生是为了让现有的功能与Lambda表达式良好的兼容，这样的接口可以隐式的转化为Lambda表达式。

但是函数式接口非常脆弱，只要在接口中多添加一个函数，该接口就不再是函数式接口从而导致编译失败。为了克服这种代码层面的脆弱性，Java8中提供了**@FunctionalInterface** 注解，用于显示的说明某个接口是函数式接口。

函数式接口示例：

```java
@FunctionalInterface
public interface Functional{
    void method();
}
```

**注意**：默认方法和静态方法不会破坏函数式接口的定义

### 1.6 Lambda表达式的this关键字

this关键字在Lambda表达式中指的是声明它的外部对象。



##2 接口的默认方法和静态方法

###2.1 default关键字修饰接口方法

在Java8中允许使用default关键字修饰接口中的默认方法，默认方法与抽象方法的区别在于默认方法不强制实现类必须实现，但是可以被实现类继承和重写。默认方法的出现提供了在不破坏接口的兼容性的前提下对接口进行扩展。

```java
private interface Defaulable{    
	//接口中的default方法，实现类可以不用实现
    default String notRequired(){
    	return "Default implementation";
    }     
}

//不实现Defaulable接口的默认方法
private static class DefaultableImpl implements Defaulable{
}

//重写Defaulable接口的默认方法
private static class OverridableImpl implements Defaulable{
    @Override
    public String notRequired(){
        return "Overridden implementation";
    }
}
```

由于JVM上的默认方法的实现在字节码层面提供了支持，因此效率非常高。默认方法允许在不打破现有继承体系的基础上改进接口。尽管默认方法有这么多好处，但在实际开发中应该谨慎使用：在复杂的继承体系中，默认方法可能引起歧义和编译错误。

###2.2 接口中定义静态方法

Java8可以在接口中定义静态方法，接口中的静态方法与一般Java类中的静态方法一样。



##3 方法引用

###3.1 什么是方法引用

方法引用是用来直接访问类或者实例的已经存在的方法或者构造方法。方法引用提供了一种引用而不执行方法的方式，本质就是Lambda表达式的一个语法糖，唯一的用途就是用来简写Lambda表达式。当Lambda表达式仅仅调用一个已经存在的方法时，就可以用方法引用来简写。

```java
//Lambda表达式仅调用一个已存在的方法
Arrays.sort(stringsArray,(s1,s2)->s1.compareToIgnoreCase(s2));

//使用方法引用简写
Arrays.sort(stringsArray, String::compareToIgnoreCase);
```

由于Lambda表达式仅仅调用一个已存在的方法，而不做任何其它事。这时通过一个方法名字来引用这个方法看起来更加清晰。方法引用就是一个更加紧凑，易读的Lambda表达式。

###3.2 四种方法引用

方法引用的标准形式为：

```text
类名::方法名
```

方法引用主要分为以下四类：

| 类型               | 示例                                   |
| ---------------- | ------------------------------------ |
| 引用静态方法           | ContainingClass::staticMethodName    |
| 引用某个对象的实例方法      | containingObject::instanceMethodName |
| 引用某个类型的任意对象的实例方法 | ContainingType::methodName           |
| 引用构造方法           | ClassName::new                       |

####3.2.1 静态方法引用

组成语法格式：ClassName::staticMethodName

- 静态方法引用比较容易理解，和静态方法调用相比，只是把 **. **换为 **::**
- 在目标类型兼容的任何地方，都可以使用静态方法引用。

例子：

　　String::valueOf   等价于lambda表达式 (s) -> String.valueOf(s)

　　Math::pow       等价于lambda表达式  (x, y) -> Math.pow(x, y);



####3.2.2 特定实例对象的方法引用

这种方法引用与静态方法引用类似，只不过这里使用对象引用而不是类名。实例方法引用又分为以下三类：



**a**.实例上的实例方法引用

组成语法格式：instanceReference::methodName



**b**.超类上的实例方法引用

组成语法格式：super::methodName

方法的名称由methodName指定，通过使用**super**，可以引用方法的超类版本。

还可以捕获this 指针，this :: equals  等价于lambda表达式  x -> this.equals(x);



**c**.类型上的实例方法引用

组成语法格式：ClassName::methodName

注意：若类型的实例方法是泛型的，就需要在::分隔符前提供类型参数，或者（多数情况下）利用目标类型推导出其类型。

静态方法引用和类型上的实例方法引用拥有一样的语法。编译器会根据实际情况做出决定。一般我们不需要指定方法引用中的参数类型，因为编译器往往可以推导出结果，但如果需要我们也可以显式在::分隔符之前提供参数类型信息。

例子：String::toString 等价于lambda表达式 (s) -> s.toString()



####3.2.3 任意对象（属于同一个类）的实例方法引用

组成语法格式： ContainingType::methodName

例子：

```java
String[] stringArray = { "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda" };
Arrays.sort(stringArray, String::compareToIgnoreCase);
```



####3.2.4 构造方法引用

构造方法引用又分构造方法引用和数组构造方法引用。



**a**.构造方法引用

组成语法格式： class::new

构造函数本质上是静态方法，只是方法名字比较特殊，使用的是**new 关键字**。

例子：String::new， 等价于lambda表达式 () -> new String() 



b.数组构造方法引用

组成语法格式：TypeName[]**::**new



##4 重复注解

从Java1.5引入注解以来，注解已经变得非常流行并被广泛使用，但是注解的使用有一个很大的限制：在同一个地方不能多次使用同一个注解。

在Java8以前，为了解决这一问题，是通过另外一个注解来储存重复注解实现的。

```java
public @interface Authority {
     String role();
}

public @interface Authorities {
    Authority[] value();
}

public class RepeatAnnotationUseOldVersion {
    //用Authorities储存重复的Authority注解。虽然可以实现，但可读性低
    @Authorities({@Authority(role="Admin"),@Authority(role="Manager")})
    public void doSomeThing(){
    }
}
```

Java8中增加了**@Repeatable**注解来定义重复注解，这个注解仅仅只是在编译期间有效，底层的技术仍然相同。

```java
//使用@Repeatable注解将需要重复的@Authority注解指向了存储注解@Authorities
//这样@Authority就可以重复使用，这样的写法更适合常规的思维，可读性强一点
@Repeatable(Authorities.class)
public @interface Authority {
     String role();
}

public @interface Authorities {
    Authority[] value();
}

public class RepeatAnnotationUseNewVersion {
    @Authority(role="Admin")
    @Authority(role="Manager")
    public void doSomeThing(){ }
}
```



##5 类型注解

###5.1 是什么

在Java8之前，注解只可以用于类、方法、属性等上面，Java8拓宽了注解的应用场景，现在注解几乎可以使用在任何元素上。

如：

```text
创建类实例
        new @Interned MyObject();
类型映射
       myString = (@NonNull String) str;
implements 语句中
        class UnmodifiableList<T> implements @Readonly List<@Readonly T> { ... }
throw exception声明
        void monitorTemperature() throws @Critical TemperatureException { ... }
```

需要注意的是，类型注解只是语法而不是语义，并不会影响java的编译时间，加载时间，以及运行时间，也就是说，编译成class文件的时候并不包含类型注解。

###5.2 作用

如果出现如下代码：

```java
Collections.emptyList().add("One");
int i=Integer.parseInt("hello");
System.console().readLine();
```

代码编译是可以通过的，但运行是会分别报UnsupportedOperationException； NumberFormatException；NullPointerException异常，这些都是runtime error；类型注解被用来支持在Java的程序中做强类型检查。配合插件式的check framework，可以在编译的时候检测出runtime error，以提高代码质量。这就是类型注解的作用。

###5.3 check framework

check framework是第三方工具，配合Java的类型注解效果就是1+1>2。它可以嵌入到javac编译器里面，可以配合ant和maven使用，也可以作为eclipse插件。地址是http://types.cs.washington.edu/checker-framework/。
check framework可以找到类型注解出现的地方并检查，举个简单的例子：

```java
import checkers.nullness.quals.*;
public class GetStarted {
    void sample() {
        @NonNull Object ref = new Object();
    }
}
```

使用javac编译上面的类 ：

```text
javac -processor checkers.nullness.NullnessChecker GetStarted.java
```

编译是通过，但如果修改成：

```java
import checkers.nullness.quals.*;
public class GetStarted {
    void sample() {
        @NonNull Object ref = null;
    }
}
```

再次编译，则出现 :

```text
GetStarted.java:5: incompatible types.
found   : @Nullable <nulltype>
required: @NonNull Object
        @NonNull Object ref = null;
                              ^
1 error
```

如果你不想使用类型注解检测出来错误，则不需要processor，直接javac GetStarted.java是可以编译通过的，这是在[java 8 with Type Annotation Support](https://jdk8.java.net/type-annotations/)版本里面可以，但java 5,6,7版本都不行，因为javac编译器不知道@NonNull是什么东西，但check framework 有个向下兼容的解决方案，就是将类型注解nonnull用/**/注释起来

比如上面例子修改为:

```java
import checkers.nullness.quals.*;
public class GetStarted {
    void sample() {
        /*@NonNull*/ Object ref = null;
    }
}
```

这样javac编译器就会忽略掉注释块，但用check framework里面的javac编译器同样能够检测出nonnull错误。
通过类型注解+check framework我们可以看到，现在runtime error可以在编译时候就能找到。

###5.4 关于JSR 308

JSR 308想要解决在Java 1.5注解中出现的两个问题：

- 在句法上对注解的限制：只能把注解写在声明的地方 
- 类型系统在语义上的限制：类型系统还做不到预防所有的bug 

JSR 308 通过如下方法解决上述两个问题： 

- 对Java语言的句法进行扩充，允许注解出现在更多的位置上。包括：方法接收器（method receivers，译注：例public int size() @Readonly { ... }），泛型参数，数组，类型转换，类型测试，对象创建，类型参数绑定，类继承和throws子句。其实就是类型注解，现在是java 8的一个特性
- 通过引入可插拔的类型系统（pluggable type systems）能够创建功能更强大的注解处理器。类型检查器对带有类型限定注解的源码进行分析，一旦发现不匹配等错误之处就会产生警告信息。其实就是check framework



对JSR308，有人反对，觉得更复杂更静态了，比如 
@NotEmpty List<@NonNull String> strings = new ArrayList<@NonNull String>()> 
换成动态语言为 
var strings = ["one", "two"]; 

有人赞成，说到底，代码才是“最根本”的文档。代码中包含的注解清楚表明了代码编写者的意图。当没有及时更新或者有遗漏的时候，恰恰是注解中包含的意图信息，最容易在其他文档中被丢失。而且将运行时的错误转到编译阶段，不但可以加速开发进程，还可以节省测试时检查bug的时间。



##6 泛化目标类型推断

###6.1 泛型的缺点

泛型是Java1.5引入的新特性，作用于编译阶段，泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数。这种类型变量可以用在类、接口和方法的创建中。当然最常见的就是用在集合中。

泛型的最大优点是提供了程序的类型安全同时可以向后兼容，但尴尬的是每次定义时都要写明泛型的类型，这样在显示指定上不仅感觉有些冗长，最主要是很多程序员不熟悉泛型，因此很多时候不能够给出正确的类型参数。

现在通过编译器自动推断泛型的参数类型，能够减少这样的情况，并提高代码可读性。

###6.2 Java7的泛型类型推断改进

在以前的版本中使用泛型类型，需要在声明并赋值的时候，两侧都加上泛型类型。

如：

```java
Map<String, String> myMap = new HashMap<String, String>();
```

而在Java7中这种方式得到了改进，可以使用如下语句进行声明并赋值：

```java
Map<String, String> myMap = new HashMap<>(); 
```

可以省略等号后面的泛型，编译器会根据变量声明时的泛型类型自动推断出实例化HashMap时的泛型类型。

一定要注意new HashMap后面的“<>”，只有加上这个“<>”才表示是自动类型推断，否则就是非泛型类型的HashMap，并且在使用编译器编译源代码时会给出一个警告提示。但是，Java SE 7在创建泛型实例时的类型推断是有限制的；只有构造器的参数化类型在上下文中被显著的声明了，才可以使用类型推断，否则不行。

如下面的例子在java 7无法正确编译：

```java

List<String> list = new ArrayList<>();   
list.add("A");// 由于addAll期望获得Collection<? extends String>类型的参数，因此下面的语句无法通过   
list.addAll(new ArrayList<>());
```

但现在在java8里面可以编译，因为根据方法参数来自动推断泛型的类型

###6.3 Java8的泛型目标类型推断改进

java8里面泛型的目标类型推断主要有2个方面：

1）支持通过方法上下文推断泛型目标类型

2）支持在方法调用链路当中，泛型类型推断传递到最后一个方法

例子：

```java
class List<E> {   
   static <Z> List<Z> nil() { ... };   
   static <Z> List<Z> cons(Z head, List<Z> tail) { ... };   
   E head() { ... }   
}
```

在调用上面方法的时候可以这样写：

```java
//通过方法赋值的目标参数来自动推断泛型的类型   
List<String> l = List.nil();   
//而不是显示的指定类型   
//List<String> l = List.<String>nil();   
//通过前面方法参数类型推断泛型的类型   
List.cons(42, List.nil());   
//而不是显示的指定类型   
//List.cons(42, List.<Integer>nil());
```

Java作为静态语言的代表者，可以说类型系统相当丰富。导致类型间互相转换的问题困扰着每个java程序员，通过编译器自动推断类型的东西可以稍微缓解一下类型转换太复杂的问题。其实泛型目标类型推断改进主要是在使用Lambda表达式时用来推断合法的Lambda表达式的类型的上下文 。

##7 获取参数名称

为了在运行时获得Java程序中方法的参数名称，老一辈的Java程序员必须使用不同方法，例如Paranamer liberary。Java 8终于将这个特性规范化，在语言层面（使用反射API和Parameter.getName()方法）和字节码层面（使用新的javac编译器以及-parameters参数）提供支持。

```java
package com.javacodegeeks.java8.parameter.names;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterNames {
    public static void main(String[] args) throws Exception {
        Method method = ParameterNames.class.getMethod( "main", String[].class );
        for( final Parameter parameter: method.getParameters() ) {
            System.out.println( "Parameter: " + parameter.getName() );
        }
    }
}
```

在Java 8中这个特性是默认关闭的，因此如果不带-parameters参数编译上述代码并运行，则会输出如下结果：

Parameter: arg0

如果带-parameters参数，则会输出如下结果（正确的结果）：

Parameter: args

如果你使用Maven进行项目管理，则可以在maven-compiler-plugin编译器的配置项中配置-parameters参数：

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.1</version>
    <configuration>
        <compilerArgument>-parameters</compilerArgument>
        <source>1.8</source>
        <target>1.8</target>
    </configuration>
</plugin>
```

