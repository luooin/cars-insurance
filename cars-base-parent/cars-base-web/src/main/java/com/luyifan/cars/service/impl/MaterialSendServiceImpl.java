//package com.luyifan.goods.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.luyifan.goods.mapper.MaterialSendMapper;
//import com.luyifan.goods.model.dto.ApplyParm;
//import com.luyifan.goods.model.dto.InfoUpdateParm;
//import com.luyifan.goods.model.dto.MaterialSendParm;
//import com.luyifan.goods.model.entity.MaterialSend;
//import com.luyifan.goods.model.entity.MaterialSendDetail;
//import com.luyifan.goods.service.MaterialSendDetailService;
//import com.luyifan.goods.service.MaterialSendService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class MaterialSendServiceImpl extends ServiceImpl<MaterialSendMapper, MaterialSend> implements MaterialSendService {
//    @Autowired
//    private MaterialSendDetailService materialSendDetailService;
////    @Autowired
////    private MaterialInfoService materialInfoService;
//    @Override
//    @Transactional
//    public void sendSave(MaterialSendParm parm) {
//        //保存发放主表
//        MaterialSend send = new MaterialSend();
//        BeanUtils.copyProperties(parm,send);
//        send.setCreateTime(new Date());
//        int insert = this.baseMapper.insert(send);
//        //保存物资明细
//        if(insert > 0){
//             parm.getInfos().forEach(item ->{
//                item.setSendId(send.getSendId());
//            });
//             //明细入库
//            materialSendDetailService.saveBatch(parm.getInfos());
//        }
//    }
//
//    @Override
//    public void apply(ApplyParm parm) {
//        //审核通过
//        if(parm.getStatus().equals("1")){
//            //更新记录的审核状态
//            //更新记录的状态
//            MaterialSend into = new MaterialSend();
//            into.setSendId(parm.getIntoId());
//            into.setStatus(parm.getStatus());
//            int i = this.baseMapper.updateById(into);
//            //更新物资的库存
//            if(i>0){
//                //根据记录的id查询物资库存
//                QueryWrapper<MaterialSendDetail> queryWrapper = new QueryWrapper<>();
//                queryWrapper.lambda().eq(MaterialSendDetail::getSendId,parm.getIntoId());
//                List<MaterialSendDetail> list = materialSendDetailService.list(queryWrapper);
//                List<InfoUpdateParm> upList = new ArrayList<>();
//                for(int j =0;j<list.size();j++){
//                    InfoUpdateParm updateParm = new InfoUpdateParm();
//                    updateParm.setStore(list.get(j).getNum());
//                    updateParm.setInfoId(list.get(j).getInfoId());
//                    upList.add(updateParm);
//                }
//                //出库处理：批量更新
//                materialInfoService.sendUpdateStore(upList);
//            }
//        }else if(parm.getStatus().equals("2")){ //拒绝
//            //更新记录的状态
//            MaterialSend into = new MaterialSend();
//            into.setSendId(parm.getIntoId());
//            into.setStatus(parm.getStatus());
//            this.baseMapper.updateById(into);
//        }
//    }
//}