# MRedrock Exam 2020

### APP简要介绍：

这是一款叫做音乐(lè)的软件，我再强调一遍，第二个字读快乐的乐！

它的作用很简单，就是查看他人的歌单以及白嫖网易云的免费音乐

即便它是如此的简单，以至于没有多少功能，但是却耗费了我近2天的时间= =



### 完成的功能：

##### 1、播放页：

随机播放 循环播放 暂停 播放 上一首 下一首 进度拖拽 后台播放 歌词显示

##### 2、首页：

banner 歌单推荐 封面 标题 作者

##### 3、歌单详情页：

封面 标题 作者 歌曲数 歌曲列表

##### 4、登录页：

伪登录

##### 5、个人信息页：

头像 id 地区邮编 关注数 被关注数 动态数

##### 6、个人歌单页：

封面 标题 作者 歌曲数 直接播放歌单

##### 7、搜索页：

搜索指定关键词的歌曲



### 使用步骤：

点开APP，可以看到输入账号和密码的界面，默认的账号是 hljj 给的账号，有需要可以更改，输入密码并没有任何用处，然后点击登录按钮即可进入主界面

主界面下方可以选择首页、个人歌单页、个人信息页

点击首页的右上角的放大镜图标可以进入搜索页，搜索页中可以输入关键词并点击搜索歌曲，点击搜索结果可以播放点击的歌曲，点击搜索页的左上角的箭头图标可以返回

首页的上方横向展示了banner，点击banner可以进入专辑详情页

首页的中间以瀑布流方式纵向展示了推荐的歌单，点击歌单可以进入歌单详情页

首页的下方是音乐条，点击音乐条左边的图标可以切换播放和暂停，音乐条右边显示了当前播放的歌曲的名称和歌手名，点击可以进入播放页，首次进入APP时显示的是欢迎语

个人歌单页的上方纵向展示了当前用户的所有歌单，点击歌单图标可以直接播放该歌单，点击歌单右侧可以进入歌单详情页

个人歌单页的下方同样是音乐条

个人详情页依次显示了用户头像、用户名、地区邮编、被关注数、动态数、关注数

专辑/歌单详情页上方显示了歌单的封面、名称、作者、歌曲数，点击左上角的箭头图标可以返回

专辑/歌单详情页中间的右侧图标可以点击切换随机播放、循环播放

专辑/歌单详情页下方纵向展示了歌单的每一首歌曲以及歌曲时长，点击可以播放歌曲，当前正在播放的歌曲会蓝色高亮

播放页的上方显示了歌曲名，点击左侧箭头图标可以返回，点击右侧的省略号图标可以回到该歌曲的专辑/歌单详情页

播放页的中间显示了歌曲封面、随机播放按钮、上一曲按钮、播放暂停按钮、下一曲按钮、循环播放按钮，当前播放顺序的按钮会蓝色高亮

播放页的下方是歌词区和进度条，歌词区显示了当前歌词和下一句歌词，进度条左边是当前进度时间，右边是剩余时间，可以拖动进度条改变当前播放位置



### 使用到的重要技术知识点：

1、manifest中配置网络权限

2、设置networkSecurityConfig以支持http

3、设置screenOrientation让activity强制竖屏

4、部分RecyclerViewAdapter采用PagedListAdapter代替，以达到分页加载的效果

5、为PagedListAdapter设置DataSource和对应的Factory

6、采用MVVM设计模式配合DataBinding、LifeCycle，将视图、逻辑、数据分离，让视图层实时观察数据的变化并做出相应改变

7、使用SharedPreferences记录、读取输入的账号id

8、通过学习activity任务栈，编写代码实现仅在启动时进入登录页

9、采用多fragment的方式展示页面，手动管理fragment状态

10、通过观察LiveData的方式让activity被动的切换fragment

11、采用轮询的方式来获取播放状态信息

12、使用service实现后台播放，view model与service之间通过自定义的binder进行通信

13、自定义了不同颜色按钮的样式、输入框的样式、进度条的样式

14、使用了自己手写的工具库，包括Edlig（图片加载）、fiber（多线程）、httpss（网络请求）、jsoff（JSON解析），但是效果不是很好 /(ㄒoㄒ)/~~

### 心得体会：

要编写一款精美实用、稳定高效 、没有BUG的APP是一件非常困难的事，需要足够的耐心、仔细、具有优秀的编程思维和架构能力、善于分析代码、深入理解Android底层原理

在尝试编写MVVM的程序以后，我了解到了自己目前的能力还不足以驾驭这种设计模式，需要在日后继续努力学习，提升自己对代码的熟练度，掌握更好的编程思维，这样才能对以后的新项目得心应手

也许在编写项目的时候画一幅设计流程图可以进一步帮助自己找到方向？我常常有“写了一大堆代码以后忘记了自己之前到底要干嘛”的困惑，这非常危险！