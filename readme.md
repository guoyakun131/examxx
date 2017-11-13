

# [Exam++](http://www.examxx.net)
# Exam++已发布第二版并正式更名为Examstack，详情请进入[官网](http://www.examstack.com)查看

- [Exam++](http://www.examxx.net)是国内首款基于`JAVA`与`MYSQL`开发的网络考试系统。它可以稳定、顺畅的运行在`Windows`与`Linux`平台上。您可以通过它快捷方便的创建试题和题库，发布试卷，组织考试，系统自动批改。高度的可配置性和灵活性使得它可以被应用于很多领域。

- 软件采用`GPL`协议，完全开放且免费，并且有固定的开发团队提供技术支持
[官方网站](http://www.examxx.net)
![](http://static.oschina.net/uploads/space/2015/0129/193211_RwoW_135389.png)

![](http://static.oschina.net/uploads/space/2015/0112/104147_knZd_135389.jpg)

##[Exam++](http://www.examxx.net) 有什么功能
| 功能模块      |    子功能 | 详细介绍  |
| :-------- | :--------| :-- |
|用户功能模块|用户注册登陆|用户可以通过用户名邮箱注册网站，并且通过注册的用户登陆网站。|
||随机练习|从题库中随机取出指定数量的题目供学员练习。|
||强化练习|按照学员知识分布情况，分类进行练习，每次练习的结果会纳入到学员学习进度中。|
||错题练习|学员做错过的题库会记录在错题库中，学员可以从中进行学习。|
||模拟考试|学员可以从模拟考试的分类中选择试卷进行考试。|
||随机组卷|学员可以从随机组卷的分类中选择试卷进行考试。|
||专家试卷|学员可以从专家试卷的分类中选择试卷进行考试。|
||统计分析|用图表方式对学员知识体系下所有的题目做统计分析，学员可以清楚的知道自己的知识点掌握情况。|
||考试历史|参加过的考试会记录在考试历史中，通过点击可以查看答题情况，得分，和错题解答。|
|管理员/教师功能模块|题库管理|通过题库管理模块，教师可以增加、修改、删除题目，现在一共有6种题型，包括单选、多选、判断、简答、论述、分析、计算。|
||试卷管理|教师可以从题库中选择试题组成试卷供学员考试之用，组卷分为手动和自动两种。组卷完毕后还可以进一步调整试卷的分值，移除或添加试题。|
||用户管理|教师或管理员可以管理目前网站的注册用户。|

##使用
- [Exam++](http://www.examxx.net)采用了mysql数据库，因此，请安装mysql（5.0以上版本），安装完毕后，请创建一个名为examxx的数据库，并将doc目录下的数据库文件`examxx.sql`导入到数据库。
- 请将examxx.war拷贝到tomcat目录下的webapps目录中。
- tomcat启动后，war包自动部署到tomcat，打开webapps\examxx\WEB-INF\spring\root-context.xml修改数据库配置，填写你自己的数据库信息，如下：

![](http://git.oschina.net/ocelot/examxx/raw/master/doc/111.png)
- 启动tomcat服务器，输入http://localhost:8080/examxx 进入到exam++主页面。如果能正常打开，则进度到第下一步，否则，请检查服务器配置或数据库配置是否正确。

![](http://git.oschina.net/ocelot/examxx/raw/master/doc/222.jpg)
- 点击右上角登录按钮，输入用户名admin和密码123456即可登录系统。

##问题
- 如果有任何问题或者建议请发email：<link4hy@126.com>,<mars-fox@163.com>。 建议与问题一经采纳即会将您的贡献大名列入[Exam++](http://www.examxx.net)的感谢名单中。
- 或加入QQ群和我们一起讨论吧！
[![](http://pub.idqqimg.com/wpa/images/group.png)](http://shang.qq.com/wpa/qunwpa?idkey=b365bd36df1bd42ffcf8ab7b91fb93f21841548bcda6ec721de276bae9f72e35)

##修改记录
- 2015-03-07 试题增加标签功能
- 2015-02-03 修改题库分页按钮无效的bug
- 2015-01-24 完善题库导入功能
- 2015-01-11 试题评论功能