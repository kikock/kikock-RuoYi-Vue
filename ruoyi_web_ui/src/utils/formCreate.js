/**
 * 针对 https://github.com/xaboy/form-create-designer 封装的工具类
 */

// 编码表单 Conf
export const encodeConf = (designerRef) => {
    return JSON.stringify(designerRef.value.getOption());
}


// 编码表单 Fields
export const encodeFields = (designerRef) => {
    // @ts-ignore
    const rule = designerRef.value.getRule()
    const fields = []
    rule.forEach((item) => {
        fields.push(JSON.stringify(item))
    })
    return fields
}

// 解码表单 Fields
export const decodeFields = (fields) => {
    const rule = []
    // var parse = JSON.parse(fields);
    console.log(parse);
    parse.forEach((item) => {
        rule.push(JSON.parse(item));
    })
    return rule
}

// 设置表单的 Conf 和 Fields
export const setConfAndFields = (designerRef, conf, fields) => {
    // @ts-ignore
    designerRef.value.setOption(JSON.parse(conf))
    // @ts-ignore
    designerRef.value.setRule(JSON.parse(fields))
}

// 设置表单的 Conf 和 Fields 和 value 和 title
export const setMyConfAndFields = (detailPreview,conf, fields,value,title) => {
    // @ts-ignore
    detailPreview.value.option = JSON.parse(conf)
    // @ts-ignore
    // detailPreview.value.rule = decodeFields(fields);
    detailPreview.value.rule = JSON.parse(fields);
    if (value) {
        // @ts-ignore
        detailPreview.value.value = value
    }
    if (title) {
        // @ts-ignore
        detailPreview.value.title = title
    }
}

// 设置表单的 Conf 和 Fields 和 value 和 title
export const setMyConfAndFields2 = (detailPreview,conf, fields,value,title) => {
    // @ts-ignore
    detailPreview.option = JSON.parse(conf)
    // @ts-ignore
    // detailPreview.value.rule = decodeFields(fields);
    detailPreview.rule = JSON.parse(fields);
    if (value) {
        // @ts-ignore
        detailPreview.value = value
    }
    if (title) {
        // @ts-ignore
        detailPreview.title = title
    }
}
