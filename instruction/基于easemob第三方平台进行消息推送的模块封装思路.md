## 保密性
> 发送的数据交互请求发送到服务器再经由服务器处理后再推送至第三方平台进行数据推送

## 持久性
> 数据原则上应保存于服务器，保存于服务器数据库中，文件资源保存于ftp服务器中

## 应对第三方接口限流方案
> 由于第三方平台接口有对请求数据进行限流，所以为了保证数据能够及时有效精准的推送的app端
在高并发有多条数据请求进入服务器端，先将请求数据存进消息队列中，添加定时任务消费数据；消费的同时进行持久化处理

## 消息队列的选用

