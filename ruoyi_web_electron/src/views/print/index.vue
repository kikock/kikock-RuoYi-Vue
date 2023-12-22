<template>
  <div class="app-container">
    <div class="rainbow-button-wrapper">
      <button id="openPrintgt800" class="about-button modal-hide" @click="print"> 打印测试</button>
    </div>
    <!-- TODO  未实现-->
<!--    <div class="rainbow-button-wrapper">-->
<!--      <button id="openPrintPage" class="about-button modal-hide" @click="printPage"> 打印分页 </button>-->
<!--    </div>-->

    <div class="container">
      <webview id="printWebview" :src="html" nodeintegration  webpreferences="contextIsolation=no" v-show="false"></webview>
<!--      <webview id="printWebviewPage" :src="htmlPage" nodeintegration  webpreferences="contextIsolation=no" v-show="false"></webview>-->
    </div>

  </div>
</template>

<script>
const ipcRenderer = require("electron").ipcRenderer;
export const absPath = fileNameOrFolder => {
  //获取根目录，开发环境与安装环境区别
  const path = require('path')
  return path.join(process.resourcesPath,
    (process.env.NODE_ENV == 'development' ?
      `../../../../${fileNameOrFolder}` : `../${fileNameOrFolder}`))
}
export default {
  name: "Index",
  data(){
    return {
      printerList: [],
      html:absPath('/static/print.html'),
      htmlPage:absPath('/static/printPage.html'),
      printer: "Microsoft Print to PDF",
      initWebViewSuccess: false,
      initWebViewPageSuccess: false,
      count :1

    }
  },
  mounted() {
    this.getPrinterList()
    this.initWebView()
  },
  methods:{
    print() {
      if(!this.initWebViewSuccess){
        this.$modal.msgError('正在初始化打印机,请稍后再试');
      }
      const webview=this.webview
      // // const webview =  document.getElementById('printWebview');
      var data = {
        hzxm:'张三',
        address: '上海市青浦区',
        phone:'133333333'+this.count
      };
      this.count ++;
      //在send时将arr传递过去
      webview.send("webview-print-render", data); //向webview嵌套的页面响应事件

      webview.addEventListener("ipc-message", event => {
        console.log(event.channel); // Prints "pong" 在此监听事件中接收webview嵌套页面所响应的事件
        if (event.channel === "webview-print-do") {
          console.log("通信成功");
          webview.print({
            silent: true,
            printBackground: true,
            deviceName: '' //选择打印机
          }).then((res) => {

          }).catch((err) => {
            })
            .finally(() => {
              // this.messageBox.close()
            });

        }
      });
    },
    async printPage() {
      if(!this.initWebViewPageSuccess){
        this.$modal.msgError('正在初始化打印机,请稍后再试');
      }
      const webviewPage=this.webviewPage
      // // const webview =  document.getElementById('printWebview');

      var data = {
        hzxm:'张三',
        address: '上海市xxxx区',
        phone:'133333333'+this.count
      };
      this.count ++;
      //在send时将arr传递过去
      webviewPage.send("webview-print-render", data); //向webview嵌套的页面响应事件

      webviewPage.addEventListener("ipc-message", event => {
        console.log(event.channel); // Prints "pong" 在此监听事件中接收webview嵌套页面所响应的事件
        if (event.channel === "webview-print-do") {
          console.log("通信成功");
          webviewPage.print({
            silent: true,
            printBackground: true,
            deviceName: '', //选择打印机,
            pageSize: 'A4'
          }).then((res) => {

          }).catch((err) => {
          })
            .finally(() => {
              // this.messageBox.close()
            });

        }
      });
    },
    getPrinterList(){
      ipcRenderer.send('getPrinterList');
      ipcRenderer.once('getPrinterList', (event, data) => {
        //data就是打印机列表
        this.printList = data;
        // isDefault 为true 就是系统默认打印机
        console.log(this.printList)

      });
    },
    initWebView(){
      const webview = document.querySelector("#printWebview");
      this.webview = webview;
      webview.addEventListener("dom-ready", () => {
        //dom-ready---webview加载完成
        // webview.openDevTools(); //这个方法可以打开print.html的控制台
        this.initWebViewSuccess = true
      });
      //分页 未实现
      // const webviewPage = document.querySelector("#printWebviewPage");
      // this.webviewPage = webviewPage;
      // webviewPage.addEventListener("dom-ready", () => {
      //   //dom-ready---webview加载完成
      //   // webview.openDevTools(); //这个方法可以打开print.html的控制台
      //   this.initWebViewPageSuccess = true
      // });
    }
  }
}
</script>

