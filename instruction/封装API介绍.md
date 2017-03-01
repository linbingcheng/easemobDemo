## 测试参考http://api-docs.easemob.com/#/获取token
### 获取token
(后面这个接口不用管，要使用token的地方都封装好了)
调用方式参考 ：com.linbingcheng.easemob.TokenTest.getToken()

### 用户体系集成

####注册IM用户[单个]
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.addIMUser()
####注册IM用户[批量] (接口建议在20-60之间。）
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.addIMUsersBatch()
####获取IM用户[单个]
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.getIMUserByUserName()
####获取IM用户[批量]，参数为空时默认返回最早创建的10个用户 <br>
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.getIMUsersBatch()
####删除IM用户[单个]
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.deleteIMUserByUserName()
####删除IM用户[批量]，随机删除
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.deleteIMUserBatch()
####重置IM用户密码
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.modifyIMUserPasswordWithAdminToken()
####修改用户昵称
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.modifyIMUserNickNameWithAdminToken()
####给IM用户的添加好友
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.addFriendSingle()
####解除IM用户的好友关系
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.deleteFriendSingle()
####查看某个IM用户的好友信息
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.getFriends()
####往IM用户的黑名单中加人
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.addToBlackList()
####从IM用户的黑名单中减人
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.removeFromBlackList()
####获取IM用户的黑名单
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.getBlackList()
####查看用户在线状态
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.getIMUserStatus()
####查询离线消息数
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.getOfflineMsgCount()
####用户账号禁用
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.deactivateIMUser()
####用户账号解禁
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.activateIMUser()
####强制用户下线
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.disconnectIMUser()
####获取用户参与的群组
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.getIMUserAllChatGroups()
####获取用户所有参与的聊天室
调用方式参考 ：com.linbingcheng.easemob.IMUserTest.getIMUserAllChatRooms()

###聊天室管理

####创建聊天室
调用方式参考 ：com.linbingcheng.easemob.ChatRoomTest.createChatRoom()
####修改聊天室信息
调用方式参考 ：com.linbingcheng.easemob.ChatRoomTest.modifyChatRoom()
####删除聊天室
调用方式参考 ：com.linbingcheng.easemob.ChatRoomTest.deleteChatRoom()
####获取app中所有的聊天室
调用方式参考 ：com.linbingcheng.easemob.ChatRoomTest.getAllChatRooms()
####获取一个聊天室详情
调用方式参考 ：com.linbingcheng.easemob.ChatRoomTest.getChatRoomDetail()
####聊天室成员添加[单个]
调用方式参考 ：com.linbingcheng.easemob.ChatRoomTest.addSingleUserToChatRoom()
####聊天室成员添加[批量]
调用方式参考 ：com.linbingcheng.easemob.ChatRoomTest.addBatchUsersToChatRoom()
####聊天室成员删除[单个]
调用方式参考 ：com.linbingcheng.easemob.ChatRoomTest.removeSingleUserFromChatRoom()
####聊天室成员删除[批量]
调用方式参考 ：com.linbingcheng.easemob.ChatRoomTest.removeBatchUsersFromChatRoom()