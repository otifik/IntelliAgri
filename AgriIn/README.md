塘口配置界面 √
投入品配置界面 √
鱼苗投入界面 √
投喂模板界面 √
投喂饲料界面 √
投喂调水剂界面 √

MyApi、ApiRetrofit中URL配置
update界面
list界面
delete按钮

bug：
1.塘口的query (鱼苗投入中的塘口选择需要用到) √
2.添加成功回调success √
3.--MaterialDialog圆角 (不做考虑吧，搜了一下好像没有修改的方法，除非换普通Dialog)--
4.FishInput界面问题 √
5.--假数据 (不用了)--
6.词条过长，滑动显示 (算小bug，日后解决)
7.点击词条进入详情页 
8.--fishpondlist换成fragment--
9.添加成功后返回Toast错误 √
10.列表为空时展示的空背景 √
11.无限阅读(暂时解决，onloadmore中注释掉++) √
12.PictureSelector没有进入修改界面当中 (后期再做解决)
13.鱼苗投入中加入单位 √
14.如何把从后端获取的数据填入到MaterialDialog
15.点击弹出MaterialDialog弹出详细信息
16.养殖日志那块如何分隔开实现列表栏效果
17.选择过后还是需要显示已经选择的数据，图片看情况解决
18.入塘塘口需要从后端获取，批次号需要根据鱼苗品种和入塘日期自动生成
19.java如何将对象数组的某一个字段映射为一个String数组 √(直接遍历吧，影响到性能可以改一下)
20.Presenter中throwable调用方法有问题
21.投入品配置界面问题，营养成分图片提交方式，base64 √(直接通过MultiPart传输)
22.--巡视检查需要修改--
23.投入品增加模块，png偶尔出现不能查看的情况
24.BrokenPipe的bug
25.数据库datetime格式问题
26.不选择物品时回调为空崩溃
27.自动刷新