# springBootDemo（希望大家坚持哦）
技术点整合

## 开发环境

- IDE：IntelliJ IDEA
- JDK：1.8
- Spring Boot：2.2.6.RELEASE

1.创建springboot项目

    * 参考：https://blog.csdn.net/dkbnull/article/details/81806983
 
 关于项目基本信息：
 
    *groupId一般分为多个段，一般第一段为域，第二段为公司名称。例如apachegroupId是org.apache，它的域是org，公司名称是apache，
    *artifactId是tomcat。
    *package：groupId.artifactId打头的。
 
 关于注解：
 
    *@SpringBootApplication注解作用是开启Spring Boot的自动配置；
    *@SpringBootConfiguration注解就是@Configuration注解，表示该类是一个配置类；
    *@EnableAutoConfiguration注解作用是让Spring Boot根据jar包依赖为当前项目进行自动配置；
    *@ComponentScan注解的作用是告诉Spring自动扫描并且装入bean容器。
 
 关于配置：
 
      Spring Boot使用一个全局的配置文件application.properties或者application.yml，放在src/main/resources目录下
    
2.创建多模块项目：

      * 参考：https://www.cnblogs.com/MaxElephant/p/8205234.html   
  
  多模块项目配置：父pom是为了抽取统一的配置信息和依赖版本控制，方便子pom直接引用，简化子pom的配置。
  
      1、父模块的打包类型
        *多模块项目中，父模块打包类型必须是pom，同时以给出所有的子模块，其中每个module，都是另外一个maven项目。
      
      2、继承设置
        *继承是maven中很强大的一种功能，继承可以使子pom获得parent中的各项配置，对子pom进行统一的配置和依赖管理。父pom中的大多数元素都能被子pom继承。
      
      3、使用dependencyManagement管理依赖版本号
        *最顶层的父pom中使用该元素，让所有子模块引用一个依赖而不用显式的列出版本号。maven会沿着父子层次向上走，直到找到一个拥有dependencyManagement元素的项目，然后它就会使用在这个dependencyManagement元素中指定的版本号。
      
      4、使用properties控制依赖包的版本号，便于版本维护
        *在properties标签中，添加各依赖包的版本号，然后在dependency中直接引用该依赖版本号的值即可。
        
      5、关于exclusions标签
        *最常见的就是版本冲突：需要使用exclusions元素。
          通过mvn dependency:tree命令查看依赖树，点击IDEA右侧的Maven Projects，在每个模块的Dependencies中即可查看每个dependency内部的依赖及版本号，从来识别哪些依赖需要被排除掉。
      
    




















