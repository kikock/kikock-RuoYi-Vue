/**
 * 通用js方法封装处理
 * Copyright (c) 2019 ruoyi
 */
import * as dd from "dingtalk-jsapi";
import {getToken, setToken} from '@/utils/auth'
import config from '@/config'
import {dingLogin} from '@/api/dingtalk'
import store from '@/store'
import {toast} from '@/utils/common'
import {wechatLogin} from '@/api/wechat'
// 日期格式化
export function parseTime(time, pattern) {
    if (arguments.length === 0 || !time) {
        return null
    }
    const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
    let date
    if (typeof time === 'object') {
        date = time
    } else {
        if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
            time = parseInt(time)
        } else if (typeof time === 'string') {
            time = time.replace(new RegExp(/-/gm), '/').replace('T', ' ').replace(new RegExp(/\.[\d]{3}/gm), '');
        }
        if ((typeof time === 'number') && (time.toString().length === 10)) {
            time = time * 1000
        }
        date = new Date(time)
    }
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    }
    const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
        let value = formatObj[key]
        // Note: getDay() returns 0 on Sunday
        if (key === 'a') {
            return ['日', '一', '二', '三', '四', '五', '六'][value]
        }
        if (result.length > 0 && value < 10) {
            value = '0' + value
        }
        return value || 0
    })
    return time_str
}

// 表单重置
export function resetForm(refName) {
    if (this.$refs[refName]) {
        this.$refs[refName].resetFields();
    }
}

// 添加日期范围
export function addDateRange(params, dateRange, propName) {
    let search = params;
    search.params = typeof (search.params) === 'object' && search.params !== null && !Array.isArray(search.params) ? search.params : {};
    dateRange = Array.isArray(dateRange) ? dateRange : [];
    if (typeof (propName) === 'undefined') {
        search.params['beginTime'] = dateRange[0];
        search.params['endTime'] = dateRange[1];
    } else {
        search.params['begin' + propName] = dateRange[0];
        search.params['end' + propName] = dateRange[1];
    }
    return search;
}

// 回显数据字典
export function selectDictLabel(datas, value) {
    var actions = [];
    Object.keys(datas).some((key) => {
        if (datas[key].value == ('' + value)) {
            actions.push(datas[key].label);
            return true;
        }
    })
    return actions.join('');
}

// 回显数据字典（字符串数组）
export function selectDictLabels(datas, value, separator) {
    var actions = [];
    var currentSeparator = undefined === separator ? "," : separator;
    var temp = value.split(currentSeparator);
    Object.keys(value.split(currentSeparator)).some((val) => {
        Object.keys(datas).some((key) => {
            if (datas[key].value == ('' + temp[val])) {
                actions.push(datas[key].label + currentSeparator);
            }
        })
    })
    return actions.join('').substring(0, actions.join('').length - 1);
}

// 字符串格式化(%s )
export function sprintf(str) {
    var args = arguments, flag = true, i = 1;
    str = str.replace(/%s/g, function () {
        var arg = args[i++];
        if (typeof arg === 'undefined') {
            flag = false;
            return '';
        }
        return arg;
    });
    return flag ? str : '';
}

// 转换字符串，undefined,null等转化为""
export function praseStrEmpty(str) {
    if (!str || str == "undefined" || str == "null") {
        return "";
    }
    return str;
}

// 数据合并
export function mergeRecursive(source, target) {
    for (var p in target) {
        try {
            if (target[p].constructor == Object) {
                source[p] = mergeRecursive(source[p], target[p]);
            } else {
                source[p] = target[p];
            }
        } catch (e) {
            source[p] = target[p];
        }
    }
    return source;
};

/**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 */
export function handleTree(data, id, parentId, children) {
    let config = {
        id: id || 'id',
        parentId: parentId || 'parentId',
        childrenList: children || 'children'
    };

    var childrenListMap = {};
    var nodeIds = {};
    var tree = [];

    for (let d of data) {
        let parentId = d[config.parentId];
        if (childrenListMap[parentId] == null) {
            childrenListMap[parentId] = [];
        }
        nodeIds[d[config.id]] = d;
        childrenListMap[parentId].push(d);
    }

    for (let d of data) {
        let parentId = d[config.parentId];
        if (nodeIds[parentId] == null) {
            tree.push(d);
        }
    }

    for (let t of tree) {
        adaptToChildrenList(t);
    }

    function adaptToChildrenList(o) {
        if (childrenListMap[o[config.id]] !== null) {
            o[config.childrenList] = childrenListMap[o[config.id]];
        }
        if (o[config.childrenList]) {
            for (let c of o[config.childrenList]) {
                adaptToChildrenList(c);
            }
        }
    }

    return tree;
}

