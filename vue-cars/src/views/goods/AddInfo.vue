<template>
    <SysDialog
            :title="dialog.title"
            :width="dialog.width"
            :height="dialog.height"
            :visible="dialog.visible"
            @onClose="onClose"
            @onConfirm="commit"
    >
        <template v-slot:content>
            <el-form
                    :model="addModel"
                    ref="addFormRef"
                    :rules="rules"
                    label-width="80px"
                    size="default"
            >
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="categoryId" label="车辆类型">
                            <el-select
                                    style="width: 100%"
                                    v-model="addModel.categoryId"
                                    placeholder="请选择分类"
                                    size="default"
                            >
                                <el-option
                                        v-for="item in categoryData.list"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="carName" label="车辆品牌">
                            <el-input v-model="addModel.carName"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="purchasePrice" label="购置价格">
                            <el-input v-model="addModel.purchasePrice"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="discountFactor" label="折扣系数">
                            <el-input v-model="addModel.discountFactor"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="initRegisterDate" label="初登日期">
                            <el-date-picker
                                    v-model="addModel.initRegisterDate"
                                    type="date"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12" :offset="0">
                        <el-form-item prop="insuranceStartDate" label="起保日期">
                            <el-date-picker
                                    v-model="addModel.insuranceStartDate"
                                    type="date"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24" :offset="0">
                        <el-form-item prop="image" label="图片">
                            <el-upload
                                    ref="uploadRef"
                                    action="#"
                                    :on-change="uploadFile"
                                    :auto-upload="false"
                                    :limit="1"
                                    :file-list="fileList"
                                    list-type="picture-card"
                            >
                                <el-icon><Plus /></el-icon>

                                <template #file="{ file }">
                                    <div>
                                        <img
                                                class="el-upload-list__item-thumbnail"
                                                :src="file.url"
                                                alt=""
                                        />
                                        <span class="el-upload-list__item-actions">
                      <span
                              class="el-upload-list__item-preview"
                              @click="handlePictureCardPreview(file)"
                      >
                        <el-icon><zoom-in /></el-icon>
                      </span>
                      <span
                              v-if="!disabled"
                              class="el-upload-list__item-delete"
                              @click="handleRemove(file)"
                      >
                        <el-icon><Delete /></el-icon>
                      </span>
                    </span>
                                    </div>
                                </template>
                            </el-upload>
                            <el-dialog v-model="dialogVisible">
                                <img w-full :src="dialogImageUrl" alt="Preview Image" />
                            </el-dialog>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </template>
    </SysDialog>
</template>

<script setup lang="ts">
import { MaterialType } from "@/api/material/MaterialModel";
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "@/hooks/useDialog";
import {ElMessage, ElNotification, FormInstance} from "element-plus";
import { nextTick, reactive, ref } from "vue";
import useUpload from "@/composables/material/useUpload";
import useSelectCategory from "@/composables/material/useSelectCategory";
import { addApi,editApi } from "@/api/material/index";
import { EditType, Title } from "@/type/BaseEnum";
//下拉分类
const { categoryData, getCateList } = useSelectCategory();
//图片上传
const {
    dialogImageUrl,
    dialogVisible,
    disabled,
    handlePictureCardPreview,
    handleRemove,
    uploadRef,
    uploadFile,
    fileList,
    imgurl,
} = useUpload();
//表单ref属性
const addFormRef = ref<FormInstance>();
//弹框属性
const { dialog, onClose, onShow } = useDialog();
//显示弹框
const show = async (type: string, row?: MaterialType) => {
    imgurl.value = "";
    //清空图片数据
    fileList.value = [];
    if (uploadRef.value) {
        uploadRef.value?.clearFiles();
    }
    //获取车辆分类
    await getCateList();
    //设置弹框属性
    type == EditType.ADD
        ? (dialog.title = Title.ADD)
        : (dialog.title = Title.EDIT);
    dialog.height = 320;
    dialog.width = 680;
    //编辑，设置数据回显
    if (row) {
        nextTick(() => {
            Object.assign(addModel, row);
            //设置图片回显
            let img = {
                name: "",
                url: "",
            };
            img.url = addModel.image;
            imgurl.value = addModel.image;
            fileList.value.push(img);
        });
    }
    onShow();
    //清空表单
    addFormRef.value?.resetFields();
    addModel.type = type;
};
//暴露出去
defineExpose({
    show,
});
//表单绑定的对象
const addModel = reactive<MaterialType>({
    carId:"",
    categoryId:"",
    carName:"",
    image:"",
    purchasePrice: 0,
    discountFactor:"",
    initRegisterDate:"",
    insuranceDate:""
});
//表单验证规则
const rules = reactive({
    categoryId: [
        {
            required: true,
            trigger: "blur",
            message: "请选择车辆类型",
        },
    ],
    carName: [
        {
            required: true,
            trigger: "blur",
            message: "请填写车辆品牌",
        },
    ],
    image: [
        {
            required: true,
            trigger: "blur",
            message: "请上传车辆图片",
        },
    ],
    purchasePrice: [
        {
            required: true,
            trigger: "blur",
            message: "请填写购买",
        },
    ],
    discountFactor: [
        {
            required: true,
            trigger: "blur",
            message: "请填写折扣系数",
        },
    ],
});
//注册事件
const emits = defineEmits(['reFresh'])
//表单提交
const commit = () => {
    addModel.image = imgurl.value;
    addFormRef.value?.validate(async (valid) => {
        if (valid) {
            let res = null;
            if (addModel.type == EditType.ADD) {
                res = await addApi(addModel);
            } else {
                res = await editApi(addModel)
            }
            if (res && res.code == 200) {
                ElNotification({
                    title: "Success",
                    message: res.msg,
                    type: "success"
                })
                emits('reFresh')
                onClose();
            }
        }
    });
};
</script>

<style scoped></style>
