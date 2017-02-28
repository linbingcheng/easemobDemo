## 测试参考http://api-docs.easemob.com/#/获取token
### 获取token
(后面这个接口不用管，要使用token的地方都封装好了)
参考 com.linbingcheng.easemob.TokenTest.getToken()

### 用户体系集成

####注册IM用户[单个]
参考 com.linbingcheng.easemob.IMUserTest.addIMUser()
####注册IM用户[批量] (接口建议在20-60之间。）
参考 com.linbingcheng.easemob.IMUserTest.addIMUsersBatch()
####获取IM用户[单个]
参考 com.linbingcheng.easemob.IMUserTest.getIMUserByUserName()
####获取IM用户[批量]，参数为空时默认返回最早创建的10个用户 <br>
参考 com.linbingcheng.easemob.IMUserTest.getIMUsersBatch()
####删除IM用户[单个]
参考 com.linbingcheng.easemob.IMUserTest.deleteIMUserByUserName()
####删除IM用户[批量]，随机删除
参考 com.linbingcheng.easemob.IMUserTest.deleteIMUserBatch()