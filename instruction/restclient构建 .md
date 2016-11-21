由于第三方平台提供的的基于最简单的HTTP协议的REST风格的服务端接口
构建REST client
第三方平台环信的 REST API 都是基于 JSON ，所以用REST client构造 HTTP 请求的时候，需要在 HTTP HEADER 中指明：

-|
Header_name	|Header_value|	Description
Accept	|application/json|	服务器端返回给客户端的数据类型
Content-Type	|application/json|	客户端发送到服务器端的数据类型

第三方平台推荐使用的Jersey 来实现REST client，尝试使用Jersey和Httpclient都试一下