/**
 * 参数处理
 * @param {*} params  参数
 */
export function tansParams(params) {
    let result = ''
    for (const propName of Object.keys(params)) {
        const value = params[propName];
        var part = encodeURIComponent(propName) + "=";
        if (value !== null && typeof (value) !== "undefined") {
            if (typeof value === 'object') {
                for (const key of Object.keys(value)) {
                    if (value[key] !== null && typeof (value[key]) !== 'undefined') {
                        let params = propName + '[' + key + ']';
                        var subPart = encodeURIComponent(params) + "=";
                        result += subPart + encodeURIComponent(value[key]) + "&";
                    }
                }
            } else {
                result += part + encodeURIComponent(value) + "&";
            }
        }
    }
    return result
}

// 验证是否为blob格式
export async function blobValidate(data) {
    try {
        const text = await data.text();
        JSON.parse(text);
        return false;
    } catch (error) {
        return true;
    }
}

/**
 * 根据总条数计算页码
 * @param {number} count 总条数
 * @param {number} pageSize 每页条数
 * @returns
 */
export function pagerCount(count, pageSize) {
    if (typeof (count) == "number") {
        if (count > 0) {
            try {
                var _pagerCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
                var c = _pagerCount.toFixed(0);//小数取整
                _pagerCount = c > _pagerCount ? c - 1 : c;//过滤四舍五入
                return _pagerCount;

            } catch (error) {
                return 0;
            }

        } else {
            return 0;
        }

    } else {
        return 0;
    }

}
export function changeDocumentTitle(title) {
    // 钉钉浏览器内部，使用钉钉提供的JSAPI中的接口dd.biz.navigation.setTitle设置导航栏标题
    if(window.navigator.userAgent.includes("DingTalk")) {
        dd.biz.navigation.setTitle({
            title,
            onSuccess : (result)=> {
                console.log(result);
            },
            onFail : (err)=> {
                console.log(result);
            }
        })
    } else document.title = title;//其他浏览器
}
//判断微信登录
export function checkWechatLogin() {
    if (!getToken()) {
        let thiz = this;
        var ua = navigator.userAgent.toLowerCase(); // 将用户代理头的值转为小写
        if (dd.env.platform !== 'notInDingTalk') {
            console.log('钉钉客户端打开')
            dd.ready(function () {
                // 钉钉获取个人信息
                dd.runtime.permission.requestAuthCode({
                    corpId: config.corpId,
                    onSuccess: function (result) {
                        dingLogin(result.code).then(res => {
                            if (res.code == 200) {
                                console.log("钉钉授权返回数据", res);
                                setToken(res.token, 28800);
                                // // 设置用户信息
                                store.dispatch('GetInfo').then(res => {
                                    console.log("设置用户信息返回数据", res);
                                    toast("钉钉授权登录成功!")
                                })
                            }
                        })
                    },
                    onFail: function (err) {
                        toast("获取个人信息异常:" + err.errorMessage)
                    }
                })
            })
        } else if (ua.match(/micromessenger/i) == 'micromessenger') {
            console.log('微信客户端打开')
            //获取当前页面的url
            let link = window.location.href;
            let code = null; //授权获取的code,用它查询用户基本信息
            let appid = config.wx_mp.appId; //公众号或小程序appid
            //就是用户授权后返回来的地址
            let appsecret = config.wx_mp.secret; //公众号开发者秘钥
            // 判断link中有没有code=字符串，
            if (link.indexOf('code=') === -1) {
                //没有code= 发请求
                let uri = encodeURIComponent(link);
                console.log(uri);
//         scope为snsapi_base
                window.location.href = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=${uri}&response_type=code&scope=snsapi_base&state=123#wechat_redirect`;
                // scope为snsapi_userinfo：
                // window.location.href = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appid}&redirect_uri=${uri}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect`;
            } else {
                //回调函数已经执行 返回的链接存在code= 地址解析
                console.log("回调函数已经执行 返回的链接存在code= 地址解析");
                let temp = link.split("code=")[1];
                code = temp.split("&")[0];
                wechatLogin(code).then(res => {
                    if (res.code == 200) {
                        console.log("微信授权成功返回数据", res);
                        setToken(res.token, 28800);
                        // // 设置用户信息
                        store.dispatch('GetInfo').then(res => {
                            console.log("微信授权登录成功", res);
                        })
                    }
                })
            }
        }
    }
}
