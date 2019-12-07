# README

## 简介
这是一个多人共享博客平台，实现的功能：

- 用户注册和登陆
- 用户发布，更新，删除博客
- 博客展示

这里提供了一个示例（有可能无法访问）：http://107.173.222.199:8080/

## 使用

clone项目到本地后

1. 连接数据库
   - 新建一个数据库，修改resources目录下的application.properties中数据库连接为你的数据库
   - 初始化数据库，这里使用flyway来管理数据库
     - 在pom.xml中修改flyway插件的url为你的数据库连接
     - 在终端输入mvn flyway:migrate进行数据库初始化操作
2. 点击运行，等待完成后浏览器打开localhost:8080即可使用

## 部署

修改好你的数据库地址后就可以部署了，这里提供两种部署方式

1. jar包模式

   在终端输入mvn  package将项目打包为jar包。

   在部署环境中输入java -jar 项目名.jar >>  日志存放的地址/console.log（日志名称） 2>&1 &

2. docker部署

   这需要你的机器上已经安装了docker。你可以编辑项目目录下的Dockerfile，在其中添加你需要的命令。

   这里已经提供了一个简单的配置，在终端输入 docker build ./ -t blog-springboot构建一个docker镜像，将其推送到你的私人仓库。

   在服务器上pull该镜像，使用docker run --name blog -p:8080:8080 blog-springboot即可完成。

   

