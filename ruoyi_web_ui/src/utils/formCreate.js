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
    console.log(fields);
    fields.forEach((item) => {
        rule.push(JSON.parse(item))
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

// 设置表单的 Conf 和 Fields
export const setConfAndFields2 = (
    detailPreview,
    conf,
    fields,
    value
) => {
    // @ts-ignore
    detailPreview.value.option = JSON.parse(conf)
    // @ts-ignore
    detailPreview.value.rule = decodeFields(fields)
    if (value) {
        // @ts-ignore
        detailPreview.value.value = value
    }
}
