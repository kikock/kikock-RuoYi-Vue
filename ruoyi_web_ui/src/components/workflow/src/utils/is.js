"use strict";
// copy to vben-admin
Object.defineProperty(exports, "__esModule", {value: true});
exports.isEmptyVal = exports.isImgPath = exports.isDark = exports.isUrl = exports.isClient = exports.isServer = exports.isMap = exports.isElement = exports.isWindow = exports.isArray = exports.isRegExp = exports.isBoolean = exports.isFunction = exports.isString = exports.isPromise = exports.isNumber = exports.isNullOrUnDef = exports.isNullAndUnDef = exports.isNull = exports.isDate = exports.isEmpty = exports.isObject = exports.isUnDef = exports.isDef = exports.is = void 0;
var toString = Object.prototype.toString;
var is = function (val, type) {
    return toString.call(val) === "[object ".concat(type, "]");
};
exports.is = is;
var isDef = function (val) {
    return typeof val !== 'undefined';
};
exports.isDef = isDef;
var isUnDef = function (val) {
    return !(0, exports.isDef)(val);
};
exports.isUnDef = isUnDef;
var isObject = function (val) {
    return val !== null && (0, exports.is)(val, 'Object');
};
exports.isObject = isObject;
var isEmpty = function (val) {
    if (val === null) {
        return true;
    }
    if ((0, exports.isArray)(val) || (0, exports.isString)(val)) {
        return val.length === 0;
    }
    if (val instanceof Map || val instanceof Set) {
        return val.size === 0;
    }
    if ((0, exports.isObject)(val)) {
        return Object.keys(val).length === 0;
    }
    return false;
};
exports.isEmpty = isEmpty;
var isDate = function (val) {
    return (0, exports.is)(val, 'Date');
};
exports.isDate = isDate;
var isNull = function (val) {
    return val === null;
};
exports.isNull = isNull;
var isNullAndUnDef = function (val) {
    return (0, exports.isUnDef)(val) && (0, exports.isNull)(val);
};
exports.isNullAndUnDef = isNullAndUnDef;
var isNullOrUnDef = function (val) {
    return (0, exports.isUnDef)(val) || (0, exports.isNull)(val);
};
exports.isNullOrUnDef = isNullOrUnDef;
var isNumber = function (val) {
    return (0, exports.is)(val, 'Number');
};
exports.isNumber = isNumber;
var isPromise = function (val) {
    return (0, exports.is)(val, 'Promise') && (0, exports.isObject)(val) && (0, exports.isFunction)(val.then) && (0, exports.isFunction)(val.catch);
};
exports.isPromise = isPromise;
var isString = function (val) {
    return (0, exports.is)(val, 'String');
};
exports.isString = isString;
var isFunction = function (val) {
    return typeof val === 'function';
};
exports.isFunction = isFunction;
var isBoolean = function (val) {
    return (0, exports.is)(val, 'Boolean');
};
exports.isBoolean = isBoolean;
var isRegExp = function (val) {
    return (0, exports.is)(val, 'RegExp');
};
exports.isRegExp = isRegExp;
var isArray = function (val) {
    return val && Array.isArray(val);
};
exports.isArray = isArray;
var isWindow = function (val) {
    return typeof window !== 'undefined' && (0, exports.is)(val, 'Window');
};
exports.isWindow = isWindow;
var isElement = function (val) {
    return (0, exports.isObject)(val) && !!val.tagName;
};
exports.isElement = isElement;
var isMap = function (val) {
    return (0, exports.is)(val, 'Map');
};
exports.isMap = isMap;
exports.isServer = typeof window === 'undefined';
exports.isClient = !exports.isServer;
var isUrl = function (path) {
    var reg = /(((^https?:(?:\/\/)?)(?:[-:&=\+\$,\w]+@)?[A-Za-z0-9.-]+(?::\d+)?|(?:www.|[-:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&%@.\w_]*)#?(?:[\w]*))?)$/;
    return reg.test(path);
};
exports.isUrl = isUrl;
var isDark = function () {
    return window.matchMedia('(prefers-color-scheme: dark)').matches;
};
exports.isDark = isDark;
// 是否是图片链接
var isImgPath = function (path) {
    return /(https?:\/\/|data:image\/).*?\.(png|jpg|jpeg|gif|svg|webp|ico)/gi.test(path);
};
exports.isImgPath = isImgPath;
var isEmptyVal = function (val) {
    return val === '' || val === null || val === undefined;
};
exports.isEmptyVal = isEmptyVal;