<style scoped>

/* Welcome ------------------------ */

.about {
  --about-space: 4rem;

  position: absolute;
  display: flex;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 1;
  overflow-x: hidden;
  overflow-y: auto;
  padding: 0;
  background-color: hsl(0,0%,98%);
  pointer-events: none;
  visibility: hidden;
  opacity: 0;
  transform: scale(1.1);
  transition: visibility 0s .12s linear , opacity .12s ease-in, transform .12s ease-in;
}
.about.is-shown {
  pointer-events: auto;
  visibility: visible;
  opacity: 1;
  transform: scale(1);
  transition: visibility 0s 0s linear , opacity .24s ease-out, transform .24s ease-out;
}

.about-wrapper {
  margin: auto;
}

.about-header {
  padding: var(--about-space) 0;
  border-bottom: 1px solid hsl(0,0%,88%);
}

.about-logo {
  display: block;
  margin: 0 auto;
  width: 320px; /* TODO: Adjust asset to this size */
  max-width: 100%;
}

.about-sections {
  max-width: 680px;
  padding: 0 var(--about-space);
}

.about-section {
  margin: var(--about-space) 0;
}

.about h2 {
  text-align: center;
  margin: 0 0 1em 0;
  font-size: 1.5em;
  color: hsl(0, 0%, 55%);
}

.about .about-code h2 {
  color: hsl(330, 65%, 55%);
}

.about .play-along h2 {
  color: hsl(222, 53%, 50%);
}

.about-button {
  display: block;
  margin: 0 auto;
  padding: .4em 1.2em;
  font: inherit;
  font-size: 1.6em;
  color: inherit;
  border: 2px solid;
  border-radius: 4px;
  background-color: transparent;
}
.about-button:focus {
  outline: none;
  border-color: hsl(0,0%,88%);
}

footer.about-section {
  text-align: center;
}

.rainbow-button-wrapper {
  --rainbow-button-width: 170px;
  --rainbow-button-height: 50px;
  --rainbow-button-width-inner: 164px;
  --rainbow-button-height-inner: 44px;
  --rainbow-color-1: hsl(116, 30%, 36%);
  --rainbow-color-2: hsl(194, 60%, 36%);
  --rainbow-color-3: hsl(222, 53%, 50%);
  --rainbow-color-4: hsl(285, 47%, 46%);
  --rainbow-color-5: hsl(330, 65%, 48%);
  --rainbow-color-6: hsl(32, 79%, 49%);
  --rainbow-color-7: hsl(53, 84%, 50%);

  display: inline-block;
  width: var(--rainbow-button-width);
  height: var(--rainbow-button-height);
  position: relative;
  overflow: hidden;
  border-radius: 5px;
}

.rainbow-button-wrapper:before {
  display: block;
  position: absolute;
  z-index: 2;
  top: 0;
  left: 0;
  width: 600px;
  height: var(--rainbow-button-height);
  background: #CCC;
  background: linear-gradient(to right, var(--rainbow-color-1) 0%, var(--rainbow-color-2) 14%, var(--rainbow-color-3) 28%, var(--rainbow-color-4) 42%, var(--rainbow-color-5) 56%, var(--rainbow-color-6) 70%, var(--rainbow-color-7) 84%, var(--rainbow-color-1) 100%);
  background-position: -200px 0;
  transition: all 0.5s;
  content: "";
}

.rainbow-button-wrapper button {
  display: block;
  width: var(--rainbow-button-width-inner);
  height: var(--rainbow-button-height-inner);
  position: absolute;
  z-index: 3;
  top: 3px;
  left: 3px;
  border: none;
  background: white;
  color: black;
  font-size: 1.3rem;
}

.rainbow-button-wrapper:hover:before {
  background-position: 200px 0;
}

@media (min-width: 940px) {
  .about-header {
    align-self: center;
    padding: var(--about-space);
    border-right: 1px solid hsl(0,0%,88%);
    border-bottom: none;
  }
  .about-wrapper {
    display: flex;
  }
}

.container{
  margin-top: 20px;
}
</style>
