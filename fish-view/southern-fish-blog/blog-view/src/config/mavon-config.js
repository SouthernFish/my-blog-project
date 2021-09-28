import {baseImgURL, baseURL} from "@/config";
import {getAccessToken} from "@/utils/accessToken";

/*富文本编辑图片上传配置*/
const uploadConfig = {
    action:  baseURL + "/blog/upload/file/byftp?token=" + getAccessToken(),// 必填参数 图片上传地址
    methods: 'POST',  // 必填参数 图片上传方式
    token: '',  // 可选参数 如果需要token验证，假设你的token有存放在sessionStorage
    name: 'file',  // 必填参数 文件的参数名
    size: 5*1024,  // 可选参数   图片大小，单位为Kb, 1M = 1024Kb
    accept: 'image/png, image/gif, image/jpeg, image/bmp, image/x-icon, image/jfif'  // 可选 可上传的图片格式
};

// toolbar工具栏的工具选项（默认展示全部）
const toolOptions = {
    bold: true, // 粗体
    italic: true, // 斜体
    header: true, //标题
    underline: true, //下划线
    strikethrough: true, // 中划线
    mark: true, // 标记
    superscript: true, //上角标
    subscript: true, //下角标
    quote: true, // 引用
    ol: true, //有序列表
    ul: true, // 无序列表
    link: true, // 链接
    imagelink: true, //图片链接
    code: true, //code
    table: true, //表格
    fullscreen: true, //全屏编辑
    readmodel : true, // 沉浸式阅读
    htmlcode: true, //展示html源码
    help: true, //帮助
    /*1.3.5 */
    undo: true, // 上一步
    redo: true, //下一步
    trash: true, // 清空
    save: false,//保存（触发events中的save事件）
    /*1.4.2*/
    navigation: true,//导航目录
    /*2.1.8 */
    alignleft: true, //左对齐
    aligncenter: true, //居中
    alignright: true, // 右对齐
    /*2.2.1*/
    subfield: true, //单双栏模式
    preview: true, // 预览
};

const handlers = {
    image: function image() {
        var self = this;
        var fileInput = this.container.querySelector('input.ql-image[type=file]');
        if (fileInput === null) {
            fileInput = document.createElement('input');
            fileInput.setAttribute('type', 'file');
            // 设置图片参数名
            if (uploadConfig.name) {
                fileInput.setAttribute('name', uploadConfig.name);
            }
            // 可设置上传图片的格式
            fileInput.setAttribute('accept', uploadConfig.accept);
            // fileInput.classList.add('ql-image');
            // 监听选择文件
            fileInput.addEventListener('change', function () {
                // 创建formData
                var formData = new FormData();
                formData.append(uploadConfig.name, fileInput.files[0]);
                // 如果需要token且存在token
                if (uploadConfig.token) {
                    formData.append('token', uploadConfig.token)
                }
                // 图片上传
                var xhr = new XMLHttpRequest();
                xhr.open(uploadConfig.methods, uploadConfig.action, true);
                // 上传数据成功，会触发
                xhr.onload = function () {
                    if (xhr.status === 200) {
                        var res = JSON.parse(xhr.responseText);
                        let length = self.markdown.getSelection(true).index;
                        //这里很重要，你图片上传成功后，img的src需要在这里添加，res.path就是你服务器返回的图片链接。
                        let path = baseImgURL + res.data;
                        console.log(path, "查看上传的路径")
                        self.markdown.insertEmbed(length, 'image', path);
                        self.markdown.setSelection(length + 1)
                    }
                    fileInput.value = ''
                };
                // 开始上传数据
                xhr.upload.onloadstart = function () {
                    fileInput.value = ''
                };
                // 当发生网络异常的时候会触发，如果上传数据的过程还未结束
                xhr.upload.onerror = function () {
                };
                // 上传数据完成（成功或者失败）时会触发
                xhr.upload.onloadend = function () {
                    // console.log('上传结束')
                };
                xhr.send(formData)
            });
            this.container.appendChild(fileInput);
        }
        fileInput.click();
    }
};

export default {
    placeholder: '',
    theme: 'snow',  // 主题
    modules: {
        toolbar: {
            container: toolOptions,  // 工具栏选项
            handlers: handlers  // 事件重写
        }
    }
};
