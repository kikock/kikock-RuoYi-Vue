## 开发
1. 环境说明：nodejs 版本为 16.13.0 以上,如果该版本以下,[请使用electron4分支中的代码](https://gitee.com/suxia2/ruo-yi-vue-postgresql-electron/tree/electron4/).

### 已知bug
1. 点击首页会404(未解决，请大佬帮忙解决一下)
2. 打包后无法引用 "clipboard"， 把以下文件内容全部注释掉
```
  /src/directive/module/clipboard.js
```

```bash
# 克隆项目
git clone https://gitee.com/suxia2/ruo-yi-vue-postgresql-electron.git

# 进入项目目录
cd ruoyi-ui

# 安装依赖
npm install

# 建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 启动服务
npm run electron:serve
# 打包服务 windows 
npm run electron:build
```

### 改造步骤 已完成
1. 安装electron-builder
```vue
 add electron-builder
```
2. 在vue.config.js中添加以下代码
```
 pluginOptions: {
    electronBuilder: {
      nodeIntegration: true,
      contextIsolation: false,
    }
  }
```
3. 在启动类/src/background.js 更改并添加以下代码
```
const win = new BrowserWindow({
    width: 1200,
    height: 700,
    // fullscreen: true,//全屏
    show: false,
    webPreferences: {
      // Use pluginOptions.nodeIntegration, leave this alone
      // See nklayman.github.io/vue-cli-plugin-electron-builder/guide/security.html#node-integration for more info
      contextIsolation:false,     //上下文隔离
      enableRemoteModule: true,   //启用远程模块
      nodeIntegration: true, //开启自带node环境
      webviewTag: true,     //开启webview
      webSecurity: false,
      allowDisplayingInsecureContent: true,
      allowRunningInsecureContent: true
    },
    // frame: false,//客户端窗口顶部菜单去掉
  })
  win.maximize()
  win.show()
  ipcMain.on('getPrinterList', (event) => {
    //主线程获取打印机列表
    const list = win.webContents.getPrinters();
    //通过webContents发送事件到渲染线程，同时将打印机列表也传过去
    win.webContents.send('getPrinterList', list);
  });
```
5. 打印模板存放位置
```
  /static/print.html
```

