// @ts-nocheck
import { ref } from "vue";
import { ElMessage, UploadUserFile,UploadFile } from "element-plus";
import { uploadImageApi } from "@/api/material/index.ts";
export default function useUpload() {
    //图片上传组件ref属性
    const uploadRef = ref()
    const dialogImageUrl = ref("");
    const dialogVisible = ref(false);
    const disabled = ref(false);
    //上传的文件
    const fileList = ref<UploadUserFile[]>([])
    //上传图片的地址
    const imgurl = ref('');
    const handlePictureCardPreview = (file: UploadFile) => {
        dialogImageUrl.value = file.url!;
        dialogVisible.value = true;
    };

    const handleRemove = (file: UploadFile) => {
        fileList.value = fileList.value.filter(item => item.name != file.name)
    };

    //图片上传
    const uploadFile = async(file: any) => {
        console.log(file)
        //定义可以上传的图片类型
        const typeArr = ["image/png", "image/gif", "image/jpeg", "image/jpg"];
        const isImg = typeArr.indexOf(file.raw.type) !== -1;
        const isMore3M = file.size / 1024 / 1024 < 3;
        if (!isImg) {
            ElMessage.warning("只能上传图片类型!");
            uploadRef.value?.clearFiles()
            return;
        }
        if (!isMore3M) {
            ElMessage.warning("图片大小超过3M!");
            uploadRef.value?.clearFiles()
            return;
        }
        //设置图片上传的参数
        const formData = new FormData();
        formData.append("file", file.raw);
        let res = await uploadImageApi(formData)
        if(res && res.code == 200){
            console.log(res)
            imgurl.value = process.env.BASE_API + res.data
        }
    }
    return {
        dialogImageUrl,
        dialogVisible,
        disabled,
        handlePictureCardPreview,
        handleRemove,
        uploadRef,
        uploadFile,
        fileList,
        imgurl
    }
}
