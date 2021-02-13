# yzydemo

## 一、开放登录
### 代码逻辑入口
`/oauth/`
### 调试步骤：
### 1.拼接以下URL并设置为应用主页
```
https://open.weixin.qq.com/connect/oauth2/authorize?appid={CORPID}&redirect_uri=http://{DOMAIN}/demo/oauth/&response_type=code&scope=scope&agentid={AGENTID}&state=state#wechat_redirect
```
### 2.打开粤政易进入应用即可开始调试