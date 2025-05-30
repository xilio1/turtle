

export const Callbacks = (emit: any) => ({
    urlProcessor: (url: string, srcType: any) => url,
    /**
     * 文件上传
     * @param file 选择的文件
     * @param callback
     */
    fileUpload(file: File, callback: (url: string, params?: any) => void) {
        if (/image/i.test(file.type)) {
            const formData = new FormData();
            formData.append("file", file);
            Https.action(API.FILE.upload_image, {
                body: formData
            }).then((response: any) => {
                if (response.data) {
                    callback(response.data, {
                        name: file.name.replace(/\.[^.]+$/, ""),
                        isShadow: true,
                        width: "60%",
                        height: "auto",
                    });
                }
            }).catch(() => {
                callback("images/demo-error.png"); // 回显错误图片
            });
        }
    },
    //内容改变的时回调
    afterChange: (text: any, html: any) => {
        if (emit) {
            emit("markdown-change", {content: text});
            emit('update:modelValue',  text)
        }
    },
    beforeImageMounted: (srcProp: any, src: any) => ({srcProp, src}),
});
